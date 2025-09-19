package code.with.sahbaan.neohire.Repositories.Recruiter;

import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
