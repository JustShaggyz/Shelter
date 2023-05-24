package com.shelter.config.auth;

import com.shelter.data.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(max = 30)
    private String firstName;

    @NotBlank
    @Size(max = 30)
    private String lastName;

    @Pattern(regexp = "\"^08\\\\d{8}$\"\n", message = "Phone number must be 10 digits and start with 08")
    private String phoneNumber;

    @Pattern(regexp = "\"^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$\"\n")
    private String email;

    @Pattern(regexp = "\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).{8,}$\"\n")
    private String password;

    private Role role;
}
