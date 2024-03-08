package edu.unc.clinica.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unc.clinica.domain.RefreshToken;
import edu.unc.clinica.repositories.RefreshTokenRepository;
import edu.unc.clinica.repositories.UserRepository;

@Service
public class RefreshTokenService {

	@Autowired
	RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public RefreshToken createRefreshToken(String username) {
		RefreshToken refreshToken = RefreshToken.builder()
				.infoUser(userRepository.findByUsername(username))
				.token(UUID.randomUUID().toString())
				.expiryDate(Instant.now().plusMillis(600000))
				.build();
		return refreshTokenRepository.save(refreshToken);
	}
	
	public Optional<RefreshToken> findByToken(String token){
		return refreshTokenRepository.findByToken(token);
		
	}
	public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;

    }
	
}
