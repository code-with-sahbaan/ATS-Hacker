package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Repositories.UserRepository;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.UserResponse;
import code.with.sahbaan.neohire.Services.UserService;
import code.with.sahbaan.neohire.Utils.CustomUserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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
            CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Users users = customUserPrincipal.getUser();
            return findByEmail(users.getEmail()).get();
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
}
