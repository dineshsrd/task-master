package com.taskmaster.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        // Set response status and content type
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Create the response body
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseData.put("message", "Unauthorized access");

        // Convert map to JSON and write to response
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseData);
    }
}
