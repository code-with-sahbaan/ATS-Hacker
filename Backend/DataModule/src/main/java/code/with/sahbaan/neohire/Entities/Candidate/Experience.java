package code.with.sahbaan.neohire.Entities.Candidate;

import code.with.sahbaan.neohire.Entities.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EXPERIENCE")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EXPERIENCE_ID")
    private long experienceId;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "ROLE_DESCRIPTION", length = 65535)
    private String roleDescription;

    @Column(name = "FROM_DATE")
    private LocalDate from;

    @Column(name = "TO_DATE")
    private LocalDate to;

    @ManyToOne
    private Users candidate;

    @Column(name = "IS_PROFILE_COMPLETED")
    private boolean isProfileCompleted = false;

}
