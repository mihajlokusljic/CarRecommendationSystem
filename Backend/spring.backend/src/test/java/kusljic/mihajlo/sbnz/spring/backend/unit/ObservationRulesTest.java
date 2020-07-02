package kusljic.mihajlo.sbnz.spring.backend.unit;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import kusljic.mihajlo.sbnz.spring.backend.facts.CarManufacturer;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarModel;
import kusljic.mihajlo.sbnz.spring.backend.facts.CarType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.facts.FuelType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Observation;
import kusljic.mihajlo.sbnz.spring.backend.facts.ObservationType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Transmission;

public class ObservationRulesTest {
	
	private KieSession kieSession;
	
	@Before
	public void initSession() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		this.kieSession = kContainer.newKieSession("rules-session");
	}
	
	@After
	public void disposeSession() {
		this.kieSession.dispose();
	}
	
	@Test
	public void begginerFriendlyScore100() {
		// type == CarType.MICRO || type == CarType.HATCHBACK, havingParkingSensors, havingRearCamera, 
		// havingNavigationSystem, transmission == Transmission.AUTOMATIC
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.HATCHBACK);
		positive.setHavingParkingSensors(true);
		positive.setHavingRearCamera(true);
		positive.setHavingNavigationSystem(true);
		positive.setTransmission(Transmission.AUTOMATIC);
		this.executeTest(positive, ObservationType.BEGINNER_FRIENDLY, 100);
	}
	
	
	@Test
	public void begginerFriendlyScore70() {
		// type == CarType.MICRO || type == CarType.HATCHBACK || type == CarType.SEDAN, havingParkingSensors, 
		// havingRearCamera
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.SEDAN);
		positive.setHavingParkingSensors(true);
		positive.setHavingRearCamera(true);
		this.executeTest(positive, ObservationType.BEGINNER_FRIENDLY, 70);
	}
	
	@Test
	public void begginerFriendlyScore40() {
		// type == CarType.MICRO || type == CarType.HATCHBACK  || type == CarType.SEDAN
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.MICRO);
		this.executeTest(positive, ObservationType.BEGINNER_FRIENDLY, 40);
	}
	
	@Test
	public void cargoFriendlyScore100() {
		// type == CarType.PICKUP  || type == CarType.WAGON, bootCapacityLitres >= 900, enginePowerBhp >= 100
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.PICKUP);
		positive.setBootCapacityLitres(1200);
		positive.setEnginePowerBhp(150);
		this.executeTest(positive, ObservationType.CARGO_FRIENDLY, 100);
	}
	
	@Test
	public void cargoFriendlyScore80() {
		// type == CarType.PICKUP || type == CarType.WAGON, bootCapacityLitres >= 900
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.WAGON);
		positive.setBootCapacityLitres(1000);
		this.executeTest(positive, ObservationType.CARGO_FRIENDLY, 80);
	}
	
	@Test
	public void cityFriendlyScore100() {
		// type == CarType.MICRO || type == CarType.HATCHBACK, havingParkingSensors, havingRearCamera, mileage >= 15
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.HATCHBACK);
		positive.setHavingParkingSensors(true);
		positive.setHavingRearCamera(true);
		positive.setMileage(23);
		this.executeTest(positive, ObservationType.CITY_FRIENDLY, 100);
	}
	
	@Test
	public void cityFriendlyScore80() {
		// type == CarType.MICRO || type == CarType.HATCHBACK || type == CarType.SEDAN, havingParkingSensors, havingRearCamera
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.MICRO);
		positive.setHavingParkingSensors(true);
		positive.setHavingRearCamera(true);
		this.executeTest(positive, ObservationType.CITY_FRIENDLY, 80);
	}
	
	@Test
	public void cityFriendlyScore40() {
		// type == CarType.MICRO || type == CarType.HATCHBACK || type == CarType.SEDAN
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.SEDAN);
		this.executeTest(positive, ObservationType.CITY_FRIENDLY, 40);
	}
	
	@Test
	public void connectivitySupported() {
		// bluetoothConnective
		CarModel positive = this.initUnfriendlyCar();
		positive.setBluetoothConnective(true);
		this.executeTest(positive, ObservationType.CONNECTIVITY_SUPPORTED, 100);
	}
	
	@Test
	public void familyFriendlyScore100() {
		// type == CarType.MINIVAN || type == CarType.HATCHBACK || type == CarType.SEDAN, 
		// bootCapacityLitres >= 400, seatsNumber >= 5, supportingChildSeatMounts, havingPassangerAirbags
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.MINIVAN);
		positive.setBootCapacityLitres(510);
		positive.setSeatsNumber(7);
		positive.setSupportingChildSeatMounts(true);
		positive.setHavingPassangerAirbags(true);
		this.executeTest(positive, ObservationType.FAMILY_FRIENDLY, 100);
	}
	
	@Test
	public void familyFriendlyScore80() {
		// bootCapacityLitres >= 400, seatsNumber >= 5, havingPassangerAirbags
		CarModel positive = this.initUnfriendlyCar();
		positive.setBootCapacityLitres(400);
		positive.setSeatsNumber(5);
		positive.setHavingPassangerAirbags(true);
		this.executeTest(positive, ObservationType.FAMILY_FRIENDLY, 80);
	}
	
	@Test
	public void familyFriendlyScore50() {
		// seatsNumber >= 5, havingPassangerAirbags
		CarModel positive = this.initUnfriendlyCar();
		positive.setSeatsNumber(7);
		positive.setHavingPassangerAirbags(true);
		this.executeTest(positive, ObservationType.FAMILY_FRIENDLY, 50);
	}
	
	@Test
	public void offroadFriendlyScore100() {
		// type == CarType.PICKUP || type == CarType.SUV, 
		// enginePowerBhp > 150, fuelType == FuelType.DIESEL || fuelType == FuelType.PETROL
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.SUV);
		positive.setEnginePowerBhp(200);
		positive.setFuelType(FuelType.DIESEL);
		this.executeTest(positive, ObservationType.OFFROAD_FRIENDLY, 100);
	}
	
	@Test
	public void offroadFriendlyScore40() {
		// type == CarType.PICKUP || type == CarType.SUV
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.PICKUP);
		this.executeTest(positive, ObservationType.OFFROAD_FRIENDLY, 40);
	}
	
	@Test
	public void sportFriendly() {
		// type == CarType.COUPE, topSpeedKmh > 250, enginePowerBhp >= 400
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.COUPE);
		positive.setTopSpeedKmh(280);
		positive.setEnginePowerBhp(450);
		this.executeTest(positive, ObservationType.SPORT_FRIENDLY, 100);
	}
	
	@Test
	public void travelFriendlyScore100() {
		// type == CarType.MINIVAN || type == CarType.WAGON,  bootCapacityLitres > 400, mileage > 20, 
		// havingNavigationSystem
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.MINIVAN);
		positive.setBootCapacityLitres(420);
		positive.setMileage(29);
		positive.setHavingNavigationSystem(true);
		this.executeTest(positive, ObservationType.TRAVEL_FRIENDLY, 100);
	}
	
	@Test
	public void travelFriendlyScore60() {
		// bootCapacityLitres > 400, mileage > 20
		CarModel positive = this.initUnfriendlyCar();
		positive.setBootCapacityLitres(401);
		positive.setMileage(21);
		this.executeTest(positive, ObservationType.TRAVEL_FRIENDLY, 60);
	}
	
	@Test
	public void fuelEfficientScore100() {
		// fuelType == FuelType.ELECTRIC || fuelType == FuelType.HIBRID, 
		// mileage >= 20, engineDisplacementCcm <= 1500
		CarModel positive = this.initUnfriendlyCar();
		positive.setFuelType(FuelType.HIBRID);
		positive.setMileage(23);
		positive.setEngineDisplacementCcm(980);
		this.executeTest(positive, ObservationType.FUEL_EFFICIENT, 100);
	}
	
	@Test
	public void fuelEfficientScore70() {
		// mileage >= 20, engineDisplacementCcm <= 1500
		CarModel positive = this.initUnfriendlyCar();
		positive.setMileage(20);
		positive.setEngineDisplacementCcm(1500);
		this.executeTest(positive, ObservationType.FUEL_EFFICIENT, 70);
	}
	
	@Test
	public void fuelEfficientScore40() {
		// engineDisplacementCcm <= 1500
		CarModel positive = this.initUnfriendlyCar();
		positive.setFuelType(FuelType.ELECTRIC);
		positive.setEngineDisplacementCcm(0);
		this.executeTest(positive, ObservationType.FUEL_EFFICIENT, 40);
	}
	
	@Test
	public void lowRegistrationCosts() {
		// type != CarType.COUPE, engineDisplacementCcm <= 1500
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.MICRO);
		positive.setEngineDisplacementCcm(680);
		this.executeTest(positive, ObservationType.LOW_REGISTRATION_COSTS, 100);
	}
	
	
	private void executeTest(CarModel positive, ObservationType testedObservationType, double expectedScore) {
		this.kieSession.insert(positive);
		CarModel negative = this.initUnfriendlyCar();
		this.kieSession.insert(negative);
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("global observations").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that observation was formed for positive example car
		QueryResults observations = this.kieSession.getQueryResults("Fetch observations for car model and observation type", positive, testedObservationType);
		assertEquals(1, observations.size());
		
		for (QueryResultsRow row : observations) {
			Observation obs = (Observation) row.get("$o");
			assertEquals(testedObservationType, obs.getType());
			assertEquals(positive, obs.getCarModel());
			assertEquals(expectedScore, obs.getScore());
		}
		
		// Assert that observation was not formed for unfriendly car
		observations = this.kieSession.getQueryResults("Fetch observations for car model and observation type", negative, testedObservationType);
		assertEquals(0, observations.size());
	}
	
	private CarModel initUnfriendlyCar() {
		CarModel result = new CarModel();
		CarManufacturer manufacturer = new CarManufacturer("Test manufacturer", null, new Country());
		result.setManufacturer(manufacturer);
		result.setBasePriceEuros(800);
		result.setBluetoothConnective(false);
		result.setBootCapacityLitres(50);
		result.setDoorsNumber(2);
		result.setEngineDisplacementCcm(1600);
		result.setEnginePowerBhp(70);
		result.setFuelType(FuelType.PETROL);
		result.setHavingNavigationSystem(false);
		result.setHavingParkingSensors(false);
		result.setHavingPassangerAirbags(false);
		result.setHavingRearCamera(false);
		result.setMileage(5);
		result.setName("Test car");
		result.setSeatsNumber(2);
		result.setSupportingChildSeatMounts(false);
		result.setTopSpeedKmh(90);
		result.setTransmission(Transmission.MANUAL);
		result.setType(CarType.COUPE);
		return result;
	}
	
}
