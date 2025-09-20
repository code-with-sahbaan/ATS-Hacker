package code.with.sahbaan.neohire.Controllers.v1.Recruiter;

import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import code.with.sahbaan.neohire.Services.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
