package code.with.sahbaan.neohire.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse {

    private String name;
    private String email;
    private String role;
    private String pictureUrl;
    private String city;
    private String country;
    private Set<String> skills;
}
