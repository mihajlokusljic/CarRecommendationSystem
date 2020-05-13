package kusljic.mihajlo.sbnz.spring.backend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kusljic.mihajlo.sbnz.spring.backend.dto.LoginRequestDTO;
import kusljic.mihajlo.sbnz.spring.backend.dto.LoginResponseDTO;
import kusljic.mihajlo.sbnz.spring.backend.util.JwtUtil;



@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtils;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
		try {
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), 
							loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			// Create JSON web token for user
			User user = (User) authentication.getPrincipal();
			String jwt = jwtUtils.generateToken(user);
			
			// Return token for successful authentication
			LoginResponseDTO response = new LoginResponseDTO(jwt);
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<String>("Invalid username or password", HttpStatus.BAD_REQUEST);
		}
	}

}
