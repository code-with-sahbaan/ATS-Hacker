package code.with.sahbaan.neohire.Controllers.v1.Recruiter;

import code.with.sahbaan.neohire.RequestDTO.Recruiter.RecommendedResumeRequest;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.PostJobRequest;
import code.with.sahbaan.neohire.Services.JobService;
import code.with.sahbaan.neohire.Services.RagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("job/v1")
public class JobController {

    @Autowired
    private JobService jobService;


    @GetMapping("getJobs")
    public ResponseEntity<BaseResponse<List<GetJobResponse>>> getAllJobs() throws Exception {
        log.info("Executing getAllJobs in JobController");
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    @PostMapping("postJob")
    public ResponseEntity<BaseResponse<?>> postJob(@RequestBody PostJobRequest postJobRequest) throws Exception {
        log.info("Executing postJob in JobController");
        return new ResponseEntity<>(jobService.postJob(postJobRequest), HttpStatus.OK);
    }

    @PostMapping("getRecommendedResume")
    public ResponseEntity<BaseResponse<List<ResumeResponse>>> getRecommendedResume(@RequestBody RecommendedResumeRequest recommendedResumeRequest) throws Exception {
        log.info("Executing getRecommendedResume in JobController");
        return new ResponseEntity<>(jobService.getRecommendedResume(recommendedResumeRequest), HttpStatus.OK);
    }
}
