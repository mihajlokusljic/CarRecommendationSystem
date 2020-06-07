package kusljic.mihajlo.sbnz.spring.backend.facts;

public class Observation {
	
	private CarModel carModel;
	private double score;
	private ObservationType type;
	
	public Observation() {
		super();
	}
	
	public Observation(ObservationType type, CarModel carModel) {
		super();
		this.type = type;
		this.carModel = carModel;
	}
	
	public Observation(ObservationType type, CarModel carModel, double score) {
		super();
		this.type = type;
		this.carModel = carModel;
		this.score = score;
	}

	public ObservationType getType() {
		return type;
	}
	public void setType(ObservationType type) {
		this.type = type;
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
