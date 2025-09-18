package code.with.sahbaan.neohire.Entities.Candidate;

import code.with.sahbaan.neohire.Entities.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "LAST_UPDATED")
    private LocalDateTime lastUpdated;

    @OneToOne
    private Users candidate;

    @PrePersist
    protected void onCreate() {
        this.lastUpdated = LocalDateTime.now();
    }
}
