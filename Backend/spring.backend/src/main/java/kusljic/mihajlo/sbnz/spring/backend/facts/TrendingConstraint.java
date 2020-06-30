package kusljic.mihajlo.sbnz.spring.backend.facts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trending_constraint")
public class TrendingConstraint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "time_window", nullable = false)
	private String timeWindow;
	
	@Column(name = "minimum_recommendations", nullable = false)
	private Integer minimumRecommendations;
	
	public TrendingConstraint() {
		super();
	}
	
	public TrendingConstraint(String timeWindow, Integer minimumRecommendations) {
		super();
		this.timeWindow = timeWindow;
		this.minimumRecommendations = minimumRecommendations;
	}

	public TrendingConstraint(Long id, String timeWindow, Integer minimumRecommendations) {
		super();
		this.id = id;
		this.timeWindow = timeWindow;
		this.minimumRecommendations = minimumRecommendations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimeWindow() {
		return timeWindow;
	}

	public void setTimeWindow(String timeWindow) {
		this.timeWindow = timeWindow;
	}

	public Integer getMinimumRecommendations() {
		return minimumRecommendations;
	}

	public void setMinimumRecommendations(Integer minimumRecommendations) {
		this.minimumRecommendations = minimumRecommendations;
	}
	
}
