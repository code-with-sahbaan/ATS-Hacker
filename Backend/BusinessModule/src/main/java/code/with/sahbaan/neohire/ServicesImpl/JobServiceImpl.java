package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Repositories.Recruiter.JobRepository;
import code.with.sahbaan.neohire.RequestDTO.Recruiter.RecommendedResumeRequest;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.PostJobRequest;
import code.with.sahbaan.neohire.Services.JobService;
import code.with.sahbaan.neohire.Services.RagService;
import code.with.sahbaan.neohire.Services.ResumeService;
import code.with.sahbaan.neohire.Services.UserService;
import code.with.sahbaan.neohire.Utils.BusinessConstants;
import org.springframework.ai.document.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RagService ragService;

    @Autowired
    private ResumeService resumeService;

    @Override
    public BaseResponse<List<GetJobResponse>> getAllJobs() throws Exception {
        try{
            Users users = userService.getCurrentlyLoggedUser();
            List<GetJobResponse> jobs = jobRepository.getAllJobs(users);
            return new BaseResponse<>("Jobs Fetched Successfully", jobs);
        }catch(Exception e){
            throw new Exception("Failed to get Jobs");
        }
    }

    @Override
    public BaseResponse<?> postJob(PostJobRequest postJobRequest) throws Exception {
        try{
            Job job = new Job();
            BeanUtils.copyProperties(postJobRequest, job);
            job.setRecruiter(userService.getCurrentlyLoggedUser());
            Job saved = jobRepository.save(job);
            ragService.ingestJobPost(saved);
            return new BaseResponse<>("Job Posted Successfully", null);
        }catch(Exception e){
            throw new Exception("Failed to post Job");
        }
    }

    @Override
    public Job getJobById(long id) throws Exception {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public BaseResponse<List<ResumeResponse>> getRecommendedResume(RecommendedResumeRequest recommendedResumeRequest) throws Exception {
        try{
            Job job = getJobById(recommendedResumeRequest.getJobId());
            Map<String, String> jdSections = Map.of(
                    BusinessConstants.QUALIFICATIONS, job.getQualifications(),
                    BusinessConstants.RESPONSIBILITIES,  job.getResponsibilities(),
                    BusinessConstants.NICE_TO_HAVE, job.getNiceToHave()
            );
            Map<Long, Double> resumeScores = new HashMap<>();

            for (var entry : jdSections.entrySet()) {
                String sectionName = entry.getKey();
                String sectionText = entry.getValue();

                if (sectionText.isBlank()) continue;

                List<Document> sectionResults = ragService.getSimilarityResumes(sectionText);

                for (Document doc : sectionResults) {
                    Long resumeId = Long.parseLong(String.valueOf(doc.getMetadata().get("resumeId")));
                    double score = doc.getScore() != null ? doc.getScore() : 0.0; // higher = more similar

                    // Weight RequiredExperience higher than NiceToHave, etc.
                    double weight = BusinessConstants.SECTION_WEIGHTS.getOrDefault(sectionName, 1.0);

                    resumeScores.merge(resumeId, score * weight, Double::sum);
                }
            }

            // Sort by final score
            List<Long> resumeIds = resumeScores.entrySet().stream()
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .map(Map.Entry::getKey) // only Resume ID list
                    .toList();

            List<ResumeResponse> resumeResponses = new ArrayList<>();
            for (Long resumeId : resumeIds) {
                Resume resume = resumeService.findResumeById(resumeId);
                ResumeResponse resumeResponse = new ResumeResponse();
                BeanUtils.copyProperties(resume, resumeResponse);
                resumeResponses.add(resumeResponse);
            }

            return new BaseResponse<>("Resumes Fetched Successfully", resumeResponses);

        }catch (Exception e){
            throw new Exception("Failed to Get Resumes");
        }
    }
}
