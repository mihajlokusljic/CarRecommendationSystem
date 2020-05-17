package kusljic.mihajlo.sbnz.spring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {
	
	CarModel findOneByName(String name);

}
