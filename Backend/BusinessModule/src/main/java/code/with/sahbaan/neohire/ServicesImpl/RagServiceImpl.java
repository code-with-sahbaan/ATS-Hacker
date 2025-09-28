package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import code.with.sahbaan.neohire.Services.RagService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RagServiceImpl implements RagService {

    @Autowired
    private VectorStore vectorStore;

    @Override
    public void ingestResumeFromPdf(MultipartFile pdf, String userEmail, long resumeId) {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdf.getResource());
        List<Document> docs = reader.get();
        TokenTextSplitter resumeSplitter = new TokenTextSplitter(
                400,   // chunkSize (good balance of precision + context)
                80,   // minChunkSizeChars (don’t split too small)
                80,    // minChunkLengthToEmbed (skip headers like "Education")
                50,    // maxNumChunks (safety cap)
                false   // keepSeparator (preserve bullet points, newlines)
        );
        docs = resumeSplitter.apply(docs);
        // Add metadata
        docs.forEach(doc -> {
            doc.getMetadata().put("userEmail", userEmail);
            doc.getMetadata().put("resumeId", resumeId);
            doc.getMetadata().put("type", "resume");
        });
        // Deleting old embeddings
        vectorStore.delete("resumeId == " + resumeId);
        // Store in pgvector
        vectorStore.add(docs);
    }

    @Override
    public void ingestJobPost(Job job) throws Exception {
        try{
            TokenTextSplitter jobSectionSplitter = new TokenTextSplitter(
                    800,
                    200,
                    100,
                    100,
                    true
            );
            for (int i = 0; i < 3; i++) {
                String section = switch (i) {
                    case 0 -> "Qualifications";
                    case 1 -> "NiceToHave";
                    case 2 -> "Responsibilities";
                    default -> "";
                };
                String text = switch (i) {
                    case 0 -> job.getQualifications();
                    case 1 -> job.getNiceToHave();
                    case 2 -> job.getResponsibilities();
                    default -> "";
                };
                if (text.isEmpty()) {continue;}
                List<Document> docs = jobSectionSplitter.apply(List.of(new Document(text)));
                // Add metadata
                docs.forEach(doc -> {
                    doc.getMetadata().put("userEmail", job.getRecruiter().getEmail());
                    doc.getMetadata().put("jobId", job.getJobId());
                    doc.getMetadata().put("section", section);
                    doc.getMetadata().put("type", "job");
                });
                // Store in pgvector
                vectorStore.add(docs);
            }

        }catch(Exception e){
            throw new Exception("Failed to post Job");
        }
    }

    @Override
    public List<Document> getSimilarityResumes(String text) {
        String filterExpression = "type == 'resume'";
        return vectorStore.similaritySearch(
                SearchRequest.
                        builder()
                        .query(text)
                        .filterExpression(filterExpression)
                        .similarityThreshold(0.45)
                        .topK(10)
                        .build()
        );
    }

    @Override
    public List<Document> getSimilarityJobs(String text) {
        String filterExpression = "type == 'job'";
        return vectorStore.similaritySearch(
                SearchRequest.
                        builder()
                        .query(text)
                        .filterExpression(filterExpression)
                        .similarityThreshold(0.45)
                        .topK(50)
                        .build()
        );
    }
}
