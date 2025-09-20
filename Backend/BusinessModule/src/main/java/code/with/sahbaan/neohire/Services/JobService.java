package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;

import java.util.List;

public interface JobService {

    BaseResponse<List<GetJobResponse>> getAllJobs() throws Exception;

}
