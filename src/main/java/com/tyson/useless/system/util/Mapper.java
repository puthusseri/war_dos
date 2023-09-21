package com.tyson.useless.system.util;

import java.util.List;
import java.util.stream.Collectors;

import com.tyson.useless.system.entity.*;
import com.tyson.useless.system.vo.*;

public class Mapper {

	public static List<BookRecord> bookModelToVo(List<Book> books) {

		return books.stream().map(vo -> {
			var bookVo = new BookRecord(vo.getId(), vo.getIsbn(), vo.getName(), vo.getSerialName(),
					vo.getDescription());
			return bookVo;
		}).collect(Collectors.toList());
	}

	public static List<AuthorRecord> authorModelToVo(List<Author> authors) {

		return authors.stream().map(vo -> {
			var authorVo = new AuthorRecord(vo.getId(), vo.getName(), vo.getDescription());
			return authorVo;
		}).collect(Collectors.toList());

	}

	public static List<CategoryRecord> categoryModelToVo(List<Category> categories) {

		return categories.stream().map(vo -> {
			var categoryVo = new CategoryRecord(vo.getId(), vo.getName());
			return categoryVo;
		}).collect(Collectors.toList());

	}

	public static List<PublisherRecord> publisherModelToVo(List<Publisher> publishers) {

		return publishers.stream().map(vo -> {
			var publisherVo = new PublisherRecord(vo.getId(), vo.getName());
			return publisherVo;
		}).collect(Collectors.toList());

	}
	public static List<WebhookRecord> webhookModelToVo(List<Webhook> webhooks) {

		return webhooks.stream().map(vo -> {
			var webhookVo = new WebhookRecord(vo.getId(), vo.getUrl());
			return webhookVo;
		}).collect(Collectors.toList());

	}

}
