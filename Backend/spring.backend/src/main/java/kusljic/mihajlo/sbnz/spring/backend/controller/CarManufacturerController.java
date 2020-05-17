package kusljic.mihajlo.sbnz.spring.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kusljic.mihajlo.sbnz.spring.backend.dto.CarManufacturerDTO;
import kusljic.mihajlo.sbnz.spring.backend.exception.InvalidFieldException;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.service.CarManufacturerService;
import kusljic.mihajlo.sbnz.spring.backend.service.CountryService;

@RestController
@RequestMapping(value = "api/car-manufacturers")
public class CarManufacturerController {
	
	private CarManufacturerService carManufacturerService;
	private CountryService countryService;
	
	@Autowired
	public CarManufacturerController(CarManufacturerService carManufacturerService, CountryService countryService) {
		super();
		this.carManufacturerService = carManufacturerService;
		this.countryService = countryService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<CarManufacturerDTO> addManufacturer(@RequestBody CarManufacturerDTO newManufacturerData) {
		CarManufacturer newManufacturer = this.buildFromDTO(newManufacturerData);
		newManufacturer = this.carManufacturerService.create(newManufacturer);
		CarManufacturerDTO result = new CarManufacturerDTO(newManufacturer);
		return new ResponseEntity<CarManufacturerDTO>(result, HttpStatus.CREATED);
	}
	
	private CarManufacturer buildFromDTO(CarManufacturerDTO dto) {
		CarManufacturer result = new CarManufacturer();
		result.setId(dto.getId());
		result.setName(dto.getName());
		Country countryofOrigin = this.countryService.findById(dto.getCountryId());
		if(countryofOrigin == null) {
			throw new InvalidFieldException("Invalid country for car manufacturer.");
		}
		result.setCountryOfOrigin(countryofOrigin);
		return result;
	}

}
