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
import kusljic.mihajlo.sbnz.spring.backend.dto.CarModelDTO;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.service.CarManufacturerService;
import kusljic.mihajlo.sbnz.spring.backend.service.CarModelService;

@RestController
@RequestMapping(value = "api/car-models")
@CrossOrigin
public class CarModelController {

	private CarModelService carModelService;
	private CarManufacturerService carManufacturerService;

	@Autowired
	public CarModelController(CarModelService carModelService, CarManufacturerService carManufacturerService) {
		super();
		this.carModelService = carModelService;
		this.carManufacturerService = carManufacturerService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CarModelDTO> getCarModel(@PathVariable("id") Long id) {
		CarModel model = this.carModelService.findById(id);
		if (model == null) {
			throw new EntityNotFoundException("Requested car model does not exist.");
		}
		CarModelDTO result = new CarModelDTO(model);
		return new ResponseEntity<CarModelDTO>(result, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Page<CarModelDTO>> getCarModels(Pageable page) {
		Page<CarModel> models = this.carModelService.findByPage(page);
		List<CarModelDTO> content = new ArrayList<CarModelDTO>();
		for (CarModel carModel : models) {
			content.add(new CarModelDTO(carModel));
		}
		Page<CarModelDTO> result = new PageImpl<CarModelDTO>(content, models.getPageable(), models.getTotalElements());
		return new ResponseEntity<Page<CarModelDTO>>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "/trending")
	public ResponseEntity<Page<CarModelDTO>> getTrendingCarModels(Pageable page) {
		Page<CarModel> models = this.carModelService.findTrendingCarModels(page);
		List<CarModelDTO> content = new ArrayList<CarModelDTO>();
		for (CarModel carModel : models) {
			content.add(new CarModelDTO(carModel));
		}
		Page<CarModelDTO> result = new PageImpl<CarModelDTO>(content, models.getPageable(), models.getTotalElements());
		return new ResponseEntity<Page<CarModelDTO>>(result, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<CarModelDTO> addCarModel(@RequestBody CarModelDTO newModelData) {
		CarModel newCarModel = this.buildFromDTO(newModelData);
		newCarModel = this.carModelService.create(newCarModel);
		CarModelDTO result = new CarModelDTO(newCarModel);
		return new ResponseEntity<CarModelDTO>(result, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<CarModelDTO> modifyCarModel(@RequestBody CarModelDTO newModelData) {
		CarModel model = this.buildFromDTO(newModelData);
		model = this.carModelService.update(model);
		CarModelDTO result = new CarModelDTO(model);
		return new ResponseEntity<CarModelDTO>(result, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Void> deleteCarModel(@PathVariable("id") Long id) {
		this.carModelService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private CarModel buildFromDTO(CarModelDTO dto) {
		CarModel result = new CarModel();
		result.setId(dto.getId());
		result.setBasePriceEuros(dto.getBasePriceEuros());
		result.setBluetoothConnective(dto.isBluetoothConnective());
		result.setBootCapacityLitres(dto.getBootCapacityLitres());
		result.setDoorsNumber(dto.getDoorsNumber());
		result.setEngineDisplacementCcm(dto.getEngineDisplacementCcm());
		result.setEnginePowerBhp(dto.getEnginePowerBhp());
		result.setFuelType(dto.getFuelType());
		result.setHavingNavigationSystem(dto.isHavingNavigationSystem());
		result.setHavingParkingSensors(dto.isHavingParkingSensors());
		result.setHavingPassangerAirbags(dto.isHavingPassangerAirbags());
		result.setHavingRearCamera(dto.isHavingRearCamera());
		CarManufacturer manufacturer = this.carManufacturerService.findById(dto.getManufacturerId());
		if (manufacturer == null) {
			throw new EntityNotFoundException("Invalid car manufacturer given in car model.");
		}
		result.setManufacturer(manufacturer);
		result.setMileage(dto.getMileage());
		result.setName(dto.getName());
		result.setSeatsNumber(dto.getSeatsNumber());
		result.setSupportingChildSeatMounts(dto.isSupportingChildSeatMounts());
		result.setTopSpeedKmh(dto.getTopSpeedKmh());
		result.setTransmission(dto.getTransmission());
		result.setType(dto.getType());
		return result;
	}

}
