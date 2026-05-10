package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.konrad.zerowaste.domain.UserProfile;
import pl.konrad.zerowaste.repository.UserProfileRepository;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile getOrCreateUser(String supabaseUserId, String email) {
        return userProfileRepository.findBySupabaseUserId(supabaseUserId)
                .orElseGet(() -> userProfileRepository.save(
                        UserProfile.builder()
                                .supabaseUserId(supabaseUserId)
                                .email(email)
                                .build()
                ));
    }
}