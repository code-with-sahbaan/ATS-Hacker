package code.with.sahbaan.neohire.Entities.Recruiter;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESUME_JOB")
public class ResumeJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESUME_JOB_ID")
    private long resumeJobId;

    @ManyToOne
    @JoinColumn(name = "JOB_ID")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "RESUME_ID")
    private Resume resume;
}
