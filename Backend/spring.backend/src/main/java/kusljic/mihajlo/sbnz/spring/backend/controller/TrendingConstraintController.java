package kusljic.mihajlo.sbnz.spring.backend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kusljic.mihajlo.sbnz.spring.backend.dto.TrendingConstraintDTO;
import kusljic.mihajlo.sbnz.spring.backend.facts.TrendingConstraint;
import kusljic.mihajlo.sbnz.spring.backend.service.TrendingConstraintService;

@RestController
@RequestMapping(value = "/api/trending-constraint")
@CrossOrigin
public class TrendingConstraintController {
	
	private TrendingConstraintService trendingConstraintService;

	@Autowired
	public TrendingConstraintController(TrendingConstraintService trendingConstraintService) {
		super();
		this.trendingConstraintService = trendingConstraintService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<List<TrendingConstraintDTO>> getTrendingConstraint() {
		TrendingConstraint constraint = this.trendingConstraintService.getTrendingConstraint();
		List<TrendingConstraintDTO> result = new ArrayList<TrendingConstraintDTO>();
		if (constraint != null) {
			result.add(new TrendingConstraintDTO(constraint));
		}
		
		return new ResponseEntity<List<TrendingConstraintDTO>>(result, HttpStatus.OK);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<TrendingConstraintDTO> setTrendingConstraint(@RequestBody @Valid TrendingConstraintDTO newConstraintDTO) {
		TrendingConstraint newConstraint = this.fromDTO(newConstraintDTO);
		newConstraint = this.trendingConstraintService.setTrendingConstraint(newConstraint);
		return new ResponseEntity<TrendingConstraintDTO>(newConstraintDTO, HttpStatus.OK);
	}
	
	private TrendingConstraint fromDTO(TrendingConstraintDTO dto) {
		TrendingConstraint constraint = new TrendingConstraint();
		constraint.setTimeWindow(String.format("%dd", dto.getPreviousDaysCount()));
		constraint.setMinimumRecommendations(dto.getMinimumRecommendations());
		return constraint;
	}

}
