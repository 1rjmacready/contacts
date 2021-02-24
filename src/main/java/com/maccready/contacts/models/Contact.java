package com.maccready.contacts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
