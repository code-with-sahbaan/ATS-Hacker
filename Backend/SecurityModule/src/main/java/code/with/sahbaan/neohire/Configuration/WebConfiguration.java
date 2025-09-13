package code.with.sahbaan.neohire.Configuration;

import code.with.sahbaan.neohire.Services.CustomOAuth2UserService;
import code.with.sahbaan.neohire.ServicesImpl.CustomOAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Set;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebConfiguration {

    @Autowired
    private final CustomOAuth2UserServiceImpl customOAuth2UserService;


    @Value("${cors.allowed.origins}")
    private String originAllowed;

    @Value("${public.urls}")
    private Set<String> publicUrls;



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(originAllowed));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configure(http));
        http.logout(lOut->{
            lOut.logoutUrl("/user/logout").invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID").deleteCookies("JSESSIONID");

            // Allow CORS for the logout URL
            lOut.addLogoutHandler((request, response, authentication) -> {
                response.addHeader("Access-Control-Allow-Origin", originAllowed);
                response.addHeader("Access-Control-Allow-Credentials", "true");
            });
        });
        http.oauth2Login(oauth ->
        {
            oauth.userInfoEndpoint(info -> {
               info.userService(customOAuth2UserService);
            });
           oauth.defaultSuccessUrl(originAllowed, true);
        });

        http.authorizeHttpRequests(request -> {
            request.requestMatchers(publicUrls.toArray(new String[0])).permitAll();
        });

        http.authorizeHttpRequests(request -> {
            request.anyRequest().authenticated();
        });

        return http.build();
    }

}

