package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.exception.EntityAlreadyExistsException;
import kusljic.mihajlo.sbnz.spring.backend.exception.InvalidFieldException;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.repository.CarModelRepository;
import kusljic.mihajlo.sbnz.spring.backend.service.CarModelService;
import kusljic.mihajlo.sbnz.spring.backend.service.KnowledgeEngineService;

@Service
public class CarModelServiceImpl implements CarModelService {
	
	private CarModelRepository carModelRepository;
	private KnowledgeEngineService knowledgeEngineService;
	
	@Autowired
	public CarModelServiceImpl(CarModelRepository carModelRepository, KnowledgeEngineService knowledgeEngineService) {
		super();
		this.carModelRepository = carModelRepository;
		this.knowledgeEngineService = knowledgeEngineService;
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
		this.checkCarModelData(newCarModel);
		CarModel sameCarModel = this.carModelRepository.findOneByName(newCarModel.getName());
		if(sameCarModel != null) {
			throw new EntityAlreadyExistsException(String.format("Car model with name %s already exists.", newCarModel.getName()));
		}
		CarModel result = this.carModelRepository.save(newCarModel);
		this.knowledgeEngineService.processNewCarModel(result);
		return result;
	}

	@Override
	public CarModel update(CarModel carModel) {
		this.checkCarModelData(carModel);
		CarModel modelToUpdate = this.findById(carModel.getId());
		if(modelToUpdate == null) {
			throw new EntityNotFoundException("The given car model does not exist.");
		}
		
		this.knowledgeEngineService.removeCarModelData(modelToUpdate);
		CarModel modifiedCarmodel = this.carModelRepository.save(carModel);
		this.knowledgeEngineService.processNewCarModel(modifiedCarmodel);
		return modifiedCarmodel;
	}

	@Override
	public void delete(Long carModelId) {
		CarModel toDelete = this.findById(carModelId);
		if(toDelete == null) {
			throw new EntityNotFoundException(String.format("Car model with id %d does not exist", carModelId));
		}
		this.knowledgeEngineService.removeCarModelData(toDelete);
		this.carModelRepository.delete(toDelete);
	}

	@Override
	public Page<CarModel> findByPage(Pageable page) {
		return this.carModelRepository.findAll(page);
	}
	
	private void checkCarModelData(CarModel carModel) {
		if(carModel.getBasePriceEuros() <= 0) {
			throw new InvalidFieldException("Base price must be greater than zero.");
		}
		if(carModel.getBootCapacityLitres() <= 0) {
			throw new InvalidFieldException("Boot capacity must be greater than zero.");
		}
		if(carModel.getDoorsNumber() <= 0) {
			throw new InvalidFieldException("Doors number must be greater than zero.");
		}
		if(carModel.getEngineDisplacementCcm() <= 0) {
			throw new InvalidFieldException("Engine displacement must be greater than zero.");
		}
		if(carModel.getEnginePowerBhp() <= 0) {
			throw new InvalidFieldException("Engine power must be greater than zero.");
		}
		if(carModel.getMileage() <= 0) {
			throw new InvalidFieldException("Mileage must be greater than zero.");
		}
		if(carModel.getName() == null || carModel.getName().contentEquals("")) {
			throw new InvalidFieldException("Name is required.");
		}
		if(carModel.getSeatsNumber() <= 0) {
			throw new InvalidFieldException("Seats number must be greater than zero.");
		}
		if(carModel.getTopSpeedKmh() <= 0) {
			throw new InvalidFieldException("Top speed must be greater than zero.");
		}
	}

}
