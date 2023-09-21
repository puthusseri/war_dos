package com.tyson.useless.system.service;

import com.tyson.useless.system.entity.Webhook;

import java.util.List;

public interface WebhookService {

	public List<Webhook> findAllWebhooks();

	public Webhook findWebhookById(Long id);

	public void createWebhook(Webhook webhook);

	public void deleteWebhook(Long id);

}
