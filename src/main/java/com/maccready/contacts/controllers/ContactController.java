package com.maccready.contacts.controllers;

import com.maccready.contacts.models.Contact;
import com.maccready.contacts.services.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequestMapping("/contacts")
@RestController
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    List<Contact> getAllContacts(){
        return service.getAll();
    }
    @PostMapping
    public ResponseEntity<Contact> saveContact(@RequestBody Contact contact) throws URISyntaxException {
        Long id = service.save(contact);
        return ResponseEntity.created(new URI("/persons/" + id)).body(contact);
    }
}
