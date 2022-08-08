package com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @NotNull
    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    @Size(min = 4, max = 60)
    private String password;

    @NotNull
    @Size(min = 4, max = 60)
    private String passwordConfirmation;

    @NotNull
    private String captchaToken;

}
