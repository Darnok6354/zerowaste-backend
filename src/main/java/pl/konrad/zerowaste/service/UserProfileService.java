package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.konrad.zerowaste.domain.UserProfile;
import pl.konrad.zerowaste.dto.UserSyncRequest;
import pl.konrad.zerowaste.repository.UserProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    // TWOJA OBECNA METODA
    public UserProfile getOrCreateUser(String supabaseUserId, String email) {
        return userProfileRepository.findBySupabaseUserId(supabaseUserId)
                .orElseGet(() -> userProfileRepository.save(
                        UserProfile.builder()
                                .supabaseUserId(supabaseUserId)
                                .email(email)
                                .build()
                ));
    }

    // NOWA METODA DO SYNCHRONIZACJI Z REJESTRACJI
    @Transactional
    public void syncUser(UserSyncRequest request) {
        if (userProfileRepository.existsBySupabaseUserId(request.id())) {
            log.info("Użytkownik o Supabase ID {} już istnieje. Pomijam tworzenie.", request.id());
            return;
        }

        UserProfile newUser = UserProfile.builder()
                .supabaseUserId(request.id())
                .email(request.email())
                .firstName(request.firstName())
                .build();

        userProfileRepository.save(newUser);
        log.info("Utworzono nowego użytkownika: {}", request.email());
    }
}