package code.with.sahbaan.neohire.ResponseDTO.Candidate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeResponse {

    private String resumeUrl;

    private String resumeName;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime lastUpdated;
}
