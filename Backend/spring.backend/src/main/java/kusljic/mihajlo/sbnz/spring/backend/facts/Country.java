package kusljic.mihajlo.sbnz.spring.backend.facts;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "capital_latitude")
	private Double capitalLatitude;
	
	@Column(name = "capital_longitude")
	private double capitalLongitude;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "countryOfOrigin")
	private Set<CarManufacturer> basedManufacturers;
	
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

	public Double getCapitalLatitude() {
		return capitalLatitude;
	}

	public void setCapitalLatitude(Double capitalLatitude) {
		this.capitalLatitude = capitalLatitude;
	}

	public double getCapitalLongitude() {
		return capitalLongitude;
	}

	public void setCapitalLongitude(double capitalLongitude) {
		this.capitalLongitude = capitalLongitude;
	}

	public Set<CarManufacturer> getBasedManufacturers() {
		return basedManufacturers;
	}

	public void setBasedManufacturers(Set<CarManufacturer> basedManufacturers) {
		this.basedManufacturers = basedManufacturers;
	}
	
}
