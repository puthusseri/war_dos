package com.tyson.useless.system.controller;

import com.tyson.useless.system.entity.Publisher;
import com.tyson.useless.system.ratelimit.RateLimited;
import com.tyson.useless.system.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublisherController {

	final PublisherService publisherService;
	public PublisherController(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/publishers")
	public String findAllPublishers(Model model) {
		model.addAttribute("publishers", publisherService.findAllPublishers());
		return "list-publishers";
	}

	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/publisher/{id}")
	public String findPublisherById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("publisher", publisherService.findPublisherById(id));
		return "list-publisher";
	}

	@GetMapping("/addPublisher")
	public String showCreateForm(Publisher publisher) {
		return "add-publisher";
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/add-publisher")
	public String createPublisher(Publisher publisher, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-publisher";
		}
		publisherService.createPublisher(publisher);
		model.addAttribute("publisher", publisherService.findAllPublishers());
		return "redirect:/publishers";
	}

	@GetMapping("/updatePublisher/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("publisher", publisherService.findPublisherById(id));
		return "update-publisher";
	}

	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/update-publisher/{id}")
	public String updatePublisher(@PathVariable("id") Long id, Publisher publisher, BindingResult result, Model model) {
		if (result.hasErrors()) {
			publisher.setId(id);
			return "update-publishers";
		}
		publisherService.updatePublisher(publisher);
		model.addAttribute("publisher", publisherService.findAllPublishers());
		return "redirect:/publishers";
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/remove-publisher/{id}")
	public String deletePublisher(@PathVariable("id") Long id, Model model) {
		publisherService.deletePublisher(id);
		model.addAttribute("publisher", publisherService.findAllPublishers());
		return "redirect:/publishers";
	}s
}
