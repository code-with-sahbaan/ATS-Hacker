package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Repositories.Recruiter.JobRepository;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import code.with.sahbaan.neohire.Services.JobService;
import code.with.sahbaan.neohire.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService;

    @Override
    public BaseResponse<List<GetJobResponse>> getAllJobs() throws Exception {
        try{
            Users users = userService.getCurrentlyLoggedUser();
            List<GetJobResponse> jobs = jobRepository.getAllJobs(users);
            return new BaseResponse<>("Jobs Fetched Successfully", jobs);
        }catch(Exception e){
            throw new Exception("Failed to get Jobs");
        }
    }
}
