package kusljic.mihajlo.sbnz.spring.backend.facts;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "car_manufacturer")
public class CarManufacturer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "logo_image_path")
	private String logoImagePath;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private Country countryOfOrigin;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "manufacturer")	
	private Set<CarModel> carModels;
	
	public CarManufacturer() {
		super();
		this.carModels = new HashSet<CarModel>();
	}
	
	public CarManufacturer(String name, String logoImagePath, Country countryOfOrigin) {
		super();
		this.name = name;
		this.logoImagePath = logoImagePath;
		this.countryOfOrigin = countryOfOrigin;
		this.carModels = new HashSet<CarModel>();
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

	public Set<CarModel> getCarModels() {
		return carModels;
	}

	public void setCarModels(Set<CarModel> carModels) {
		this.carModels = carModels;
	}
	
}
