package code.with.sahbaan.neohire.Entities.Recruiter;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.Entities.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JOB")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JOB_ID")
    private long jobId;

    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Column(name = "RESPONSIBILITIES", length = 65535)
    private String responsibilities;

    @Column(name = "QUALIFICATIONS", length = 65535)
    private String qualifications;

    @Column(name = "NICE_TO_HAVE", length = 65535)
    private String niceToHave;

    @Column(name = "COMPANY_DETAILS", length = 65535)
    private String companyDetails;

    @Column(name = "JOB_POSTED_DATE")
    private LocalDateTime jobPostedDateTime;

    @ManyToOne
    private Users recruiter;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<ResumeJob> resumeJobs = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.jobPostedDateTime = LocalDateTime.now();
    }
}
