package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Services.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.secret}")
    private String secret;

    @Value("${access.token.expiry.in.hour}")
    private int accessTokenExpiry;

    @Override
    public String generateToken(Users user) {
        Algorithm algorithm = Algorithm.HMAC256((secret).getBytes());
        Date expirationTime = new Date(System.currentTimeMillis() + (accessTokenExpiry * 60L) * 60 * 1000);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

        return JWT.create().withSubject(user.getEmail())
                .withIssuer(user.getProvider())
                .withExpiresAt(expirationTime)
                .withClaim("roles", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }
}
