package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Services.RagService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
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
                500,   // chunkSize (good balance of precision + context)
                100,   // minChunkSizeChars (don’t split too small)
                50,    // minChunkLengthToEmbed (skip headers like "Education")
                80,    // maxNumChunks (safety cap)
                true   // keepSeparator (preserve bullet points, newlines)
        );
        docs = resumeSplitter.apply(docs);
        // Add metadata
        docs.forEach(doc -> {
            doc.getMetadata().put("userEmail", userEmail);
            doc.getMetadata().put("resumeId", resumeId);
        });
        // Store in pgvector
        vectorStore.add(docs);
    }
}
