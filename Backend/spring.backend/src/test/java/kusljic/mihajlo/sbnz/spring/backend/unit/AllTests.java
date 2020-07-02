package kusljic.mihajlo.sbnz.spring.backend.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ObservationRulesTest.class,
	ConformanceRulesTest.class,
	RecommendationRulesTest.class,
	BruteForcePreventionRulesTest.class
})
public class AllTests {

}
