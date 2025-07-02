package auth.papertrail.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auth.papertrail.app.entity.EndUser;

@Repository
public interface UserRepository extends JpaRepository<EndUser, UUID> {
    public EndUser findByEmail(String email);
    
}
