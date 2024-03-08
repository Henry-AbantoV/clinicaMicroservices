package edu.unc.clinica.services;

import java.util.List;

import edu.unc.clinica.dto.UserRequest;
import edu.unc.clinica.dto.UserResponse;

public interface UserService {

	UserResponse saveUser(UserRequest userRequest);
	UserResponse getUser();
	List<UserResponse> getAllUser();
	
}
