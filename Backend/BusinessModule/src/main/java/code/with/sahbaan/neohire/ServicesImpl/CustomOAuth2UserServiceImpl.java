package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Services.CustomOAuth2UserService;
import code.with.sahbaan.neohire.Services.JwtService;
import code.with.sahbaan.neohire.Services.UserService;
import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Utils.Constants;
import code.with.sahbaan.neohire.Utils.CustomUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService implements CustomOAuth2UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract Google profile details
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        // Save or update in DB
        try{
            if (userService.findByEmail(email).isEmpty()) {
                Users users = Users.builder()
                        .email(email)
                        .name(name)
                        .pictureUrl(picture)
                        .provider(Constants.PROVIDER_GOOGLE)
                        .role(Constants.ROLE_CANDIDATE) // default Role
                        .build();
                userService.saveOrUpdate(users);
            }
            Users users = userService.findByEmail(email).get();
            String accessToken = jwtService.generateToken(users);

            return new CustomUserPrincipal(users, oAuth2User.getAttributes(), accessToken);
        }catch (Exception e){
            return null;
        }
    }

}
