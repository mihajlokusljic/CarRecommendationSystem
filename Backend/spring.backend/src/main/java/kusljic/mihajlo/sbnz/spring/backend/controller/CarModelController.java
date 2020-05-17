package kusljic.mihajlo.sbnz.spring.backend.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kusljic.mihajlo.sbnz.spring.backend.dto.CarModelDTO;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.service.CarManufacturerService;
import kusljic.mihajlo.sbnz.spring.backend.service.CarModelService;

@RestController
@RequestMapping(value = "api/car-models")
public class CarModelController {
	
	private CarModelService carModelService;
	private CarManufacturerService carManufacturerService;


	@Autowired
	public CarModelController(CarModelService carModelService, CarManufacturerService carManufacturerService) {
		super();
		this.carModelService = carModelService;
		this.carManufacturerService = carManufacturerService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<CarModelDTO> addCarModel(@RequestBody CarModelDTO newModelData) {
		CarModel newCarModel = this.buildFromDTO(newModelData);
		newCarModel = this.carModelService.create(newCarModel);
		CarModelDTO result = new CarModelDTO(newCarModel);
		return new ResponseEntity<CarModelDTO>(result, HttpStatus.CREATED);
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
		if(manufacturer == null) {
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
