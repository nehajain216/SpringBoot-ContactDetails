package com.nj.springboot.contactdetails.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nj.springboot.contactdetails.entities.Contact;
import com.nj.springboot.contactdetails.services.ContactDetailsServices;
import com.nj.springboot.contactdetails.validation.ContactValidation;

@Controller
public class ContactDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(ContactDetailsController.class);
	private ContactDetailsServices contactDetailsServices;

	@Autowired
	private ContactValidation contactValidator;

	@Autowired
	public ContactDetailsController(ContactDetailsServices contactDetailsServices) {
		this.contactDetailsServices = contactDetailsServices;
	}

	@InitBinder
	private void dateBinder(WebDataBinder binder) {
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}

	@GetMapping("/")
	public String index(Model model) {
		logger.debug("Requesting Home Page");
		return "redirect:/contacts";
	}

	@GetMapping("/contacts")
	public String home(Model model) {
		List<Contact> allContactDetails = contactDetailsServices.getAllContactDetails();
		model.addAttribute("contacts", allContactDetails);
		return "home";
	}

	@GetMapping("contacts/{id}/edit")
	public String editContact(Model model, @PathVariable Integer id) {
		logger.debug("Fetching contact by id: {}", id);
		Contact contact = contactDetailsServices.getContactById(id);
		if (contact == null) {
			logger.debug("Trying to get contact with non existance id: {} ", id);
		} else {
			logger.debug("Found contact with id: {}", id);
			model.addAttribute("contact", contact);
		}

		return "edit";
	}

	@PostMapping("contacts/{id}")
	public String updateContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult) {
		contactValidator.validate(contact, bindingResult);
		if (bindingResult.hasErrors()) {
			return "edit";
		}
		contactDetailsServices.updateContact(contact);
		return "redirect:/contacts";
	}

	@GetMapping("contacts/new")
	public String contactForm(Model model) {
		model.addAttribute("contact", new Contact());
		return "createcontact";
	}

	@PostMapping("/contacts")
	public String saveContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult) {
		contactValidator.validate(contact, bindingResult);
		if (bindingResult.hasErrors()) {
			logger.debug("New contact form has errors");
			return "createcontact";
		}
		contactDetailsServices.createContact(contact);
		logger.debug("created new contact successfully with contact id {}", contact.getId());
		return "redirect:/contacts";
	}

	@GetMapping("contacts/{id}")
	public String viewContact(Model model, @PathVariable int id) {
		Contact contactById = contactDetailsServices.getContactById(id);
		if (contactById != null) {
			model.addAttribute("contact", contactById);
		}
		else
		{
			return "contacts";
		}
		return "deletecontact";
	}

	@PostMapping("contacts/{id}/delete")
	public String deleteContact(@PathVariable int id) {
		contactDetailsServices.deleteContact(id);
		return "redirect:/contacts";
	}
}
