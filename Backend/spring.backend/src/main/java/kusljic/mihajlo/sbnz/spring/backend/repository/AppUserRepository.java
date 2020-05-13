package kusljic.mihajlo.sbnz.spring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kusljic.mihajlo.sbnz.spring.backend.facts.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	
	AppUser findOneByUsername(String username);

}
