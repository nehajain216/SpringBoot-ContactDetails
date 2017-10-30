package com.nj.springboot.contactdetails.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.nj.springboot.contactdetails.entities.Contact;
import com.nj.springboot.contactdetails.repositories.ContactDetailsRepository;
import com.nj.springboot.contactdetails.services.ContactDetailsServices;


public class ContactDetailsControllerTest {
	
	//ContactDetailsRepository contactDetailsRepository = mock(ContactDetailsRepository.class);
	//ContactDetailsServices contactDetailsServices = new ContactDetailsServices(contactDetailsRepository);
	//ContactDetailsController contactDetailsController = new ContactDetailsController(contactDetailsServices);
	
	Model model = new ExtendedModelMap();
	
	@Test
	public void test_showContact_with_valid_id()
	{
		ContactDetailsRepository contactDetailsRepository = mock(ContactDetailsRepository.class);
		ContactDetailsServices contactDetailsServices = new ContactDetailsServices(contactDetailsRepository);
		ContactDetailsController contactDetailsController = new ContactDetailsController(contactDetailsServices);
		
		int id = 10;
		when(contactDetailsRepository.getOne(id)).thenReturn(new Contact());
		
		String view = contactDetailsController.viewContact(model, id);
		verify(contactDetailsRepository, times(1)).getOne(id);
		assertTrue(model.containsAttribute("contact"));
		assertEquals("deletecontact", view);
	}
	
	@Test
	public void test_showContact_with_invalid_id()
	{
		ContactDetailsRepository contactDetailsRepository = mock(ContactDetailsRepository.class);
		ContactDetailsServices contactDetailsServices = new ContactDetailsServices(contactDetailsRepository);
		ContactDetailsController contactDetailsController = new ContactDetailsController(contactDetailsServices);
		
		int id=10;		
		when(contactDetailsRepository.getOne(id)).thenReturn(null);
		String view = contactDetailsController.viewContact(model, id);
		verify(contactDetailsRepository, times(1)).getOne(id);
		//verify(contactDetailsServices, times(1)).getContactById(id);
		assertFalse(model.containsAttribute("contact"));
		assertEquals("contacts", view);
	}
	
	@Test
	public void test_deleteContact_with_valid_id() throws Exception
	{
		ContactDetailsRepository contactDetailsRepository = mock(ContactDetailsRepository.class);
		ContactDetailsServices contactDetailsServices = new ContactDetailsServices(contactDetailsRepository);
		ContactDetailsController contactDetailsController = new ContactDetailsController(contactDetailsServices);
		int id=86;
		//doReturn(new Contact()).when(contactDetailsRepository).deleteById(id);
		//when(contactDetailsRepository.deleteById(id)).thenReturn(new Contact());
		String view=contactDetailsController.deleteContact(id);
		verify(contactDetailsRepository, times(1)).deleteById(id);
		assertEquals("redirect:/contacts", view);
	}

}
