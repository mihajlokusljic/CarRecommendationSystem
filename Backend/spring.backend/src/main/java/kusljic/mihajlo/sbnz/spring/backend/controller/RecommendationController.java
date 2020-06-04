package kusljic.mihajlo.sbnz.spring.backend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kusljic.mihajlo.sbnz.spring.backend.dto.RecommendationDTO;
import kusljic.mihajlo.sbnz.spring.backend.dto.RecommendationQueryDTO;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.facts.Recommendation;
import kusljic.mihajlo.sbnz.spring.backend.facts.RecommendationQuery;
import kusljic.mihajlo.sbnz.spring.backend.service.CountryService;
import kusljic.mihajlo.sbnz.spring.backend.service.KnowledgeEngineService;

@RestController
@RequestMapping(value = "/api/recommendations")
public class RecommendationController {
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private KnowledgeEngineService knowledgeEngineService;
	
	@PostMapping
	public ResponseEntity<List<RecommendationDTO>> getRecommendations(@RequestBody RecommendationQueryDTO queryDto) {
		RecommendationQuery query = this.readQuery(queryDto);
		
		List<Recommendation> recommendations = this.knowledgeEngineService.generateRecommendations(query);
		List<RecommendationDTO> result = new ArrayList<RecommendationDTO>();
		for (Recommendation recommendation : recommendations) {
			result.add(new RecommendationDTO(recommendation));
		}
		
		return new ResponseEntity<List<RecommendationDTO>>(result, HttpStatus.OK);
	}
	
	private RecommendationQuery readQuery(RecommendationQueryDTO queryDto) {
		Country userCountry = this.countryService.findById(queryDto.getUserCountryId());
		if (userCountry == null) {
			throw new EntityNotFoundException("Invalid country.");
		}
		
		
		RecommendationQuery query = new RecommendationQuery();
		query.setBeginner(queryDto.isBeginner());
		query.setBudget(queryDto.getBudget());
		query.setForCargoTransport(queryDto.isForCargoTransport());
		query.setForCityTraffic(queryDto.isForCityTraffic());
		query.setForOffroading(queryDto.isForOffroading());
		query.setForSport(queryDto.isForSport());
		query.setForTravelling(queryDto.isForTravelling());
		query.setHasFamily(queryDto.isHasFamily());
		query.setNeedsConnectivity(queryDto.isNeedsConnectivity());
		query.setUsersCountry(userCountry);
		return query;
	}

}
