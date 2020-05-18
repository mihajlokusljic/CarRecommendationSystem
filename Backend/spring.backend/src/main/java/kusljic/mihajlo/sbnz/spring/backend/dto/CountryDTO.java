package kusljic.mihajlo.sbnz.spring.backend.dto;

import kusljic.mihajlo.sbnz.spring.backend.facts.Country;

public class CountryDTO {
	
	private Long id;
	private String name;
	private String code;
	
	public CountryDTO() {
		super();
	}
	
	public CountryDTO(Long id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}
	
	public CountryDTO(Country country) {
		this.id = country.getId();
		this.name = country.getName();
		this.code = country.getCode();
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
