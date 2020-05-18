package kusljic.mihajlo.sbnz.spring.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;

public interface CarModelService {
	
	CarModel findById(Long id);
	
	List<CarModel> findAll();
	
	Page<CarModel> findByPage(Pageable page);
	
	CarModel create(CarModel newCarModel);
	
	CarModel update(CarModel carModel);
	
	void delete(Long carModelId);

}
