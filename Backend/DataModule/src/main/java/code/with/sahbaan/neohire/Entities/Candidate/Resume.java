package code.with.sahbaan.neohire.Entities.Candidate;

import code.with.sahbaan.neohire.Entities.Recruiter.ResumeJob;
import code.with.sahbaan.neohire.Entities.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESUME")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESUME_ID")
    private long resumeId;

    @Column(name = "RESUME_URL")
    private String resumeUrl;

    @Column(name = "RESUME_NAME")
    private String resumeName;

    @Column(name = "RESUME_TEXT", length = 65535)
    private String resumeText;

    @Column(name = "LAST_UPDATED")
    private LocalDateTime lastUpdated;

    @OneToOne
    private Users candidate;

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<ResumeJob> resumeJobs = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.lastUpdated = LocalDateTime.now();
    }
}
