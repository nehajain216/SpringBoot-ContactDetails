package com.nj.springboot.contactdetails.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.nj.springboot.contactdetails.controllers.ContactDetailsController;
import com.nj.springboot.contactdetails.entities.Contact;
import com.nj.springboot.contactdetails.repositories.ContactDetailsRepository;

@Service
@Transactional
public class ContactDetailsServices {
	private static final Logger logger = LoggerFactory.getLogger(ContactDetailsServices.class);

	private ContactDetailsRepository contactDetailsRepository;

	@Autowired
	public ContactDetailsServices(ContactDetailsRepository contactDetailsRepository) {
		this.contactDetailsRepository = contactDetailsRepository;
	}

	public List<Contact> getAllContactDetails() {
		List<Contact> allContacts = new ArrayList<Contact>();
		try {
			allContacts = contactDetailsRepository.findAll();
		} catch (NullPointerException ex) {
			System.out.println("No contact exist :" + ex.getMessage());
		}
		return allContacts;
	}

	public Contact getContactById(int id) {
		Contact contact = null;
		try {
			contact = contactDetailsRepository.getOne(id);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Contact with given id does not exist:" + e.getMessage());
		}
		return contact;
	}

	public void updateContact(Contact contact) {
		Contact oldContact = contactDetailsRepository.getOne(contact.getId());
		oldContact.setName(contact.getName());
		oldContact.setEmail(contact.getEmail());
		oldContact.setPhoneNumber(contact.getPhoneNumber());
		oldContact.setDateOfBirth(contact.getDateOfBirth());
		contactDetailsRepository.save(oldContact);
		logger.debug("Contact updated successfully for contact id:"+contact.getId());
	}

	public void createContact(Contact contact) {
		contactDetailsRepository.save(contact);
		logger.debug("Contact created successfully with id:"+contact.getId());
	}

	public void deleteContact(int id) {
		if(id<1)
		{
			throw new RuntimeException();
		}
		contactDetailsRepository.deleteById(id);
	}

}
