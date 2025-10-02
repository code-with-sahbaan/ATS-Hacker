package code.with.sahbaan.neohire.RequestDTO.Candidate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiateInterview {

    private String title;
    private int yourYearsOfExperience;
    private String applyingForPosition;
    private int requiredExperienceForJob;
}
