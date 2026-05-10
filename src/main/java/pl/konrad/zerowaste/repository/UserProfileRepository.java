package pl.konrad.zerowaste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.konrad.zerowaste.domain.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findBySupabaseUserId(String supabaseUserId);
    Optional<UserProfile> findByEmail(String email);
}