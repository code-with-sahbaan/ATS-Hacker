package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {

    BaseResponse<ResumeResponse> updateResume(MultipartFile file) throws Exception;

    BaseResponse<ResumeResponse> getResumeDetails() throws Exception;

    Resume findResumeById(long id) throws Exception;

}
