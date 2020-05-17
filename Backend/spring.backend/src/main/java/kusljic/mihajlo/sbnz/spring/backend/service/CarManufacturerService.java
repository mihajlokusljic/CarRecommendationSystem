package kusljic.mihajlo.sbnz.spring.backend.service;

import java.util.List;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;

public interface CarManufacturerService {
	
	CarManufacturer findById(Long id);
	
	List<CarManufacturer> findAll();
	
	CarManufacturer create(CarManufacturer manufacturer);
	
	CarManufacturer update(CarManufacturer manufacturer);
	
	void delete(Long manufacturerId);

}
