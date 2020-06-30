package kusljic.mihajlo.sbnz.spring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kusljic.mihajlo.sbnz.spring.backend.facts.TrendingConstraint;

public interface TrendingConstraintRepository extends JpaRepository<TrendingConstraint, Long> {

}
