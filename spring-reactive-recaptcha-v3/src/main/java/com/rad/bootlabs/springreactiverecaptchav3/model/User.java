package com.rad.bootlabs.springreactiverecaptchav3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A user.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "app_user")
public class User {

    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @Getter(onMethod = @__(@JsonIgnore))
    @NotNull
    @Size(min = 4, max = 60)
    private String password;

    @Size(max = 100)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;
}
