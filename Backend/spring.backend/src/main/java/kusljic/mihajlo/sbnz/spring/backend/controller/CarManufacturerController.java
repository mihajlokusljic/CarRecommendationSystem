package kusljic.mihajlo.sbnz.spring.backend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@CrossOrigin
public class CarManufacturerController {

	private CarManufacturerService carManufacturerService;
	private CountryService countryService;

	@Autowired
	public CarManufacturerController(CarManufacturerService carManufacturerService, CountryService countryService) {
		super();
		this.carManufacturerService = carManufacturerService;
		this.countryService = countryService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CarManufacturerDTO> getManufacturer(@PathVariable("id") Long id) {
		CarManufacturer manufacturer = this.carManufacturerService.findById(id);
		if (manufacturer == null) {
			throw new EntityNotFoundException("Given car manufacturer does not exist.");
		}
		CarManufacturerDTO result = new CarManufacturerDTO(manufacturer);
		return new ResponseEntity<CarManufacturerDTO>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<CarManufacturerDTO>> getAllManufacturers() {
		List<CarManufacturer> manufacturers = this.carManufacturerService.findAll();
		List<CarManufacturerDTO> result = new ArrayList<CarManufacturerDTO>();
		for (CarManufacturer carManufacturer : manufacturers) {
			result.add(new CarManufacturerDTO(carManufacturer));
		}
		return new ResponseEntity<List<CarManufacturerDTO>>(result, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Page<CarManufacturerDTO>> getManufacturers(Pageable page) {
		Page<CarManufacturer> manufacturers = this.carManufacturerService.findByPage(page);
		List<CarManufacturerDTO> content = new ArrayList<CarManufacturerDTO>();
		for (CarManufacturer carManufacturer : manufacturers) {
			content.add(new CarManufacturerDTO(carManufacturer));
		}
		Page<CarManufacturerDTO> result = new PageImpl<CarManufacturerDTO>(content, manufacturers.getPageable(),
				manufacturers.getTotalElements());
		return new ResponseEntity<Page<CarManufacturerDTO>>(result, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<CarManufacturerDTO> addManufacturer(@RequestBody CarManufacturerDTO newManufacturerData) {
		CarManufacturer newManufacturer = this.buildFromDTO(newManufacturerData);
		newManufacturer = this.carManufacturerService.create(newManufacturer);
		CarManufacturerDTO result = new CarManufacturerDTO(newManufacturer);
		return new ResponseEntity<CarManufacturerDTO>(result, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<CarManufacturerDTO> modifyManufacturer(@RequestBody CarManufacturerDTO newManufacturerData) {
		CarManufacturer manufacturer = this.buildFromDTO(newManufacturerData);
		manufacturer = this.carManufacturerService.update(manufacturer);
		CarManufacturerDTO result = new CarManufacturerDTO(manufacturer);
		return new ResponseEntity<CarManufacturerDTO>(result, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Void> deleteManufacturer(@PathVariable("id") Long id) {
		this.carManufacturerService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private CarManufacturer buildFromDTO(CarManufacturerDTO dto) {
		CarManufacturer result = new CarManufacturer();
		result.setId(dto.getId());
		result.setName(dto.getName());
		Country countryofOrigin = this.countryService.findById(dto.getCountryId());
		if (countryofOrigin == null) {
			throw new InvalidFieldException("Invalid country for car manufacturer.");
		}
		result.setCountryOfOrigin(countryofOrigin);
		return result;
	}

}
