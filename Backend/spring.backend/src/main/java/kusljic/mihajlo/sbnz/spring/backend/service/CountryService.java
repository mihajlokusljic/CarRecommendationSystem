package kusljic.mihajlo.sbnz.spring.backend.service;

import java.util.List;

import kusljic.mihajlo.sbnz.spring.backend.facts.Country;

public interface CountryService {
	
	Country findById(Long id);
	
	List<Country> findAll();

}
