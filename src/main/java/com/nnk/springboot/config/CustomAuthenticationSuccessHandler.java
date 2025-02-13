package com.nnk.springboot.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom implementation of AuthenticationSuccessHandler to handle successful authentication.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Handles successful authentication by redirecting the user to the appropriate URL.
     *
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param authentication the Authentication object.
     * @throws IOException if an I/O error occurs.
     * @throws ServletException if a servlet error occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String redirectUrl = determineTargetUrl(authentication);
        response.sendRedirect(redirectUrl);
    }

    /**
     * Determines the target URL based on the user's roles.
     *
     * @param authentication the Authentication object.
     * @return the target URL.
     */
    protected String determineTargetUrl(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));

        if (isAdmin) {
            return "/admin/home"; // Redirect to admin home page
        } else if (isUser) {
            return "/bidList/list"; // Redirect to user home page
        } else {
            return "/app/login?error"; // Redirect to login page with error
        }
    }
}
