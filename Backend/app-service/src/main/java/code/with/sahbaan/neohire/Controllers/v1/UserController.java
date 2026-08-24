package code.with.sahbaan.neohire.Controllers.v1;

import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.RequestDTO.UpdateUserRequest;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.ResponseDTO.UserResponse;
import code.with.sahbaan.neohire.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUserDetails")
    public ResponseEntity<BaseResponse<UserResponse>> getUserDetails() throws Exception {
        log.info("Executing getUserDetails in UserController");
        return new ResponseEntity<>(userService.getUserDetails(), HttpStatus.OK);
    }

    @PostMapping("updateUserDetails")
    public ResponseEntity<BaseResponse<UserResponse>> updateUserDetails(@RequestBody UpdateUserRequest updateUserRequest) throws Exception {
        log.info("Executing updateUserDetails in UserController");
        return new ResponseEntity<>(userService.updateUserDetails(updateUserRequest), HttpStatus.OK);
    }

    @GetMapping("switchProfile")
    public ResponseEntity<BaseResponse<String>> switchProfile(HttpServletResponse response) throws Exception {
        log.info("Executing switchProfile in UserController");
        return new ResponseEntity<>(userService.switchProfile(response), HttpStatus.OK);
    }
}
