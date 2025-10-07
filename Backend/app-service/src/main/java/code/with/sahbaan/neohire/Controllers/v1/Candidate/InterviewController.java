package code.with.sahbaan.neohire.Controllers.v1.Candidate;

import code.with.sahbaan.neohire.RequestDTO.Candidate.InitiateInterview;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.Services.AiInterviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("interview/v1")
public class InterviewController {

    @Autowired
    private AiInterviewService aiInterviewService;

    @PostMapping("initiateInterview")
    public ResponseEntity<byte[]> initiateInterview(@RequestBody InitiateInterview initiateInterview) throws Exception {
        log.info("Executing initiateInterview in InterviewController");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(aiInterviewService.initiateInterview(initiateInterview));
    }

    @PostMapping("getReply")
    public ResponseEntity<byte[]> getReply(@RequestParam("answer") MultipartFile answer) throws Exception {
        log.info("Executing getReply in InterviewController");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(aiInterviewService.getReply(answer));
    }
}
