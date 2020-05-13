package kusljic.mihajlo.sbnz.spring.backend.facts;

public class Country {
	
	private String name;
	private String code;
	private double capitalLatitude;
	private double capitalLongitude;
	
	public Country() {
		super();
	}
	
	public Country(String name, String code, double capitalLatitude, double capitalLongitude) {
		super();
		this.name = name;
		this.code = code;
		this.capitalLatitude = capitalLatitude;
		this.capitalLongitude = capitalLongitude;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getCapitalLatitude() {
		return capitalLatitude;
	}
	public void setCapitalLatitude(double capitalLatitude) {
		this.capitalLatitude = capitalLatitude;
	}
	public double getCapitalLongitude() {
		return capitalLongitude;
	}
	public void setCapitalLongitude(double capitalLongitude) {
		this.capitalLongitude = capitalLongitude;
	}
	
}
