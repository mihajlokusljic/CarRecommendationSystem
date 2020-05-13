package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.facts.AppUser;
import kusljic.mihajlo.sbnz.spring.backend.repository.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private AppUserRepository appUserRepository;
	
	@Autowired
	public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
		super();
		this.appUserRepository = appUserRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = appUserRepository.findOneByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User does not exist.");
		}
		
		List<GrantedAuthority> userAuthorities = Arrays
				.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
		User userDetails = new User(user.getUsername(), user.getPasswordHash(), userAuthorities);
		return userDetails;
	}

}
