package com.tyson.useless.system.service.impl;

import com.tyson.useless.system.entity.Webhook;
import com.tyson.useless.system.exception.NotFoundException;
import com.tyson.useless.system.repository.WebhookRepository;
import com.tyson.useless.system.service.WebhookService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebhookServiceImpl implements WebhookService {

	final WebhookRepository webhookRepository;

	public WebhookServiceImpl(WebhookRepository webhookRepository) {
		this.webhookRepository = webhookRepository;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Webhook> findAllWebhooks() {
		return webhookRepository.findAll();
	}

	@Override
	public Webhook findWebhookById(Long id) {
		return webhookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Webhook not found  with ID %d", id)));
	}

	@Override
	public void createWebhook(Webhook webhook) {
		webhookRepository.save(webhook);
	}


	@Override
	public void deleteWebhook(Long id) {
		var webhook = webhookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Webhook not found  with ID %d", id)));

		webhookRepository.deleteById(webhook.getId());
	}

}
