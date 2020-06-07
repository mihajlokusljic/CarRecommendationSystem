package kusljic.mihajlo.sbnz.spring.backend.facts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "car_model")
public class CarModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "base_price_euros", nullable = false)
	private int basePriceEuros;
	
	@Column(name = "bluetooth_connective", nullable = false)
	private boolean bluetoothConnective;
	
	@Column(name = "boot_capacity_litres", nullable = false)
	private int bootCapacityLitres;
	
	@Column(name = "doors_number", nullable = false)
	private int doorsNumber;
	
	@Column(name = "engine_displacement_ccm", nullable = false)
	private int engineDisplacementCcm;
	
	@Column(name = "engine_power_bhp", nullable = false)
	private double enginePowerBhp;
	
	@Column(name = "fuel_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private FuelType fuelType;
	
	@Column(name = "navigation_system", nullable = false)
	private boolean havingNavigationSystem;
	
	@Column(name = "parking_sensors", nullable = false)
	private boolean havingParkingSensors;
	
	@Column(name = "passanger_airbags", nullable = false)
	private boolean havingPassangerAirbags;
	
	@Column(name = "rear_camera", nullable = false)
	private boolean havingRearCamera;
	
	@Column(name = "image_path", nullable = true)
	private String imagePath;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "manufacturer_id")
	private CarManufacturer manufacturer;
	
	@Column(name = "mileage", nullable = false)
	private double mileage;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "seats_number", nullable = false)
	private int seatsNumber;
	
	@Column(name = "child_seat_mounts", nullable = false)
	private boolean supportingChildSeatMounts;
	
	@Column(name = "top_speed_kmh", nullable = false)
	private int topSpeedKmh;
	
	@Column(name = "transmission", nullable = false)
	@Enumerated(EnumType.STRING)
	private Transmission transmission;
	
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private CarType type;
	
	public CarModel() {
		super();
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", manufacturer.getName(), name);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBasePriceEuros() {
		return basePriceEuros;
	}

	public void setBasePriceEuros(int basePriceEuros) {
		this.basePriceEuros = basePriceEuros;
	}

	public boolean isBluetoothConnective() {
		return bluetoothConnective;
	}

	public void setBluetoothConnective(boolean bluetoothConnective) {
		this.bluetoothConnective = bluetoothConnective;
	}

	public int getBootCapacityLitres() {
		return bootCapacityLitres;
	}

	public void setBootCapacityLitres(int bootCapacityLitres) {
		this.bootCapacityLitres = bootCapacityLitres;
	}

	public int getDoorsNumber() {
		return doorsNumber;
	}

	public void setDoorsNumber(int doorsNumber) {
		this.doorsNumber = doorsNumber;
	}

	public int getEngineDisplacementCcm() {
		return engineDisplacementCcm;
	}

	public void setEngineDisplacementCcm(int engineDisplacementCcm) {
		this.engineDisplacementCcm = engineDisplacementCcm;
	}

	public double getEnginePowerBhp() {
		return enginePowerBhp;
	}

	public void setEnginePowerBhp(double enginePowerBhp) {
		this.enginePowerBhp = enginePowerBhp;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public boolean isHavingNavigationSystem() {
		return havingNavigationSystem;
	}

	public void setHavingNavigationSystem(boolean havingNavigationSystem) {
		this.havingNavigationSystem = havingNavigationSystem;
	}

	public boolean isHavingParkingSensors() {
		return havingParkingSensors;
	}

	public void setHavingParkingSensors(boolean havingParkingSensors) {
		this.havingParkingSensors = havingParkingSensors;
	}

	public boolean isHavingPassangerAirbags() {
		return havingPassangerAirbags;
	}

	public void setHavingPassangerAirbags(boolean havingPassangerAirbags) {
		this.havingPassangerAirbags = havingPassangerAirbags;
	}

	public boolean isHavingRearCamera() {
		return havingRearCamera;
	}

	public void setHavingRearCamera(boolean havingRearCamera) {
		this.havingRearCamera = havingRearCamera;
	}

	public CarManufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(CarManufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeatsNumber() {
		return seatsNumber;
	}

	public void setSeatsNumber(int seatsNumber) {
		this.seatsNumber = seatsNumber;
	}

	public boolean isSupportingChildSeatMounts() {
		return supportingChildSeatMounts;
	}

	public void setSupportingChildSeatMounts(boolean supportingChildSeatMounts) {
		this.supportingChildSeatMounts = supportingChildSeatMounts;
	}

	public int getTopSpeedKmh() {
		return topSpeedKmh;
	}

	public void setTopSpeedKmh(int topSpeedKmh) {
		this.topSpeedKmh = topSpeedKmh;
	}

	public Transmission getTransmission() {
		return transmission;
	}

	public void setTransmission(Transmission transmission) {
		this.transmission = transmission;
	}

	public CarType getType() {
		return type;
	}

	public void setType(CarType type) {
		this.type = type;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
