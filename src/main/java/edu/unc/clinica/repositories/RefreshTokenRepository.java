package edu.unc.clinica.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;


import edu.unc.clinica.domain.RefreshToken;
import edu.unc.clinica.DetailsAuth.RefreshableCRUDRepository;
@Repository
public interface RefreshTokenRepository extends RefreshableCRUDRepository<RefreshToken, Integer>{
	Optional<RefreshToken> findByToken(String token);

}
