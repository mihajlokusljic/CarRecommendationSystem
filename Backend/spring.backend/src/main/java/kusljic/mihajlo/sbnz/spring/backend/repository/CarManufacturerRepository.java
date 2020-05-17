package kusljic.mihajlo.sbnz.spring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;

public interface CarManufacturerRepository extends JpaRepository<CarManufacturer, Long> {
	
	CarManufacturer findOneByName(String name);

}
