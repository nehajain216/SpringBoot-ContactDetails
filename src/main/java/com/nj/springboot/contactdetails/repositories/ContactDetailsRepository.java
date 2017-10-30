package com.nj.springboot.contactdetails.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nj.springboot.contactdetails.entities.Contact;

@Repository
public interface ContactDetailsRepository extends JpaRepository<Contact, Integer> {
	
	Contact findByEmail(String email);

}
