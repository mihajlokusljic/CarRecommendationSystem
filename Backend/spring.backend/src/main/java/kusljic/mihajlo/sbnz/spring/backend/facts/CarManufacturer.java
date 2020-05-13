package kusljic.mihajlo.sbnz.spring.backend.facts;

public class CarManufacturer {
	
	private String name;
	private String logoImagePath;
	private Country countryOfOrigin;
	
	public CarManufacturer() {
		super();
	}
	
	public CarManufacturer(String name, String logoImagePath, Country countryOfOrigin) {
		super();
		this.name = name;
		this.logoImagePath = logoImagePath;
		this.countryOfOrigin = countryOfOrigin;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogoImagePath() {
		return logoImagePath;
	}
	public void setLogoImagePath(String logoImagePath) {
		this.logoImagePath = logoImagePath;
	}
	public Country getCountryOfOrigin() {
		return countryOfOrigin;
	}
	public void setCountryOfOrigin(Country countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	
}
