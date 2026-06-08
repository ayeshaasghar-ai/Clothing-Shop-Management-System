package com.clothware.controller;

import com.clothware.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserDetailsService userDetailsService;

    /** POST /api/auth/login  { "email": "...", "password": "..." } */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid email or password"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtUtil.generateToken(email);

        return ResponseEntity.ok(Map.of(
            "token", token,
            "email", email,
            "role", userDetails.getAuthorities().iterator().next().getAuthority()
        ));
    }
}
