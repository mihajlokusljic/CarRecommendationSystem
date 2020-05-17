package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.exception.EntityAlreadyExistsException;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.repository.CarModelRepository;
import kusljic.mihajlo.sbnz.spring.backend.service.CarModelService;

@Service
public class CarModelServiceImpl implements CarModelService {
	
	private CarModelRepository carModelRepository;
	
	@Autowired
	public CarModelServiceImpl(CarModelRepository carModelRepository) {
		super();
		this.carModelRepository = carModelRepository;
	}

	@Override
	public CarModel findById(Long id) {
		return this.carModelRepository.findById(id).orElse(null);
	}

	@Override
	public List<CarModel> findAll() {
		return this.carModelRepository.findAll();
	}

	@Override
	public CarModel create(CarModel newCarModel) {
		CarModel sameCarModel = this.carModelRepository.findOneByName(newCarModel.getName());
		if(sameCarModel != null) {
			throw new EntityAlreadyExistsException(String.format("Car model with name %s already exists.", newCarModel.getName()));
		}
		return this.carModelRepository.save(newCarModel);
	}

	@Override
	public CarModel update(CarModel carModel) {
		CarModel modelToUpdate = this.findById(carModel.getId());
		if(modelToUpdate == null) {
			throw new EntityNotFoundException("The given car model does not exist.");
		}
		return this.carModelRepository.save(carModel);
	}

	@Override
	public void delete(Long carModelId) {
		CarModel toDelete = this.findById(carModelId);
		if(toDelete == null) {
			throw new EntityNotFoundException(String.format("Car model with id %d does not exist", carModelId));
		}
		this.carModelRepository.delete(toDelete);

	}

}
