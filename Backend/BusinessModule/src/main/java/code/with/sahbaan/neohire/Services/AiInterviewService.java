package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.RequestDTO.Candidate.InitiateInterview;

public interface AiInterviewService {

    byte[] initiateInterview(InitiateInterview initiateInterview) throws Exception;
}
