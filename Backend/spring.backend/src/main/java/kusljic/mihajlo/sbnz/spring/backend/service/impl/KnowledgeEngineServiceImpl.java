package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.facts.LoginAttempt;
import kusljic.mihajlo.sbnz.spring.backend.facts.ObservationType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Recommendation;
import kusljic.mihajlo.sbnz.spring.backend.facts.RecommendationQuery;
import kusljic.mihajlo.sbnz.spring.backend.repository.CarModelRepository;
import kusljic.mihajlo.sbnz.spring.backend.service.CountryService;
import kusljic.mihajlo.sbnz.spring.backend.service.KnowledgeEngineService;
import kusljic.mihajlo.sbnz.spring.backend.util.RecommendationComparator;

@Service
public class KnowledgeEngineServiceImpl implements KnowledgeEngineService {

	private KieSession kieSession;
	
	private KieContainer kieContainer;
	
	private CountryService countryService;
	
	private CarModelRepository carModelRepository;

	@Value("${maxRecommendatons}")
	private Integer maxRecommendations;
	
	@Autowired
	public KnowledgeEngineServiceImpl(KieContainer kieContainer, CountryService countryService,
			CarModelRepository carModelRepository) {
		super();
		this.kieContainer = kieContainer;
		this.countryService = countryService;
		this.carModelRepository = carModelRepository;
	}

	@PostConstruct
	private void initializeSession() {
		this.kieSession = kieContainer.newKieSession("rules-session");
		
		// set global variables
		this.kieSession.setGlobal("carModelRepository", this.carModelRepository);

		// populate session with facts about car models
		List<CarModel> carModels = this.carModelRepository.findAll();
		for (CarModel carModel : carModels) {
			this.kieSession.insert(carModel);
		}
		System.out.println("Loaded car models.");

		// generate global observations about car models
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("trending").setFocus();
		agenda.getAgendaGroup("global observations").setFocus();
		this.kieSession.fireAllRules();
	}

	@Override
	public List<Recommendation> generateRecommendations(RecommendationQuery query) {
		// Insert the latest query data into session
		FactHandle queryHandle = this.kieSession.insert(query);

		// Run rules to generate recommendations according to generated observations and
		// query data
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("trending").setFocus();
		agenda.getAgendaGroup("recommendations").setFocus();
		agenda.getAgendaGroup("conformances to user").setFocus();
		this.kieSession.fireAllRules();

		// Read generated recommendations and delete them so they don't pollute next
		// query results
		List<Recommendation> result = new ArrayList<Recommendation>();
		QueryResults recommendations = this.kieSession.getQueryResults("Fetch recommendations");
		System.out.println("we have " + recommendations.size() + " recommended car models.");

		for (QueryResultsRow row : recommendations) {
			Recommendation rec = (Recommendation) row.get("$r");
			result.add(rec);
			this.kieSession.delete(row.getFactHandle("$r"));
		}
		
		// Leave top recommendations
		Collections.sort(result, new RecommendationComparator());
		result = result.subList(0, min(result.size(), this.maxRecommendations));
		
		// Read and delete generated conformances so that they don't pollute next query results
		QueryResults conformances = this.kieSession.getQueryResults("Fetch conformances");
		for (QueryResultsRow row : conformances) {
			this.kieSession.delete(row.getFactHandle("$c"));
		}

		// Remove query data from session so next query can be accepted
		this.kieSession.delete(queryHandle);
		
		

		return result;
	}
	
	@Override
	public LoginAttempt registerLoginAttempt(LoginAttempt loginAttemp) {
		// TODO Auto-generated method stub
		this.kieSession.insert(loginAttemp);
		
		// Block accounts with too many failed login attempts
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("brute force prevention").setFocus();
		this.kieSession.fireAllRules();
		
		return loginAttemp;
	}

	@Override
	public boolean isAccountBlocked(String username) {
		QueryResults accountLocks = this.kieSession.getQueryResults("Fetch locks for account", username);
		return accountLocks.size() > 0;
	}
	
	@Override
	public CarModel processNewCarModel(CarModel newModel) {
		// insert new car model into session as a fact
		this.kieSession.insert(newModel);
		
		// generate observation facts for new car model
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("global observations").setFocus();
		this.kieSession.fireAllRules();
		
		return newModel;
	}
	
	@Override
	public boolean removeCarModelData(CarModel modelToRemove) {
		// TODO Auto-generated method stub
		QueryResults modelResults = this.kieSession.getQueryResults("Fetch car model", modelToRemove);
		if (modelResults.size() == 0) {
			return false;
		}
		
		// Fetch and remove observation facts about car model
		QueryResults observations = this.kieSession.getQueryResults("Fetch observations for car model", modelToRemove);
		for (QueryResultsRow row : observations) {
			this.kieSession.delete(row.getFactHandle("$o"));
		}
		
		// Remove fact about car model
		for (QueryResultsRow row : modelResults) {
			this.kieSession.delete(row.getFactHandle("$cm"));
		}

		return true;
	}
	
	@Override
	public void updateTrending() {
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("trending").setFocus();
		this.kieSession.fireAllRules();
	}
	
	@PreDestroy
	private void releaseSession() {
		this.kieSession.dispose();
	}

}
