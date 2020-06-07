package kusljic.mihajlo.sbnz.spring.backend.dto;

import kusljic.mihajlo.sbnz.spring.backend.facts.Country;

public class RecommendationQueryDTO {
	
	private boolean beginner;
	private double budget;
	private boolean forCargoTransport;
	private boolean forCityTraffic;
	private boolean forOffroading;
	private boolean forSport;
	private boolean forTravelling;
	private boolean hasFamily;
	private boolean needsConnectivity;
	private Long userCountryId;
	
	public RecommendationQueryDTO() {
		super();
	}

	public boolean isBeginner() {
		return beginner;
	}

	public void setBeginner(boolean beginner) {
		this.beginner = beginner;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public boolean isForCargoTransport() {
		return forCargoTransport;
	}

	public void setForCargoTransport(boolean forCargoTransport) {
		this.forCargoTransport = forCargoTransport;
	}

	public boolean isForCityTraffic() {
		return forCityTraffic;
	}

	public void setForCityTraffic(boolean forCityTraffic) {
		this.forCityTraffic = forCityTraffic;
	}

	public boolean isForOffroading() {
		return forOffroading;
	}

	public void setForOffroading(boolean forOffroading) {
		this.forOffroading = forOffroading;
	}

	public boolean isForSport() {
		return forSport;
	}

	public void setForSport(boolean forSport) {
		this.forSport = forSport;
	}

	public boolean isForTravelling() {
		return forTravelling;
	}

	public void setForTravelling(boolean forTravelling) {
		this.forTravelling = forTravelling;
	}

	public boolean isHasFamily() {
		return hasFamily;
	}

	public void setHasFamily(boolean hasFamily) {
		this.hasFamily = hasFamily;
	}

	public boolean isNeedsConnectivity() {
		return needsConnectivity;
	}

	public void setNeedsConnectivity(boolean needsConnectivity) {
		this.needsConnectivity = needsConnectivity;
	}

	public Long getUserCountryId() {
		return userCountryId;
	}

	public void setUserCountryId(Long userCountryId) {
		this.userCountryId = userCountryId;
	}

}
