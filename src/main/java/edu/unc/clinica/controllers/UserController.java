package edu.unc.clinica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import edu.unc.clinica.dto.UserResponse;
import edu.unc.clinica.domain.RefreshToken;
import edu.unc.clinica.dto.AuthRequestDTO;
import edu.unc.clinica.dto.JwtResponseDTO;
import edu.unc.clinica.dto.RefreshTokenRequestDTO;
import edu.unc.clinica.dto.UserRequest;
import edu.unc.clinica.services.JwtService;
import edu.unc.clinica.services.RefreshTokenService;
import edu.unc.clinica.services.UserService;


@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
    private JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenS;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.saveUser(userRequest);
         
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public ResponseEntity geAllUsers() {
    	  try {
              List<UserResponse> userResponses = userService.getAllUser();
              return ResponseEntity.ok(userResponses);
          } catch (Exception e){
              throw new RuntimeException(e);
          }
    }
    @PostMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
        UserResponse userResponse = userService.getUser();
        return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/test")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenS.createRefreshToken(authRequestDTO.getUsername());
           return JwtResponseDTO.builder()
                   .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                   .token(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
    
    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenS.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenS::verifyExpiration)
                .map(RefreshToken::getInfoUser)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

}
