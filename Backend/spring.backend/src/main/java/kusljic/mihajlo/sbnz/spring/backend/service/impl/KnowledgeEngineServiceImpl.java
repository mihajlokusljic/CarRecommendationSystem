package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.facts.Recommendation;
import kusljic.mihajlo.sbnz.spring.backend.facts.RecommendationQuery;
import kusljic.mihajlo.sbnz.spring.backend.service.CarModelService;
import kusljic.mihajlo.sbnz.spring.backend.service.CountryService;
import kusljic.mihajlo.sbnz.spring.backend.service.KnowledgeEngineService;

@Service
public class KnowledgeEngineServiceImpl implements KnowledgeEngineService {
	
	private final KieContainer kieContainer;
	private CarModelService carModelService;
	private KieSession kieSession;
	
	@Autowired
	private CountryService countryService;
	
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
		
		RecommendationQuery testQuery = new RecommendationQuery();
		testQuery.setBeginner(true);
		testQuery.setBudget(100000);
		testQuery.setForCityTraffic(true);
		// testQuery.setForTravelling(true);
		testQuery.setNeedsConnectivity(true);
		Country serbia = this.countryService.findById(190L);
		testQuery.setUsersCountry(serbia);
		this.generateRecommendations(testQuery);
	}

	@Override
	public List<Recommendation> generateRecommendations(RecommendationQuery query) {
		
		// Insert the latest query data into session
		FactHandle queryHandle = this.kieSession.insert(query);
		
		// Run rules to generate recommendations according to generated observations and query data
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("recommendations").setFocus();
		agenda.getAgendaGroup("conformances to user").setFocus();
		this.kieSession.fireAllRules();
		
		// Read generated recommendations and delete them so they don't polute next query results
		List<Recommendation> result = new ArrayList<Recommendation>();
		QueryResults results = kieSession.getQueryResults( "Fetch recommendations" );
		System.out.println( "we have " + results.size() + " recommended car models:" );

		for ( QueryResultsRow row : results ) {
			Recommendation rec = ( Recommendation ) row.get( "$r" );
			result.add(rec);
		    System.out.println(rec.getCarModel());
		    this.kieSession.delete(row.getFactHandle("$r"));
		}
		
		// Remove query data from session so next query can be accepted
		this.kieSession.delete(queryHandle);
		
		// TODO Auto-generated method stub
		return result;
	}

}
