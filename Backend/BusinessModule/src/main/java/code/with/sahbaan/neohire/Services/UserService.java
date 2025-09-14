package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.UserResponse;

import java.util.Optional;

public interface UserService {

    Optional<Users> findByEmail(String email);

    void saveOrUpdate(Users user) throws Exception;

    Users getCurrentlyLoggedUser() throws Exception;

    BaseResponse<UserResponse> getUserDetails() throws Exception;
}
