package com.maccready.contacts.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactValidationTest {

    private Validator validator;
    private Contact validContact;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        validContact = new Contact("RJ", "MacReady","rjmacready@xyz.com","(123) 123-1234");
    }

    @Test
    void validContact() {
        Set<ConstraintViolation<Contact>> constraints = validator.validate(validContact);
        assertEquals(0, constraints.size());
    }

    @Test
    void invalidValues() {
        validContact.setFirstName("R");
        validContact.setLastName("A few more than 50 characters, that is what I'm talking about");
        validContact.setPhone("123 123-1234");
        validContact.setEmail("rjmacreadyxyz.com");
        assertCommonErrors(validContact);
    }

    @Test
    void invalidEmailLength() {
        validContact.setEmail("rjmacreadyplayedbykurtrussel@abcdefghijklmnopqr.com");
        Set<ConstraintViolation<Contact>> constraints = validator.validate(validContact);
        assertEquals(1, constraints.size());
        assertEquals("Email max length is 50 characters to match database field",constraints.iterator().next().getMessage());
    }

    @Test
    void nullValues() {
        assertCommonErrors(new Contact());
    }

    private void assertCommonErrors(Contact contact) {
        Set<ConstraintViolation<Contact>> constraints = validator.validate(contact);
        assertEquals(4, constraints.size());
        Map<String, String> violations = constraints.stream().collect(Collectors.toMap(f ->
                f.getPropertyPath().toString(), ConstraintViolation::getMessage));
        assertEquals("First name should be between 2 and 50 characters",violations.get("firstName"));
        assertEquals("Last name should be between 2 and 50 characters",violations.get("lastName"));
        assertEquals("Email is invalid",violations.get("email"));
        assertEquals("Phone must be of the format (XXX) XXX-XXXX",violations.get("phone"));
    }
}