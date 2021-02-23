package com.maccready.contacts.repositories;

import com.maccready.contacts.entities.ContactRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactRecord, Long> {
}
