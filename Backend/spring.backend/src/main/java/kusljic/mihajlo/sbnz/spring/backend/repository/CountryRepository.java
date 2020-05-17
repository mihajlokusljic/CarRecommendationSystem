package kusljic.mihajlo.sbnz.spring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kusljic.mihajlo.sbnz.spring.backend.facts.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
