package kusljic.mihajlo.sbnz.spring.backend.dto;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;

public class CarManufacturerDTO {
	
	private Long id;
	private String name;
	private String logoImageURI;
	private String countryName;
	private Long countryId;
	private boolean canDelete;
	
	public CarManufacturerDTO() {
		super();
	}
	
	public CarManufacturerDTO(String name, String logoImageURI, Country country, boolean canDelete) {
		super();
		this.name = name;
		this.logoImageURI = logoImageURI;
		this.countryId = country.getId();
		this.countryName = country.getName();
		this.canDelete = canDelete;
	}
	
	public CarManufacturerDTO(CarManufacturer manufacturer) {
		this.id = manufacturer.getId();
		this.name = manufacturer.getName();
		this.logoImageURI = manufacturer.getLogoImagePath();
		this.countryId = manufacturer.getCountryOfOrigin().getId();
		this.countryName = manufacturer.getCountryOfOrigin().getName();
		this.canDelete = manufacturer.getCarModels().isEmpty();
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

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

}
