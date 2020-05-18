package kusljic.mihajlo.sbnz.spring.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;

public interface CarManufacturerService {
	
	CarManufacturer findById(Long id);
	
	List<CarManufacturer> findAll();
	
	Page<CarManufacturer> findByPage(Pageable page);
	
	CarManufacturer create(CarManufacturer manufacturer);
	
	CarManufacturer update(CarManufacturer manufacturer);
	
	void delete(Long manufacturerId);

}
