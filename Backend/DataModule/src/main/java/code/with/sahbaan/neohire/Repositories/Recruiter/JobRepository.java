package code.with.sahbaan.neohire.Repositories.Recruiter;

import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT NEW " +
            "code.with.sahbaan.neohire.ResponseDTO.Recruiter." +
            "GetJobResponse(jb.jobId, jb.jobTitle, " +
            "jb.companyDetails, jb.responsibilities, " +
            "jb.qualifications, jb.niceToHave, jb.jobPostedDateTime) " +
            "FROM Job jb WHERE jb.recruiter = :users")
    List<GetJobResponse> getAllJobs(@Param("users")Users users);
}
