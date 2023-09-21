package com.tyson.useless.system.controller;

import com.tyson.useless.system.entity.Webhook;
import com.tyson.useless.system.ratelimit.RateLimited;
import com.tyson.useless.system.service.WebhookService;
import com.tyson.useless.system.util.WebhookActionsImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class WebhookController {

	final WebhookService webhookService;

	public WebhookController(WebhookService webhookService) {
		this.webhookService = webhookService;
	}
	public List<Webhook> getListOfConfiguredWebhooks(){
		return webhookService.findAllWebhooks();
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/webhooks")
	public String findAllWebhooks(Model model) {
		model.addAttribute("webhooks", webhookService.findAllWebhooks());
		return "list-webhooks";
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/webhook/{id}")
	public String findWebhookById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("webhook", webhookService.findWebhookById(id));
		return "list-webhook";
	}

	@GetMapping("/addWebhook")
	public String showCreateForm(Webhook webhook) {
		return "add-webhook";
	}

	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/add-webhook")
	public String createWebhook(Webhook webhook, BindingResult result, Model model) throws IOException {
		List<Webhook> listOfWehbooks =  webhookService.findAllWebhooks();
		boolean isValidWebhook = new WebhookActionsImpl().pingWebhook(webhook.getUrl());
		if (result.hasErrors() || listOfWehbooks.size() > 2 || !isValidWebhook) {
			return "bad-webhook";
		}
		webhookService.createWebhook(webhook);
		model.addAttribute("webhook", webhookService.findAllWebhooks());
		return "redirect:/webhooks";
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/remove-webhook/{id}")
	public String deleteWebhook(@PathVariable("id") Long id, Model model) {
		webhookService.deleteWebhook(id);
		model.addAttribute("webhook", webhookService.findAllWebhooks());
		return "redirect:/webhooks";
	}
}
