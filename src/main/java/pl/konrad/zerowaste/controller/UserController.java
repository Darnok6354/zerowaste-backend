package pl.konrad.zerowaste.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pl.konrad.zerowaste.dto.UserSyncRequest;
import pl.konrad.zerowaste.service.UserProfileService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    // DODANE WSTRZYKIWANIE SERWISU
    private final UserProfileService userProfileService;

    // TWÓJ OBECNY ENDPOINT
    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "sub", jwt.getSubject(),
                "email", jwt.getClaimAsString("email"),
                "role", jwt.getClaimAsString("role")
        );
    }

    // NOWY ENDPOINT Z FRONTENDU
    @PostMapping("/sync")
    public ResponseEntity<Void> syncUser(@RequestBody @Valid UserSyncRequest request) {
        userProfileService.syncUser(request);
        return ResponseEntity.ok().build();
    }
}