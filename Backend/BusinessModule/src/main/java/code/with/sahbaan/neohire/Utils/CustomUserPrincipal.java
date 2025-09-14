package code.with.sahbaan.neohire.Utils;

import code.with.sahbaan.neohire.Entities.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class CustomUserPrincipal implements OAuth2User {

    @Getter
    private final Users user;
    private final Map<String, Object> attributes;

    public CustomUserPrincipal(Users user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

}
