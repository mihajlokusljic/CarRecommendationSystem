package kusljic.mihajlo.sbnz.spring.backend.facts;

import java.util.List;

public class RecommendationQuery {
	
	private double budget;
	private Country usersCountry;
	private List<ObservationType> requestedFeatures;
	
	public RecommendationQuery() {
		super();
	}
	
	public RecommendationQuery(double budget, Country usersCountry, List<ObservationType> requestedFeatures) {
		super();
		this.budget = budget;
		this.usersCountry = usersCountry;
		this.requestedFeatures = requestedFeatures;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public Country getUsersCountry() {
		return usersCountry;
	}

	public void setUsersCountry(Country usersCountry) {
		this.usersCountry = usersCountry;
	}

	public List<ObservationType> getRequestedFeatures() {
		return requestedFeatures;
	}

	public void setRequestedFeatures(List<ObservationType> requestedFeatures) {
		this.requestedFeatures = requestedFeatures;
	}

}
