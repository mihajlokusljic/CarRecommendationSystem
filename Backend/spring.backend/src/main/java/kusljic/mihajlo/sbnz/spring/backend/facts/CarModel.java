package kusljic.mihajlo.sbnz.spring.backend.facts;

public class CarModel {
	
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
	private CarManufacturer manufacturer;
	private double mileage;
	private String name;
	private int seatsNumber;
	private boolean supportingChildSeatMounts;
	private int topSpeedKmh;
	private Transmission transmission;
	private CarType type;
	
	public CarModel() {
		super();
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", manufacturer.getName(), name);
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
	
}
