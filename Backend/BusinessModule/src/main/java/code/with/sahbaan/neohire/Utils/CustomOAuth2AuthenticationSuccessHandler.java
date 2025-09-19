package code.with.sahbaan.neohire.Utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        // redirect to Angular app with token in query param
        String redirectUrl = allowedOrigins + "/authorize?token=" + customUserPrincipal.getAccessToken();
        response.sendRedirect(redirectUrl);
    }
}
