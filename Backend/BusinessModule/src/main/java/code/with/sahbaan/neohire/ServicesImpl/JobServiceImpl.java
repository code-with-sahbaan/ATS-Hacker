package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Repositories.Recruiter.JobRepository;
import code.with.sahbaan.neohire.Services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;


}
