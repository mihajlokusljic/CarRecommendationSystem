package kusljic.mihajlo.sbnz.spring.backend.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import kusljic.mihajlo.sbnz.spring.backend.facts.AccountLock;
import kusljic.mihajlo.sbnz.spring.backend.facts.LoginAttempt;

public class BruteForcePreventionRulesTest {
	
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
	public void whenFiveFailedLoginsThenBlockAccount() {
		String username = "test";
		for (int i = 0; i < 5; i++) {
			this.kieSession.insert(new LoginAttempt(username, new Date(), false));
		}
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("brute force prevention").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that the event for temporarily blocking given account has been created
		QueryResults blocks = this.kieSession.getQueryResults("Fetch locks for account", username);
		assertEquals(1, blocks.size());
		
		for (QueryResultsRow row : blocks) {
			AccountLock lock = (AccountLock) row.get("$al");
			assertEquals(username, lock.getUsername());
		}
	}
	
	@Test
	public void whenEigthFailedLoginsThenBlockAccount() {
		String username = "test";
		for (int i = 0; i < 8; i++) {
			this.kieSession.insert(new LoginAttempt(username, new Date(), false));
		}
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("brute force prevention").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that the event for temporarily blocking given account has been created
		QueryResults blocks = this.kieSession.getQueryResults("Fetch locks for account", username);
		assertEquals(1, blocks.size());
		
		for (QueryResultsRow row : blocks) {
			AccountLock lock = (AccountLock) row.get("$al");
			assertEquals(username, lock.getUsername());
		}
	}
	
	@Test
	public void whenThreeFailedLoginsThenDoNotBlockAccount() {
		String username = "test";
		for (int i = 0; i < 3; i++) {
			this.kieSession.insert(new LoginAttempt(username, new Date(), false));
		}
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("brute force prevention").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that the given account has not been blocked
		QueryResults blocks = this.kieSession.getQueryResults("Fetch locks for account", username);
		assertEquals(0, blocks.size());
	}
	
	@Test
	public void whenFiveFailedLoginsWithOneSuccessInBetweenThenDoNotBlockAccount() {
		String username = "test";
		for (int i = 0; i < 3; i++) {
			this.kieSession.insert(new LoginAttempt(username, new Date(), false));
		}
		
		this.kieSession.insert(new LoginAttempt(username, new Date(), true));
		
		for (int i = 0; i < 2; i++) {
			this.kieSession.insert(new LoginAttempt(username, new Date(), false));
		}
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("brute force prevention").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that the given account has not been blocked
		QueryResults blocks = this.kieSession.getQueryResults("Fetch locks for account", username);
		assertEquals(0, blocks.size());
	}
	
	@Test
	public void whenLessThanFiveFailedLoginsOnDifferentAccountsThenDoNotBlockAccounts() {
		String username1 = "test1";
		String username2 = "test2";
		for (int i = 0; i < 3; i++) {
			this.kieSession.insert(new LoginAttempt(username1, new Date(), false));
		}
		
		for (int i = 0; i < 4; i++) {
			this.kieSession.insert(new LoginAttempt(username2, new Date(), false));
		}
		
		Agenda agenda = this.kieSession.getAgenda();
		agenda.getAgendaGroup("brute force prevention").setFocus();
		this.kieSession.fireAllRules();
		
		// Assert that the given accounts have not been blocked
		QueryResults blocks = this.kieSession.getQueryResults("Fetch locks for account", username1);
		assertEquals(0, blocks.size());
		
		blocks = this.kieSession.getQueryResults("Fetch locks for account", username2);
		assertEquals(0, blocks.size());
	}
	

}
