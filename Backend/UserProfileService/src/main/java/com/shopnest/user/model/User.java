package com.shopnest.user.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("user_profile")
public class User {

    @Id
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotEmpty(message = "User name cannot be empty")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).*$",
            message = "Password must contain at least one letter and one number"
    )
    private String password;

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotEmpty(message = "Security Question cannot be empty")
    private String securityQuestion;

    @NotEmpty(message = "Security Answer cannot be empty")
    private String securityAnswer;

    @NotNull(message = "Role cannot be null")
    private Role role;

    private boolean isEnabled;
}
