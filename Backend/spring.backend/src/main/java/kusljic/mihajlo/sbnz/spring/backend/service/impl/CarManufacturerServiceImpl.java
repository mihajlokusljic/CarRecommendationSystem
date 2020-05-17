package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.exception.EntityAlreadyExistsException;
import kusljic.mihajlo.sbnz.spring.backend.exception.InvalidFieldException;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;
import kusljic.mihajlo.sbnz.spring.backend.repository.CarManufacturerRepository;
import kusljic.mihajlo.sbnz.spring.backend.service.CarManufacturerService;

@Service
public class CarManufacturerServiceImpl implements CarManufacturerService {
	
	private CarManufacturerRepository carManufacturerRepository;
	
	@Autowired
	public CarManufacturerServiceImpl(CarManufacturerRepository carManufacturerRepository) {
		super();
		this.carManufacturerRepository = carManufacturerRepository;
	}

	@Override
	public CarManufacturer findById(Long id) {
		return this.carManufacturerRepository.findById(id).orElse(null);
	}

	@Override
	public List<CarManufacturer> findAll() {
		return this.carManufacturerRepository.findAll();
	}

	@Override
	public void delete(Long manufacturerId) {
		CarManufacturer toDelete = this.findById(manufacturerId);
		if(toDelete == null) {
			throw new EntityNotFoundException(String.format("Car manufacturer with id %d does not exist", manufacturerId));
		}
		this.carManufacturerRepository.delete(toDelete);
	}
	
	@Override
	public CarManufacturer create(CarManufacturer manufacturer) {
		this.checkManufacturerData(manufacturer);
		CarManufacturer sameManufacturer = this.carManufacturerRepository.findOneByName(manufacturer.getName());
		if(sameManufacturer != null) {
			throw new EntityAlreadyExistsException(String.format("Car manufacturer %s already exists.", manufacturer.getName()));
		}
		return this.carManufacturerRepository.save(manufacturer);
	}

	@Override
	public CarManufacturer update(CarManufacturer manufacturer) {
		this.checkManufacturerData(manufacturer);
		CarManufacturer manufacturerToUpdate = this.findById(manufacturer.getId());
		if(manufacturerToUpdate == null) {
			throw new EntityNotFoundException("The given car manufacturer does not exist.");
		}
		return this.carManufacturerRepository.save(manufacturer);
	}
	
	private void checkManufacturerData(CarManufacturer manufacturer) {
		if(manufacturer.getName() == null || manufacturer.getName().equals("")) {
			throw new InvalidFieldException("Manufacturer name is required");
		}
		
		if(manufacturer.getCountryOfOrigin() == null) {
			throw new InvalidFieldException("Manufacturer's country of origin is required");
		}
	}

}
