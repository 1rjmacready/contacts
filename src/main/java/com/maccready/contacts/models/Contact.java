package com.maccready.contacts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private static final String ERROR_FIRST_NAME = "First name should be between 2 and 50 characters";
    private static final String ERROR_LAST_NAME = "Last name should be between 2 and 50 characters";
    private static final String ERROR_EMAIL = "Email is invalid";
    private static final String ERROR_PHONE = "Phone must be of the format (XXX) XXX-XXXX";
    @Pattern(message = ERROR_FIRST_NAME, regexp = "[a-zA-Z .]{2,50}")
    @NotEmpty(message = ERROR_FIRST_NAME)
    private String firstName;
    @Pattern(message = ERROR_LAST_NAME, regexp = "[a-zA-Z .]{2,50}")
    @NotEmpty(message = ERROR_LAST_NAME)
    private String lastName;
    @Pattern(message = ERROR_EMAIL, regexp = "[a-zA-Z][^@]*[@][a-zA-Z.]{5,30}")
    @NotEmpty(message = ERROR_EMAIL)
    @Size(max = 50, message = "Email max length is 50 characters to match database field")
    private String email;
    @Pattern(message = ERROR_PHONE, regexp = "[(]\\d{3}[)] *\\d{3}[-]\\d{4}")
    @NotEmpty(message = ERROR_PHONE)
    private String phone;
}
