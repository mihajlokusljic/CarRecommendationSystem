package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import javax.annotation.PostConstruct;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;

@Service
public class SampleAppService {

	private static Logger log = LoggerFactory.getLogger(SampleAppService.class);

	private final KieContainer kieContainer;

	@Autowired
	public SampleAppService(KieContainer kieContainer) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
	}

	@PostConstruct
	public void testRules() {
		KieSession kieSession = kieContainer.newKieSession();
		CarModel example = new CarModel();
		Country madeUp = new Country("Atlantic", "AT", 0, 0);
		CarManufacturer madeUpManufacturer = new CarManufacturer("Aquacar", "", madeUp);
		example.setName("Orca");
		example.setManufacturer(madeUpManufacturer);
		example.setDoorsNumber(4);
		example.setSeatsNumber(5);
		
		kieSession.insert(example);
		int count = kieSession.fireAllRules();
		kieSession.dispose();
		System.out.println(String.format("Fired %d rules.", count));
	}
}
