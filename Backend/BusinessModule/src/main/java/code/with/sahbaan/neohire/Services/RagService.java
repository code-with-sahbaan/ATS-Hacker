package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RagService {

    void ingestResumeFromPdf(MultipartFile pdf, String userEmail, long resumeId);

    void ingestJobPost(Job job) throws Exception;

    List<Document> getSimilarityResumes(String text);

    List<Document> getSimilarityJobs(String text);
}
