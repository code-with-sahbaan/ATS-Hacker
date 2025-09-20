package code.with.sahbaan.neohire.ResponseDTO.Recruiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetJobResponse {

    private long jobId;

    private String responsibilities;

    private String qualifications;

    private String niceToHave;

    private LocalDateTime jobPostedDateTime;

}
