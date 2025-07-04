package auth.papertrail.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auth.papertrail.app.entity.EndUser;

@Repository
public interface UserRepository extends JpaRepository<EndUser, UUID> {
    public Optional<EndUser> findByEmail(String email);
    
}
