package kusljic.mihajlo.sbnz.spring.backend.dto;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarType;
import kusljic.mihajlo.sbnz.spring.backend.facts.FuelType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Transmission;

public class CarModelDTO {
	
	private Long id;
	private int basePriceEuros;
	private boolean bluetoothConnective;
	private int bootCapacityLitres;
	private int doorsNumber;
	private int engineDisplacementCcm;
	private double enginePowerBhp;
	private FuelType fuelType;
	private boolean havingNavigationSystem;
	private boolean havingParkingSensors;
	private boolean havingPassangerAirbags;
	private boolean havingRearCamera;
	private Long manufacturerId;
	private String manufacturerName;
	private double mileage;
	private String name;
	private int seatsNumber;
	private boolean supportingChildSeatMounts;
	private int topSpeedKmh;
	private Transmission transmission;
	private CarType type;
	
	public CarModelDTO() {
		super();
	}
	
	public CarModelDTO(CarModel carModel) {
		this.id = carModel.getId();
		this.basePriceEuros = carModel.getBasePriceEuros();
		this.bluetoothConnective = carModel.isBluetoothConnective();
		this.bootCapacityLitres = carModel.getBootCapacityLitres();
		this.doorsNumber = carModel.getDoorsNumber();
		this.engineDisplacementCcm = carModel.getEngineDisplacementCcm();
		this.enginePowerBhp = carModel.getEnginePowerBhp();
		this.fuelType = carModel.getFuelType();
		this.havingNavigationSystem = carModel.isHavingNavigationSystem();
		this.havingParkingSensors = carModel.isHavingParkingSensors();
		this.havingPassangerAirbags = carModel.isHavingPassangerAirbags();
		this.havingRearCamera = carModel.isHavingRearCamera();
		this.manufacturerId = carModel.getManufacturer().getId();
		this.manufacturerName = carModel.getManufacturer().getName();
		this.mileage = carModel.getMileage();
		this.name = carModel.getName();
		this.seatsNumber = carModel.getSeatsNumber();
		this.supportingChildSeatMounts = carModel.isSupportingChildSeatMounts();
		this.topSpeedKmh = carModel.getTopSpeedKmh();
		this.transmission = carModel.getTransmission();
		this.type = carModel.getType();
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

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
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

}
