package kusljic.mihajlo.sbnz.spring.backend.service;

import java.util.List;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.facts.LoginAttempt;
import kusljic.mihajlo.sbnz.spring.backend.facts.Recommendation;
import kusljic.mihajlo.sbnz.spring.backend.facts.RecommendationQuery;

public interface KnowledgeEngineService {
	
	List<Recommendation> generateRecommendations(RecommendationQuery query);
	
	LoginAttempt registerLoginAttempt(LoginAttempt loginAttemp);
	
	boolean isAccountBlocked(String username);
	
	CarModel processNewCarModel(CarModel newModel);
	
	boolean removeCarModelData(CarModel modelToRemove);

}
