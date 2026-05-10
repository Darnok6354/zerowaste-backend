package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import pl.konrad.zerowaste.domain.UserProfile;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserProfileService userProfileService;

    public UserProfile getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("Authenticated JWT user not found");
        }

        String supabaseUserId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        return userProfileService.getOrCreateUser(supabaseUserId, email);
    }
}