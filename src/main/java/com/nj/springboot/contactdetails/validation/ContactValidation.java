package com.nj.springboot.contactdetails.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nj.springboot.contactdetails.entities.Contact;
import com.nj.springboot.contactdetails.repositories.ContactDetailsRepository;

@Component
public class ContactValidation implements Validator {

	@Autowired
	ContactDetailsRepository contactDetailsRepository;
	
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Contact contact = (Contact) target;
		String email = contact.getEmail();
		Contact contactByEmail = contactDetailsRepository.findByEmail(email);
		if(contactByEmail != null)
		{
			errors.rejectValue("email","duplicate email","Email already in use");
		}
	}

}
