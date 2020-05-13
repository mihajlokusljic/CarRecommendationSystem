package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.exception.EntityAlreadyExistsException;
import kusljic.mihajlo.sbnz.spring.backend.facts.AppUser;
import kusljic.mihajlo.sbnz.spring.backend.repository.AppUserRepository;
import kusljic.mihajlo.sbnz.spring.backend.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {
	
	private AppUserRepository appUserRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		super();
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public AppUser registerUser(AppUser newUser) {
		AppUser sameUser = this.findUser(newUser.getUsername());
		if(sameUser != null) {
			throw new EntityAlreadyExistsException(String.format("A user with username %s already exists.", newUser.getUsername()));
		}
		
		newUser.setPasswordHash(this.passwordEncoder.encode(newUser.getPasswordHash()));
		return this.appUserRepository.save(newUser);
	}


	@Override
	public AppUser updateUser(AppUser newUserData) {
		AppUser target = this.findUser(newUserData.getUsername());
		if(target == null) {
			throw new EntityNotFoundException(String.format("User with username %s does not exist", newUserData.getUsername()));
		}

		if(newUserData.getPasswordHash() != null && !newUserData.getPasswordHash().equals("")) {
			newUserData.setPasswordHash(this.passwordEncoder.encode(newUserData.getPasswordHash()));
		}
		
		return this.appUserRepository.save(newUserData);
	}

	@Override
	public AppUser findUser(String username) {
		return this.appUserRepository.findOneByUsername(username);
	}
	
}
