package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.nnk.springboot.services.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SpringSecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(UserService userService, PasswordEncoder passwordEncoder, HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/app/login").permitAll()
                        .requestMatchers("/user/list","/user/add", "/user/update/**", "/user/delete/**").permitAll()
                        //.requestMatchers("/user/add", "/user/update/**", "/user/delete/**").hasRole("ADMIN") // Accès ADMIN
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        /*.defaultSuccessUrl("/")*/
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/app/login?logout=true")
                        .invalidateHttpSession(true)
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/403"))
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession() // Protège contre les attaques de fixation de session
                        .maximumSessions(1) // Limite à une session par utilisateur
                        .expiredUrl("/app/login?expired=true") // Redirige vers la page de connexion en cas d'expiration
                        .maxSessionsPreventsLogin(false) // Permet une nouvelle connexion après expiration
                )

                .authenticationManager(authenticationManager(http))

                .authenticationProvider(customAuthenticationProvider);

                ;
        ;


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
    @Bean
    public ServletContextInitializer cookieConfig() {
        return servletContext -> {
            servletContext.getSessionCookieConfig().setHttpOnly(true); // Empêche l'accès JavaScript
            servletContext.getSessionCookieConfig().setSecure(true); // Utilise HTTPS uniquement

        };
    }
}
