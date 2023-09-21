package com.tyson.useless.system.repository;

import com.tyson.useless.system.entity.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookRepository extends JpaRepository<Webhook, Long> {

}
