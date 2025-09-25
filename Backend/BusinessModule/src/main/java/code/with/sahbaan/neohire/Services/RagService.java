package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.RequestDTO.Recruiter.RecommendedResumeRequest;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RagService {

    void ingestResumeFromPdf(MultipartFile pdf, String userEmail, long resumeId);

    List<Document> getSimilaritySearches(String text);
}
