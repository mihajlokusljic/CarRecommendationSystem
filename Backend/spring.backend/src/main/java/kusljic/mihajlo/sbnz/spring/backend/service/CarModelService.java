package kusljic.mihajlo.sbnz.spring.backend.service;

import java.util.List;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;

public interface CarModelService {
	
	CarModel findById(Long id);
	
	List<CarModel> findAll();
	
	CarModel create(CarModel newCarModel);
	
	CarModel update(CarModel carModel);
	
	void delete(Long carModelId);

}
