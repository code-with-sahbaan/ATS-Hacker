package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import code.with.sahbaan.neohire.RequestDTO.Recruiter.RecommendedResumeRequest;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import code.with.sahbaan.neohire.Services.JobService;
import code.with.sahbaan.neohire.Services.RagService;
import code.with.sahbaan.neohire.Services.ResumeService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        });
        // Deleting old embeddings
        vectorStore.delete("resumeId == " + resumeId);
        // Store in pgvector
        vectorStore.add(docs);
    }

    @Override
    public List<Document> getSimilaritySearches(String text) {
        return vectorStore.similaritySearch(
                SearchRequest.
                        builder()
                        .query(text)
                        .similarityThreshold(0.45)
                        .topK(10)
                        .build()
        );
    }
}
