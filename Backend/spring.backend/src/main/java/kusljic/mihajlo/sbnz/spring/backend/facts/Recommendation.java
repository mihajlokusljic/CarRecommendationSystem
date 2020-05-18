package kusljic.mihajlo.sbnz.spring.backend.facts;

public class Recommendation {
	
	private CarModel carModel;
	private double score;
	
	public Recommendation() {
		super();
	}
	
	public Recommendation(CarModel carModel, double score) {
		super();
		this.carModel = carModel;
		this.score = score;
	}

	public CarModel getCarModel() {
		return carModel;
	}
	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

}
