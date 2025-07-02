package auth.papertrail.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auth.papertrail.app.entity.AuthInfo;
import jakarta.transaction.Transactional;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, UUID> {
    
    @Modifying
    @Transactional
    @Query("update AuthInfo a set a.password = :password WHERE a.user.id = :id")
    public void updatePassword(@Param(value = "id") UUID id, @Param(value = "password") String password);

}
