package pl.konrad.zerowaste.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSyncRequest(
        @NotBlank(message = "Id from Supabase is required")
        String id,

        @NotBlank(message = "Email is required")
        @Email(message = "Wrong email format")
        String email,

        @NotBlank(message = "First name is required")
        String firstName
) {}