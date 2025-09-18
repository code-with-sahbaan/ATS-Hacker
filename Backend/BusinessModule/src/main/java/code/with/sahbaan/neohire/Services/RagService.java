package code.with.sahbaan.neohire.Services;

import org.springframework.web.multipart.MultipartFile;

public interface RagService {

    public void ingestResumeFromPdf(MultipartFile pdf, String userEmail, long resumeId);
}
