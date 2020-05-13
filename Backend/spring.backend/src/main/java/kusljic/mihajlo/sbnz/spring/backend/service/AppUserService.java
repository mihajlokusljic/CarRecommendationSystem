package kusljic.mihajlo.sbnz.spring.backend.service;

import kusljic.mihajlo.sbnz.spring.backend.facts.AppUser;

public interface AppUserService {
	
	AppUser registerUser(AppUser newUser);
	
	AppUser updateUser(AppUser newUserData);
	
	AppUser findUser(String username);

}
