package kusljic.mihajlo.sbnz.spring.backend.dto;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;

public class CarManufacturerDTO {
	
	private Long id;
	private String name;
	private String logoImageURI;
	private String countryName;
	private Long countryId;
	
	public CarManufacturerDTO() {
		super();
	}
	
	public CarManufacturerDTO(String name, String logoImageURI, Country country) {
		super();
		this.name = name;
		this.logoImageURI = logoImageURI;
		this.countryId = country.getId();
		this.countryName = country.getName();
	}
	
	public CarManufacturerDTO(CarManufacturer manufacturer) {
		this.id = manufacturer.getId();
		this.name = manufacturer.getName();
		this.logoImageURI = manufacturer.getLogoImagePath();
		this.countryId = manufacturer.getCountryOfOrigin().getId();
		this.countryName = manufacturer.getCountryOfOrigin().getName();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoImageURI() {
		return logoImageURI;
	}

	public void setLogoImageURI(String logoImageURI) {
		this.logoImageURI = logoImageURI;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

}
