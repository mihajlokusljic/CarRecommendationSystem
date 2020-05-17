package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.repository.CountryRepository;
import kusljic.mihajlo.sbnz.spring.backend.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
	
	private CountryRepository countryRepository;
	
	@Autowired
	public CountryServiceImpl(CountryRepository countryRepository) {
		super();
		this.countryRepository = countryRepository;
	}

	@Override
	public Country findById(Long id) {
		return this.countryRepository.findById(id).orElse(null);
	}

	@Override
	public List<Country> findAll() {
		return this.countryRepository.findAll();
	}

}
