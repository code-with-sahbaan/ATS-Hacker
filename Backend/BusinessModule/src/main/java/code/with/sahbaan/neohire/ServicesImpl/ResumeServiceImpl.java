package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Repositories.Candidate.ResumeRepository;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.Candidate.ResumeResponse;
import code.with.sahbaan.neohire.Services.MediaService;
import code.with.sahbaan.neohire.Services.RagService;
import code.with.sahbaan.neohire.Services.ResumeService;
import code.with.sahbaan.neohire.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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

    @Override
    public BaseResponse<ResumeResponse> updateResume(MultipartFile file) throws Exception{
        try{
            Users users = userService.getCurrentlyLoggedUser();
            Resume resume;
            Optional<Resume> resumeOptional = resumeRepository.findByCandidate(users);
            if(resumeOptional.isPresent()){
                resume = resumeOptional.get();
            }else{
                resume = new Resume();
            }
            resume.setCandidate(users);
            resume.setResumeName(file.getOriginalFilename());
            resume.setResumeUrl(mediaService.uploadFile(file));
            Resume saved = resumeRepository.save(resume);
            ragService.ingestResumeFromPdf(file, users.getEmail(), saved.getResumeId());
            return getResumeDetails();
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
}
