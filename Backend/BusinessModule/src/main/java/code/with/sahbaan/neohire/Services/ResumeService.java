package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {

    BaseResponse<ResumeResponse> updateResume(MultipartFile file) throws Exception;

    BaseResponse<ResumeResponse> getResumeDetails() throws Exception;

    Resume findResumeById(long id) throws Exception;

    BaseResponse<List<GetJobResponse>> getRecommendedJobs() throws Exception;
}
