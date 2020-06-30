package kusljic.mihajlo.sbnz.spring.backend.service;

import kusljic.mihajlo.sbnz.spring.backend.facts.TrendingConstraint;

public interface TrendingConstraintService {
	
	TrendingConstraint setTrendingConstraint(TrendingConstraint newConstraint);
	
	TrendingConstraint getTrendingConstraint();

}
