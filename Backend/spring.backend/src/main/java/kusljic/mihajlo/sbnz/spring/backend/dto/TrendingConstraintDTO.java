package kusljic.mihajlo.sbnz.spring.backend.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import kusljic.mihajlo.sbnz.spring.backend.facts.TrendingConstraint;

public class TrendingConstraintDTO {
	
	@Min(1)
	@Max(31)
	private Integer previousDaysCount;
	
	@Min(1)
	private Integer minimumRecommendations;
	
	public TrendingConstraintDTO() {
		super();
	}
	
	public TrendingConstraintDTO(@Min(1) Integer previousDaysCount, @Min(1) Integer minimumRecommendations) {
		super();
		this.previousDaysCount = previousDaysCount;
		this.minimumRecommendations = minimumRecommendations;
	}
	
	public TrendingConstraintDTO(TrendingConstraint constraint) {
		int daysCountEnd = constraint.getTimeWindow().lastIndexOf("d");
		String daysCount = constraint.getTimeWindow().substring(0, daysCountEnd);
		Integer days = Integer.parseInt(daysCount);
		this.previousDaysCount = days;
		this.minimumRecommendations = constraint.getMinimumRecommendations();
	}

	public Integer getPreviousDaysCount() {
		return previousDaysCount;
	}

	public void setPreviousDaysCount(Integer previousDaysCount) {
		this.previousDaysCount = previousDaysCount;
	}

	public Integer getMinimumRecommendations() {
		return minimumRecommendations;
	}

	public void setMinimumRecommendations(Integer minimumRecommendations) {
		this.minimumRecommendations = minimumRecommendations;
	}
	
	
	
	

}
