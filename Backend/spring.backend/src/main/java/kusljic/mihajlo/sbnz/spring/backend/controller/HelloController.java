package kusljic.mihajlo.sbnz.spring.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller used for testing, to check if the API works.
 * @author kusljic
 */
@RestController
@RequestMapping("/api/hello")
public class HelloController {

	@GetMapping
	public ResponseEntity<String> greet() {
		return new ResponseEntity<String>("Hello from car recommendation system.", HttpStatus.OK);
	}

}
