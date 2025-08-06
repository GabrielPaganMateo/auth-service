package auth.papertrail.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auth.papertrail.app.entity.OneTimePasscode;

public interface OTPRepository extends JpaRepository<OneTimePasscode, Long> {

    public Optional<OneTimePasscode> findByEndUserId(UUID id);
}
