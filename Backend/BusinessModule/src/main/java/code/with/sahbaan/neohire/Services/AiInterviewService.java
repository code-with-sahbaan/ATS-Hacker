package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.RequestDTO.Candidate.InitiateInterview;
import org.springframework.web.multipart.MultipartFile;

public interface AiInterviewService {

    byte[] initiateInterview(InitiateInterview initiateInterview) throws Exception;

    byte[] getReply(MultipartFile speech) throws Exception;
}
