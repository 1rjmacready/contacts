package com.maccready.contacts.controllers;

import com.maccready.contacts.models.Contact;
import com.maccready.contacts.services.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContactControllerTest {

    private ContactService contactService;
    private ContactController controller;
    private Contact expectedContact;

    @BeforeEach
    void setUp() {
        contactService = mock(ContactService.class);
        controller = new ContactController(contactService);
        expectedContact = new Contact("RJ", "MacReady", "ajolie@xyx.com", "5553216547");
    }

    @Test
    void getAllContacts() {
        when(contactService.getAll()).thenReturn(Collections.singletonList(expectedContact));
        List<Contact> allContacts = controller.getAllContacts();
        assertEquals(1, allContacts.size());
        assertSame(expectedContact, allContacts.get(0));
    }

    @Test
    void getContactSuccess() {
        when(contactService.get(123L)).thenReturn(Optional.of(expectedContact));
        ResponseEntity<Contact> result = controller.getContact(123L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertSame(expectedContact, result.getBody());
    }

    @Test
    void getContactNotFound() {
        when(contactService.get(123L)).thenReturn(Optional.empty());
        ResponseEntity<Contact> result = controller.getContact(123L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteContactSuccess() {
        when(contactService.delete(123L)).thenReturn(true);
        ResponseEntity<Void> result = controller.deleteContact(123L);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
    @Test
    void deleteContactNotFound() {
        when(contactService.delete(123L)).thenReturn(false);
        ResponseEntity<Void> result = controller.deleteContact(123L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void saveContact() throws URISyntaxException {
        when(contactService.save(argThat(contact -> {
            assertEquals("RJ",contact.getFirstName());
            assertEquals("MacReady",contact.getLastName());
            assertEquals("ajolie@xyx.com",contact.getEmail());

            return true;
        }))).thenReturn(324L);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://mydomain.com/persons"));
        ResponseEntity<Contact> result = controller.saveContact(expectedContact, request);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(new URI("http://mydomain.com/persons/324"),result.getHeaders().getLocation());
        assertSame(expectedContact, result.getBody());

    }
}