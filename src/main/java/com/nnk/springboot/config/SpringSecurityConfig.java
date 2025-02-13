package com.nnk.springboot.config;

import com.nnk.springboot.services.AuthenticationService;
import com.nnk.springboot.services.PasswordValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.nnk.springboot.services.UserService;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private PasswordValidationService passwordValidationService;

    @Autowired
    @Lazy
    private UserService userService;

    /**
     * Bean to provide a PasswordEncoder for encoding passwords.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean to provide an AuthenticationService.
     *
     * @param passwordEncoder the PasswordEncoder to use.
     * @return an AuthenticationService instance.
     */
    @Bean
    public AuthenticationService authenticationService(PasswordEncoder passwordEncoder) {
        return new AuthenticationService(userService, passwordEncoder);
    }

    /**
     * Bean to provide a CustomAuthenticationProvider.
     *
     * @param authenticationService the AuthenticationService to use.
     * @return a CustomAuthenticationProvider instance.
     */
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(AuthenticationService authenticationService) {
        return new CustomAuthenticationProvider(passwordValidationService, authenticationService);
    }

    /**
     * Bean to configure the SecurityFilterChain.
     *
     * @param http the HttpSecurity object to configure.
     * @return a SecurityFilterChain instance.
     * @throws Exception if an error occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/app/login").permitAll()
                        .requestMatchers("/user/list", "/user/add", "/user/update/**", "/user/delete/**","/user/validate", "secure/article-details").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/app/logout")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/403"))
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                        .maximumSessions(1)
                        .expiredUrl("/app/login?expired=true")
                        .maxSessionsPreventsLogin(false)
                )
                .authenticationManager(authenticationManager(http))
                .authenticationProvider(customAuthenticationProvider(authenticationService(passwordEncoder())));

        return http.build();
    }

    /**
     * Bean to provide an AuthenticationManager.
     *
     * @param http the HttpSecurity object to configure.
     * @return an AuthenticationManager instance.
     * @throws Exception if an error occurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    /**
     * Bean to provide a CustomAuthenticationSuccessHandler.
     *
     * @return a CustomAuthenticationSuccessHandler instance.
     */
    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    /**
     * Bean to configure cookie settings.
     *
     * @return a ServletContextInitializer instance.
     */
    @Bean
    public ServletContextInitializer cookieConfig() {
        return servletContext -> {
            servletContext.getSessionCookieConfig().setHttpOnly(true);
            servletContext.getSessionCookieConfig().setSecure(true);
        };
    }

    /**
     * Bean to configure GrantedAuthorityDefaults.
     *
     * @return a GrantedAuthorityDefaults instance.
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Configure Spring Security to not use the "ROLE_" prefix
    }
}
