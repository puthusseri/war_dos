package com.tyson.useless.system.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.tyson.useless.system.entity.Author;
import com.tyson.useless.system.entity.Category;
import com.tyson.useless.system.entity.Webhook;
import com.tyson.useless.system.ratelimit.RateLimited;
import com.tyson.useless.system.service.*;
import com.tyson.useless.system.util.WebhookActionsImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tyson.useless.system.entity.Book;

@Controller
public class BookController {
    final BookService bookService;
    final AuthorService authorService;
    final CategoryService categoryService;
    final PublisherService publisherService;
    final WebhookService webhookService;

    public BookController(PublisherService publisherService, CategoryService categoryService, BookService bookService,
                          AuthorService authorService, WebhookService webhookService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
        this.webhookService = webhookService;
    }

    @RateLimited(permits = 40, period = 60)
    @RequestMapping({"/books", "/"})
    public String findAllBooks(Model model, @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {
        var currentPage = page.orElse(1);
        var pageSize = size.orElse(5);
        var bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("books", bookPage);
        var totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "list-books";
    }

    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/searchBook")
    public String searchBook(@Param("keyword") String keyword, Model model) {
        model.addAttribute("books", bookService.searchBooks(keyword));
        model.addAttribute("keyword", keyword);
        return "list-books";
    }

    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/book/{id}")
    public String findBookById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "list-book";
    }

    @GetMapping("/add")
    public String showCreateForm(Book book, Model model) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("authors", authorService.findAllAuthors());
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "add-book";
    }

    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/add-book")
    public String createBook(Book book, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            return "add-book";
        }
        processWebhook(book);
        bookService.createBook(book);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) throws InterruptedException {
        model.addAttribute("book", bookService.findBookById(id));
        return "update-book";
    }

    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/update-book/{id}")
    public String updateBook(@PathVariable("id") Long id, Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }
        bookService.updateBook(book);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/remove-book/{id}")
    public String deleteBook(@PathVariable("id") Long id, Model model) throws InterruptedException {
        bookService.deleteBook(id);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }
    private void processWebhook(Book book) throws Exception {
        List<Webhook> listOfConfiguredWebhooks = webhookService.findAllWebhooks();
        for (Webhook webhook : listOfConfiguredWebhooks) {
            JSONObject webhookObject = createWebhookObject(book);
            new WebhookActionsImpl().pushWebhookEvent(webhook.getUrl(), webhookObject);
        }
    }
    private JSONObject createWebhookObject(Book book) throws JSONException {
        JSONObject webhookObject = new JSONObject();
        webhookObject.put("name", book.getName());
        webhookObject.put("description", book.getDescription());
        Set<Category> categories = book.getCategories();
        if (!categories.isEmpty()) {
            Category category = categories.iterator().next(); // Get the first category
            webhookObject.put("categoryName", category.getName());
        }
        Set<Author> authors = book.getAuthors();
        for (Author author : authors) {
            webhookObject.put("authorName", author.getName());
        }
        return webhookObject;
    }
}
