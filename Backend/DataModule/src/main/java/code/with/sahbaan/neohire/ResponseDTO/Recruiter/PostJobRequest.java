package code.with.sahbaan.neohire.ResponseDTO.Recruiter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostJobRequest {

    private String jobTitle;

    private String responsibilities;

    private String qualifications;

    private String niceToHave;

    private String companyDetails;

}
