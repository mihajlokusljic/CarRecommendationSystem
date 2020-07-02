package kusljic.mihajlo.sbnz.spring.backend.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import kusljic.mihajlo.sbnz.spring.backend.facts.Conformance;
import kusljic.mihajlo.sbnz.spring.backend.facts.ConformanceType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.facts.FuelType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Observation;
import kusljic.mihajlo.sbnz.spring.backend.facts.ObservationType;
import kusljic.mihajlo.sbnz.spring.backend.facts.RecommendationQuery;
import kusljic.mihajlo.sbnz.spring.backend.facts.Transmission;

public class ConformanceRulesTest {
	
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
	public void budgetFriendlyFittingBudget() {
		RecommendationQuery query = new RecommendationQuery();
		query.setBudget(30000);
		query.setRequestedFeatures(new ArrayList<ObservationType>());
		query.setUsersCountry(this.getGermany());
		CarModel positive = this.initUnfriendlyCar();
		positive.setBasePriceEuros(19000);
		
		this.executeTest(query, positive, ConformanceType.BUDGET_FRIENDLY, 100);
	}
	
	@Test
	public void budgetFriendlyNotExceedingBudgetByMoreTnatTenPercent() {
		RecommendationQuery query = new RecommendationQuery();
		query.setBudget(10000);
		query.setRequestedFeatures(new ArrayList<ObservationType>());
		query.setUsersCountry(this.getGermany());
		
		CarModel positive = this.initUnfriendlyCar();
		positive.setBasePriceEuros(10800); // exceeds budget by 8%
		
		this.executeTest(query, positive, ConformanceType.BUDGET_FRIENDLY, 92);
	}
	
	@Test
	public void servicingFriendlyScore100() {
		RecommendationQuery query = new RecommendationQuery();
		query.setBudget(10000);
		query.setRequestedFeatures(new ArrayList<ObservationType>());
		query.setUsersCountry(this.getGermany());
		
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.SEDAN);
		positive.setManufacturer(new CarManufacturer("German test manufacturer", null, this.getGermany()));
		
		this.executeTest(query, positive, ConformanceType.SERVICING_FRIENLDY, 100);
	}
	
	@Test
	public void servicingFriendlyScore70() {
		RecommendationQuery query = new RecommendationQuery();
		query.setBudget(20000);
		query.setRequestedFeatures(new ArrayList<ObservationType>());
		query.setUsersCountry(this.getSerbia());
		
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.HATCHBACK);
		positive.setManufacturer(new CarManufacturer("Italian test manufacturer", null, this.getItaly()));
		
		this.executeTest(query, positive, ConformanceType.SERVICING_FRIENLDY, 70);
	}
	
	@Test
	public void servicingFriendlyScore40() {
		RecommendationQuery query = new RecommendationQuery();
		query.setBudget(40000);
		query.setRequestedFeatures(new ArrayList<ObservationType>());
		query.setUsersCountry(this.getSerbia());
		
		CarModel positive = this.initUnfriendlyCar();
		positive.setType(CarType.HATCHBACK);
		positive.setManufacturer(new CarManufacturer("British test manufacturer", null, this.getUK()));
		
		this.executeTest(query, positive, ConformanceType.SERVICING_FRIENLDY, 40);
	}
	
	@Test
	public void maintenanceFriendly() {
		/*
		 * To be considered maintenance friendly a car model must be fuel efficient, servicing friendly and with
		 * low registration costs. Maintenance friendly score is calculated as average of scores of corresponding observations.
		 */
		CarModel positive = this.initUnfriendlyCar();
		this.kieSession.insert(positive);
		this.kieSession.insert(new Observation(ObservationType.FUEL_EFFICIENT, positive, 70));
		this.kieSession.insert(new Observation(ObservationType.LOW_REGISTRATION_COSTS, positive, 100));
		this.kieSession.insert(new Conformance(ConformanceType.SERVICING_FRIENLDY, positive, 40));
		
		CarModel negative1 = this.initUnfriendlyCar();
		this.kieSession.insert(negative1);
		
		CarModel negative2 = this.initUnfriendlyCar();
		this.kieSession.insert(negative2);
		this.kieSession.insert(new Observation(ObservationType.FUEL_EFFICIENT, negative2, 100));
		
		CarModel negative3 = this.initUnfriendlyCar();
		this.kieSession.insert(negative3);
		this.kieSession.insert(new Observation(ObservationType.FUEL_EFFICIENT, negative3, 100));
		this.kieSession.insert(new Observation(ObservationType.LOW_REGISTRATION_COSTS, negative3, 100));
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("conformances to user").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that conformance was formed for positive example car
		QueryResults conformances = this.kieSession.getQueryResults("Fetch conformances for car model and conformance type", positive, ConformanceType.MAINTENANCE_FRIENDLY);
		assertEquals(1, conformances.size());
		
		for (QueryResultsRow row : conformances) {
			Conformance conf = (Conformance) row.get("$c");
			assertEquals(ConformanceType.MAINTENANCE_FRIENDLY, conf.getType());
			assertEquals(positive, conf.getCarModel());
			assertEquals(70, conf.getScore());
		}
		
		// Assert that conformance was not formed for negative example car
		List<CarModel> negativeExamples = Arrays.asList(negative1, negative2, negative3);
		for (CarModel carModel : negativeExamples) {
			conformances = this.kieSession.getQueryResults("Fetch conformances for car model and conformance type", carModel, ConformanceType.MAINTENANCE_FRIENDLY);
			assertEquals(0, conformances.size());
		}
		
	}
	
	@Test
	public void satisfiesNeeds() {
		/*
		 * A car model must have corresponding observations generated for every observation type
		 * important to the user in order to satisfy users needs. The score for satisfying needs
		 * is calculated as the average of scores of all generated observations for the car model
		 * which are important to the user.
		 */
		RecommendationQuery query = new RecommendationQuery();
		query.setBudget(40000);
		query.setUsersCountry(this.getSerbia());
		List<ObservationType> relevantobservationTypes = new ArrayList<ObservationType>();
		relevantobservationTypes.add(ObservationType.TRAVEL_FRIENDLY);
		relevantobservationTypes.add(ObservationType.FAMILY_FRIENDLY);
		relevantobservationTypes.add(ObservationType.CONNECTIVITY_SUPPORTED);
		relevantobservationTypes.add(ObservationType.CITY_FRIENDLY);
		query.setRequestedFeatures(relevantobservationTypes);
		this.kieSession.insert(query);
		
		CarModel positive = this.initUnfriendlyCar();
		this.kieSession.insert(positive);
		this.kieSession.insert(new Observation(ObservationType.TRAVEL_FRIENDLY, positive, 60));
		this.kieSession.insert(new Observation(ObservationType.FAMILY_FRIENDLY, positive, 80));
		this.kieSession.insert(new Observation(ObservationType.CONNECTIVITY_SUPPORTED, positive, 100));
		this.kieSession.insert(new Observation(ObservationType.CITY_FRIENDLY, positive, 80));
		this.kieSession.insert(new Observation(ObservationType.BEGINNER_FRIENDLY, positive, 70)); // Not important to the user
		
		CarModel negative1 = this.initUnfriendlyCar(); // does not have any of the required observations
		this.kieSession.insert(negative1);
		
		CarModel negative2 = this.initUnfriendlyCar(); // not travel friendly and not city friendly
		this.kieSession.insert(negative2);
		this.kieSession.insert(new Observation(ObservationType.FAMILY_FRIENDLY, negative2, 80));
		this.kieSession.insert(new Observation(ObservationType.CONNECTIVITY_SUPPORTED, negative2, 100));
		this.kieSession.insert(new Observation(ObservationType.BEGINNER_FRIENDLY, negative2, 70));
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("conformances to user").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that conformance was formed for positive example car
		QueryResults conformances = this.kieSession.getQueryResults("Fetch conformances for car model and conformance type", positive, ConformanceType.SATISFIES_NEEDS);
		assertEquals(1, conformances.size());
		
		for (QueryResultsRow row : conformances) {
			Conformance conf = (Conformance) row.get("$c");
			assertEquals(ConformanceType.SATISFIES_NEEDS, conf.getType());
			assertEquals(positive, conf.getCarModel());
			assertEquals(80, conf.getScore());
		}
		
		// Assert that conformance was not formed for negative example car
		List<CarModel> negativeExamples = Arrays.asList(negative1, negative2);
		for (CarModel carModel : negativeExamples) {
			conformances = this.kieSession.getQueryResults("Fetch conformances for car model and conformance type", carModel, ConformanceType.SATISFIES_NEEDS);
			assertEquals(0, conformances.size());
		}
	}
	
	private void executeTest(RecommendationQuery query, CarModel positive, ConformanceType testedConformanceType, double expectedScore) {
		this.kieSession.insert(query);
		this.kieSession.insert(positive);
		CarModel negative = this.initUnfriendlyCar();
		this.kieSession.insert(negative);
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("conformances to user").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that conformance was formed for positive example car
		QueryResults conformances = this.kieSession.getQueryResults("Fetch conformances for car model and conformance type", positive, testedConformanceType);
		assertEquals(1, conformances.size());
		
		for (QueryResultsRow row : conformances) {
			Conformance conf = (Conformance) row.get("$c");
			assertEquals(testedConformanceType, conf.getType());
			assertEquals(positive, conf.getCarModel());
			assertEquals(expectedScore, conf.getScore());
		}
		
		// Assert that conformance was not formed for unfriendly car
		conformances = this.kieSession.getQueryResults("Fetch conformances for car model and conformance type", negative, testedConformanceType);
		assertEquals(0, conformances.size());
	}
	
	private CarModel initUnfriendlyCar() {
		CarModel result = new CarModel();
		CarManufacturer manufacturer = new CarManufacturer("Test manufacturer", null, this.getJapan());
		result.setManufacturer(manufacturer);
		result.setBasePriceEuros(100000000);
		result.setBluetoothConnective(false);
		result.setBootCapacityLitres(50);
		result.setDoorsNumber(2);
		result.setEngineDisplacementCcm(4000);
		result.setEnginePowerBhp(180);
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
	
	private Country getSerbia() {
		return new Country("Serbia", "RS", 44.83333333333334, 20.5);
	}
	
	private Country getItaly() {
		return new Country("Italy", "IT", 41.9, 12.483333);
	}
	
	private Country getGermany() {
		return new Country("Germany", "DE", 52.51666666666666, 13.4);
	}
	
	private Country getUK() {
		return new Country("United Kingdom", "GB", 51.5, -0.083333);
	}
	
	private Country getJapan() {
		return new Country("Japan", "JP", 35.68333333333333, 139.75);
	}

}
