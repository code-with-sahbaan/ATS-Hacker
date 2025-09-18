package code.with.sahbaan.neohire.Repositories.Candidate;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findByCandidate(Users users);
}
