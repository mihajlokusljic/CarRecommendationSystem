package kusljic.mihajlo.sbnz.spring.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kusljic.mihajlo.sbnz.spring.backend.dto.CountryDTO;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.service.CountryService;

@RestController
@RequestMapping(value = "api/countries")
public class CountryController {
	
	private CountryService countryService;
	
	@Autowired
	public CountryController(CountryService countryService) {
		super();
		this.countryService = countryService;
	}



	@GetMapping
	public ResponseEntity<List<CountryDTO>> getAllCountries() {
		List<Country> countries = this.countryService.findAll();
		List<CountryDTO> result = new ArrayList<CountryDTO>();
		for (Country country : countries) {
			result.add(new CountryDTO(country));
		}
		return new ResponseEntity<List<CountryDTO>>(result, HttpStatus.OK);
	}

}
