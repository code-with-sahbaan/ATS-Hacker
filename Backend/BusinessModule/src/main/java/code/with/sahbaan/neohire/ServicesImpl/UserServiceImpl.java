package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Repositories.UserRepository;
import code.with.sahbaan.neohire.RequestDTO.UpdateUserRequest;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.UserResponse;
import code.with.sahbaan.neohire.Services.JwtService;
import code.with.sahbaan.neohire.Services.UserService;
import code.with.sahbaan.neohire.Utils.Constants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
    public String getToken() throws Exception {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof JwtAuthenticationToken jwtAuth) {
                Jwt jwt = jwtAuth.getToken();
                return jwt.getTokenValue();
            }
            return null;
        }catch(Exception e){
            throw new Exception("Failed to get Token");
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
    public void switchProfile(HttpServletResponse response) throws Exception {
        try {
            Users users = getCurrentlyLoggedUser();
            if (users.getRole().equals(Constants.ROLE_CANDIDATE)) {
                users.setRole(Constants.ROLE_RECRUITER);
            }else{
                users.setRole(Constants.ROLE_CANDIDATE);
            }
            saveOrUpdate(users);
            String accessToken = jwtService.generateToken(users);
            // Secure Cookie
            ResponseCookie accessTokenCookie = ResponseCookie.from(Constants.ACCESS_TOKEN, accessToken)
                    .httpOnly(true)
                    .sameSite("None")
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            response.sendRedirect(allowedOrigins);
        } catch (Exception e) {
            throw new Exception("Failed to switch Profile");
        }
    }
}
