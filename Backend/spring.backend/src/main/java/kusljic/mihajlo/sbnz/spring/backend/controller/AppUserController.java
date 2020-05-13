package kusljic.mihajlo.sbnz.spring.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kusljic.mihajlo.sbnz.spring.backend.dto.AppUserDTO;
import kusljic.mihajlo.sbnz.spring.backend.dto.UserRegistrationDTO;
import kusljic.mihajlo.sbnz.spring.backend.facts.AppUser;
import kusljic.mihajlo.sbnz.spring.backend.facts.UserRole;
import kusljic.mihajlo.sbnz.spring.backend.service.AppUserService;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
	
	private AppUserService appUserService;
	
	@Autowired
	public AppUserController(AppUserService appUserService) {
		super();
		this.appUserService = appUserService;
	}

	@PostMapping(value = "/admins")
	public ResponseEntity<AppUserDTO> registerAdministrator(@RequestBody UserRegistrationDTO newUserData) {
		AppUser newUser = new AppUser(null, newUserData.getUsername(), newUserData.getPassword(), newUserData.getFullName(), UserRole.ADMINISTRATOR);
		newUser = this.appUserService.registerUser(newUser);
		AppUserDTO result = new AppUserDTO(newUser);
		return new ResponseEntity<AppUserDTO>(result, HttpStatus.CREATED);
	}

}
