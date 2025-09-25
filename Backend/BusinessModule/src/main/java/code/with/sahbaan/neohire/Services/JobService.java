package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import code.with.sahbaan.neohire.RequestDTO.Recruiter.RecommendedResumeRequest;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.PostJobRequest;

import java.util.List;

public interface JobService {

    BaseResponse<List<GetJobResponse>> getAllJobs() throws Exception;

    BaseResponse<?> postJob(PostJobRequest postJobRequest) throws Exception;

    Job getJobById(long id) throws Exception;

    BaseResponse<List<ResumeResponse>> getRecommendedResume(RecommendedResumeRequest recommendedResumeRequest) throws Exception;

}
