package code.with.sahbaan.neohire.Configuration;

import code.with.sahbaan.neohire.Utils.CustomOAuth2UserServiceImpl;
import code.with.sahbaan.neohire.Utils.CustomOAuth2AuthenticationSuccessHandler;
import code.with.sahbaan.neohire.Utils.Constants;
import code.with.sahbaan.neohire.Utils.FilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    private CustomOAuth2UserServiceImpl customOAuth2UserService;

    @Autowired
    private CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler;

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
                    .deleteCookies("JSESSIONID").deleteCookies("JSESSIONID")
                    .logoutSuccessUrl(originAllowed);

            // Allow CORS for the logout URL
            lOut.addLogoutHandler((request, response, authentication) -> {
                response.addHeader("Access-Control-Allow-Origin", originAllowed);
                response.addHeader("Access-Control-Allow-Credentials", "true");
            });
        });
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.oauth2Login(oauth ->
        {
            oauth.userInfoEndpoint(info -> {
               info.userService(customOAuth2UserService);
            });
            oauth.successHandler((request, response, authentication) -> {
                customOAuth2AuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            });
        });

        http.oauth2ResourceServer(oauth ->{
           oauth.jwt(Customizer.withDefaults());
        });

        http
            .exceptionHandling(ex -> ex
                 .authenticationEntryPoint((req, res, e) -> {
                     FilterUtil.writeErrorResponse(res, "Session Expired", HttpStatus.FORBIDDEN.value());
                 })
                 .accessDeniedHandler((req, res, e) -> {
                      FilterUtil.writeErrorResponse(res, "Session Expired", HttpStatus.FORBIDDEN.value());
                 })
        );

        http.authorizeHttpRequests(request -> {
            request.requestMatchers(publicUrls.toArray(new String[0])).permitAll();
        });

        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/user/**").hasAnyAuthority(Constants.ROLE_CANDIDATE, Constants.ROLE_RECRUITER);
            request.requestMatchers("/resume/**").hasAuthority(Constants.ROLE_CANDIDATE);
        });

        http.authorizeHttpRequests(request -> {
            request.anyRequest().authenticated();
        });

        return http.build();
    }

}

