package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Repositories.UserRepository;
import code.with.sahbaan.neohire.RequestDTO.UpdateUserRequest;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.UserResponse;
import code.with.sahbaan.neohire.Services.JwtService;
import code.with.sahbaan.neohire.Services.UserService;
import code.with.sahbaan.neohire.Utils.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveOrUpdate(Users user) throws Exception {
        try{
            userRepository.save(user);
        }catch(Exception e){
            throw new Exception("Failed to save/update user");
        }
    }

    @Override
    public Users getCurrentlyLoggedUser() throws Exception {
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return findByEmail(email).get();
        }catch(Exception e){
            throw new  Exception("Failed to get Users");
        }
    }

    @Override
    public BaseResponse<UserResponse> getUserDetails() throws Exception {
        try{
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(getCurrentlyLoggedUser(), userResponse);
            return new BaseResponse<>("User Details Fetched Successfully", userResponse);
        }catch(Exception e){
            throw new Exception("Failed to get User Details");
        }
    }

    @Override
    public BaseResponse<UserResponse> updateUserDetails(UpdateUserRequest updateUserRequest) throws Exception {
        Users users = getCurrentlyLoggedUser();
        BeanUtils.copyProperties(updateUserRequest, users);
        saveOrUpdate(users);
        return getUserDetails();
    }

    @Override
    public BaseResponse<String> switchProfile() throws Exception {
        try {
            Users users = getCurrentlyLoggedUser();
            if (users.getRole().equals(Constants.ROLE_CANDIDATE)) {
                users.setRole(Constants.ROLE_RECRUITER);
            }else{
                users.setRole(Constants.ROLE_CANDIDATE);
            }
            saveOrUpdate(users);
            String accessToken = jwtService.generateToken(users);
            String url = allowedOrigins + "/authorize?token=" + accessToken;
            return new BaseResponse<>("Profile Switched Successfully", url);
        } catch (Exception e) {
            throw new Exception("Failed to switch Profile");
        }
    }
}
