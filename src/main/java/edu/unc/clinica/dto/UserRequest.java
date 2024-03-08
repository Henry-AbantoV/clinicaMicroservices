package edu.unc.clinica.dto;

import java.util.List;

import edu.unc.clinica.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
	private Long id;
	private String userName;
	private String password;
	private List<UserRole> roles;
	

}
