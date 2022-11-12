package com.example.restApi.Controller;

import com.example.restApi.Entity.UserEntity;
import com.example.restApi.Exception.UserNotFoundException;
import com.example.restApi.Model.User;
import com.example.restApi.Model.UserDto;
import com.example.restApi.Repository.UserRepo;
import com.example.restApi.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt/auth")
public class JwtController {
    private final AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtController(AuthenticationManager authenticationManager, UserRepo userRepo, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity auth(@RequestBody UserDto userDto) {

        try {
            String username = userDto.getUsername();
            String password = userDto.getPassword();
            if(username == null || password == null) {
                return ResponseEntity.badRequest().body("Username and password can't be empty");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserEntity userEntity = userRepo.findByUsername(username);
            String token = jwtTokenProvider.createToken(userEntity.getUsername(), userEntity.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
        contextLogoutHandler.logout(request, response, null);
    }
}