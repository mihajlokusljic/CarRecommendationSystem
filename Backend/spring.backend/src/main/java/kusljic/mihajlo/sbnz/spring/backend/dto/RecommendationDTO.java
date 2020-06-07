package kusljic.mihajlo.sbnz.spring.backend.dto;

import kusljic.mihajlo.sbnz.spring.backend.facts.Recommendation;

public class RecommendationDTO {
	
	private Long carModelId;
	private String carModel;
	private String carModelImageUrl;
	private double score;
	
	public RecommendationDTO() {
		super();
	}
	
	public RecommendationDTO(Recommendation rec) {
		this.carModelId = rec.getCarModel().getId();
		this.carModel = rec.getCarModel().toString();
		// this.carModelImageUrl = rec.getCarModel().get
		this.score = rec.getScore();
	}

	public Long getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(Long carModelId) {
		this.carModelId = carModelId;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarModelImageUrl() {
		return carModelImageUrl;
	}

	public void setCarModelImageUrl(String carModelImageUrl) {
		this.carModelImageUrl = carModelImageUrl;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
