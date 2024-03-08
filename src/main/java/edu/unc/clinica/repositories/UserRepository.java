package edu.unc.clinica.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.unc.clinica.domain.InfoUser;

public interface UserRepository extends CrudRepository<InfoUser, Long> {
	public InfoUser findByUsername(String username);
}