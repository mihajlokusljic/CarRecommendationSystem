package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.service.CarModelService;
import kusljic.mihajlo.sbnz.spring.backend.service.KnowledgeEngineService;

@Service
public class KnowledgeEngineServiceImpl implements KnowledgeEngineService {
	
	private final KieContainer kieContainer;
	private CarModelService carModelService;
	private KieSession kieSession;
	
	@Autowired
	public KnowledgeEngineServiceImpl(KieContainer kieContainer, CarModelService carModelService) {
		super();
		this.kieContainer = kieContainer;
		this.carModelService = carModelService;
	}
	
	@PostConstruct
	private void initializeSession() {
		this.kieSession = kieContainer.newKieSession();
		
		// populate session with facts about car models
		List<CarModel> carModels = this.carModelService.findAll();
		for (CarModel carModel : carModels) {
			this.kieSession.insert(carModel);
		}
		
		// generate global observations about car models
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("global observations").setFocus();
		this.kieSession.fireAllRules();
	}

}
