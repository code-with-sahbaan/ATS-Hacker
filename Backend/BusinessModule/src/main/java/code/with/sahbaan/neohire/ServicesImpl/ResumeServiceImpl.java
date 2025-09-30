package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Repositories.Candidate.ResumeRepository;
import code.with.sahbaan.neohire.Repositories.Recruiter.JobRepository;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import code.with.sahbaan.neohire.ResponseDTO.Recruiter.GetJobResponse;
import code.with.sahbaan.neohire.Services.*;
import code.with.sahbaan.neohire.Utils.BusinessConstants;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private RagService ragService;

    @Autowired
    private JobRepository jobRepository;

    @Transactional
    @Override
    public BaseResponse<ResumeResponse> updateResume(MultipartFile file) throws Exception{
        try{
            Users users = userService.getCurrentlyLoggedUser();
            Resume resume;
            Optional<Resume> resumeOptional = resumeRepository.findByCandidate(users);
            resume = resumeOptional.orElseGet(Resume::new);
            resume.setCandidate(users);
            resume.setResumeName(file.getOriginalFilename());
            resume.setResumeUrl(mediaService.uploadFile(file));
            resume.setResumeText(extractTextFromPdf(file));
            Resume saved = resumeRepository.save(resume);
            ragService.ingestResumeFromPdf(file, users.getEmail(), saved.getResumeId());
            ResumeResponse resumeResponse = new ResumeResponse();
            BeanUtils.copyProperties(saved, resumeResponse);
            return new BaseResponse<>("Resume Details Fetched Successfully", resumeResponse);
        }catch(Exception e){
            throw new Exception("Failed to update resume");
        }
    }

    @Override
    public BaseResponse<ResumeResponse> getResumeDetails() throws Exception {
        try{
            Users users = userService.getCurrentlyLoggedUser();
            ResumeResponse resumeResponse = new ResumeResponse();
            Optional<Resume> resume = resumeRepository.findByCandidate(users);
            if (resume.isPresent()) {
                BeanUtils.copyProperties(resume.get(), resumeResponse);
            }else{
                resumeResponse.setResumeName("--");
                resumeResponse.setResumeUrl("--");
                resumeResponse.setLastUpdated(null);
            }
            return new BaseResponse<>("Resume Details Fetched Successfully", resumeResponse);
        }catch(Exception e){
            throw new  Exception("Failed to get Resume Details");
        }
    }

    @Override
    public Resume findResumeById(long id) throws Exception {
        return resumeRepository.findById(id).get();
    }

    @Override
    public BaseResponse<List<GetJobResponse>> getRecommendedJobs() throws Exception {
        try {
            Users users = userService.getCurrentlyLoggedUser();
            Resume resume = resumeRepository.findByCandidate(users).get();
            List<Document> documents = ragService.getSimilarityJobs(resume.getResumeText());
            Map<String, Double> jobRanking = documents.stream()
                    .collect(Collectors.groupingBy(
                            doc -> doc.getMetadata().get("jobId").toString(),
                            Collectors.summingDouble(doc -> {
                                double rawScore = doc.getScore() != null ? doc.getScore() : 0;
                                String section = doc.getMetadata().get("section").toString();
                                double weight = BusinessConstants.SECTION_WEIGHTS.getOrDefault(section, 1.0);
                                return rawScore * weight;   // apply weighted scoring
                            })
                    ));

            List<String> rankedJobs = jobRanking.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .map(Map.Entry::getKey)
                    .toList();

            List<GetJobResponse> getJobResponses = new ArrayList<>();
            for (String rankedJob : rankedJobs) {
                Job job = jobRepository.findById(Long.parseLong(rankedJob)).get();
                GetJobResponse getJobResponse = new GetJobResponse();
                BeanUtils.copyProperties(job, getJobResponse);
                getJobResponses.add(getJobResponse);
            }
            return new BaseResponse<>("Recommended Jobs Fetched Successfully", getJobResponses);
        } catch (Exception e) {
            throw new Exception("Failed to get Recommended Jobs");
        }
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        PDDocument document = Loader.loadPDF(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        return pdfStripper.getText(document);
    }
}
