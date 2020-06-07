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
import kusljic.mihajlo.sbnz.spring.backend.facts.ObservationType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Recommendation;
import kusljic.mihajlo.sbnz.spring.backend.facts.RecommendationQuery;
import kusljic.mihajlo.sbnz.spring.backend.service.CarModelService;
import kusljic.mihajlo.sbnz.spring.backend.service.CountryService;
import kusljic.mihajlo.sbnz.spring.backend.service.KnowledgeEngineService;
import kusljic.mihajlo.sbnz.spring.backend.util.RecommendationComparator;

@Service
public class KnowledgeEngineServiceImpl implements KnowledgeEngineService {

	private final KieContainer kieContainer;
	private CarModelService carModelService;
	private KieSession kieSession;

	@Autowired
	private CountryService countryService;

	@Value("${maxRecommendatons}")
	private Integer maxRecommendations;

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
		System.out.println("Loaded car models.");

		// generate global observations about car models
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("global observations").setFocus();
		this.kieSession.fireAllRules();

		RecommendationQuery testQuery = new RecommendationQuery();
		testQuery.setBudget(100000);
		Country serbia = this.countryService.findById(190L);
		testQuery.setUsersCountry(serbia);
		List<ObservationType> relevantObservations = Arrays.asList(ObservationType.BEGINNER_FRIENDLY,
				ObservationType.CITY_FRIENDLY, ObservationType.CONNECTIVITY_SUPPORTED);
		testQuery.setRequestedFeatures(relevantObservations);
		this.generateRecommendations(testQuery);
	}

	@Override
	public List<Recommendation> generateRecommendations(RecommendationQuery query) {
		// Insert the latest query data into session
		FactHandle queryHandle = this.kieSession.insert(query);

		// Run rules to generate recommendations according to generated observations and
		// query data
		Agenda agenda = this.kieSession.getAgenda();
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
	
	@PreDestroy
	private void releaseSession() {
		this.kieSession.dispose();
	}

}
