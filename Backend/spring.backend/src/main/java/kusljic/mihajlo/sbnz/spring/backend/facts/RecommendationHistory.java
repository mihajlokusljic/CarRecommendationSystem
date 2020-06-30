package kusljic.mihajlo.sbnz.spring.backend.facts;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
@Expires("31d")
public class RecommendationHistory {
	
	private CarModel carModel;
	private Date timestamp;
	
	public RecommendationHistory(CarModel carModel, Date timestamp) {
		super();
		this.carModel = carModel;
		this.timestamp = timestamp;
	}

	public CarModel getCarModel() {
		return carModel;
	}

	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
