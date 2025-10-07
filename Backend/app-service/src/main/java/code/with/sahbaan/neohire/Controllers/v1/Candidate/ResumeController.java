package code.with.sahbaan.neohire.Controllers.v1.Candidate;

import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import code.with.sahbaan.neohire.Services.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("resume/v1")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;


    @PostMapping("updateResume")
    public ResponseEntity<BaseResponse<ResumeResponse>> updateResume(@RequestParam("resume") MultipartFile resume) throws Exception {
        log.info("Executing updateResume in ResumeController");
        return new ResponseEntity<>(resumeService.updateResume(resume), HttpStatus.OK);
    }

    @GetMapping("getResumeDetails")
    public ResponseEntity<BaseResponse<ResumeResponse>> getResumeDetails() throws Exception {
        log.info("Executing getResumeDetails in ResumeController");
        return new ResponseEntity<>(resumeService.getResumeDetails(), HttpStatus.OK);
    }

    @GetMapping("getRecommendedJobs")
    public ResponseEntity<BaseResponse<List<GetJobResponse>>> getRecommendedJobs() throws Exception {
        log.info("Executing getRecommendedJobs in ResumeController");
        return new ResponseEntity<>(resumeService.getRecommendedJobs(), HttpStatus.OK);
    }

}
