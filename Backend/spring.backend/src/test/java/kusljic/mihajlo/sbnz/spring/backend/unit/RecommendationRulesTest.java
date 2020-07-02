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
import kusljic.mihajlo.sbnz.spring.backend.facts.Conformance;
import kusljic.mihajlo.sbnz.spring.backend.facts.ConformanceType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Country;
import kusljic.mihajlo.sbnz.spring.backend.facts.FuelType;
import kusljic.mihajlo.sbnz.spring.backend.facts.Recommendation;
import kusljic.mihajlo.sbnz.spring.backend.facts.Transmission;

public class RecommendationRulesTest {
	
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
	public void highlyRecommend() {
		/*
		 * A car needs to be at least budget friendly and to satisfy users needs to be recommended.
		 * If it is also maintenance friendly then it is highly recommended.
		 * Recommendation score is calculated as: 0.45 * $budgetScore + 0.40 * $needsScore + 0.15 * $maintenanceScore
		 */
		CarModel positive = this.initUnfriendlyCar();
		this.kieSession.insert(positive);
		this.kieSession.insert(new Conformance(ConformanceType.BUDGET_FRIENDLY, positive, 100));
		this.kieSession.insert(new Conformance(ConformanceType.SATISFIES_NEEDS, positive, 80));
		this.kieSession.insert(new Conformance(ConformanceType.MAINTENANCE_FRIENDLY, positive, 75));
		
		CarModel negative = this.initUnfriendlyCar();
		this.kieSession.insert(new Conformance(ConformanceType.BUDGET_FRIENDLY, negative, 95));
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("recommendations").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that recommendation was formed for positive example car
		QueryResults recommendations = this.kieSession.getQueryResults("Fetch recommendations for car model", positive);
		assertEquals(1, recommendations.size());
		
		for (QueryResultsRow row : recommendations) {
			Recommendation rec = (Recommendation) row.get("$r");
			assertEquals(positive, rec.getCarModel());
			assertEquals(88.25, rec.getScore());
		}
		
		// Assert that recommendation was not formed for unfriendly car
		recommendations = this.kieSession.getQueryResults("Fetch recommendations for car model", negative);
		assertEquals(0, recommendations.size());
	}
	
	@Test
	public void recommend() {
		/*
		 * A car needs to be at least budget friendly and to satisfy users needs to be recommended.
		 * Recommendation score is calculated as: 0.45 * $budgetScore + 0.40 * $needsScore
		 */
		CarModel positive = this.initUnfriendlyCar();
		this.kieSession.insert(positive);
		this.kieSession.insert(new Conformance(ConformanceType.BUDGET_FRIENDLY, positive, 92));
		this.kieSession.insert(new Conformance(ConformanceType.SATISFIES_NEEDS, positive, 90));
		
		CarModel negative = this.initUnfriendlyCar();
		this.kieSession.insert(new Conformance(ConformanceType.SATISFIES_NEEDS, negative, 95));
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("recommendations").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that recommendation was formed for positive example car
		QueryResults recommendations = this.kieSession.getQueryResults("Fetch recommendations for car model", positive);
		assertEquals(1, recommendations.size());
		
		for (QueryResultsRow row : recommendations) {
			Recommendation rec = (Recommendation) row.get("$r");
			assertEquals(positive, rec.getCarModel());
			assertEquals(77.4, rec.getScore());
		}
		
		// Assert that recommendation was not formed for unfriendly car
		recommendations = this.kieSession.getQueryResults("Fetch recommendations for car model", negative);
		assertEquals(0, recommendations.size());
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
