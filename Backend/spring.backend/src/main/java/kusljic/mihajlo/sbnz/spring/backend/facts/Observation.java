package kusljic.mihajlo.sbnz.spring.backend.facts;

public class Observation {
	
	private ObservationType type;
	private CarModel carModel;
	
	public Observation() {
		super();
	}
	
	public Observation(ObservationType type, CarModel carModel) {
		super();
		this.type = type;
		this.carModel = carModel;
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

}
