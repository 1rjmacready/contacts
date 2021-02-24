package com.maccready.contacts;

import com.maccready.contacts.models.Contact;
import com.maccready.contacts.services.ContactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}