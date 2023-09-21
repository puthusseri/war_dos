package com.tyson.useless.system.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.tyson.useless.system.entity.Webhook;
import com.tyson.useless.system.ratelimit.RateLimited;
import com.tyson.useless.system.entity.Author;
import com.tyson.useless.system.service.WebhookService;
import com.tyson.useless.system.util.WebhookActionsImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tyson.useless.system.service.AuthorService;

@Controller
public class AuthorController {

    final AuthorService authorService;
    final WebhookService webhookService;

    public AuthorController(AuthorService authorService, WebhookService webhookService) {
        this.authorService = authorService;
        this.webhookService = webhookService;
    }
    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/authors")
    public String findAllAuthors(Model model, @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size) {

        var currentPage = page.orElse(1);
        var pageSize = size.orElse(5);
        var bookPage = authorService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("authors", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "list-authors";
    }
    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/author/{id}")
    public String findAuthorById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", authorService.findAuthorById(id));
        return "list-author";
    }

    @GetMapping("/addAuthor")
    public String showCreateForm(Author author) {
        return "add-author";
    }
    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/add-author")
    public String createAuthor(Author author, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            return "add-author";
        }
        processWebhook(author);
        authorService.createAuthor(author);
        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }

    @GetMapping("/updateAuthor/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", authorService.findAuthorById(id));
        return "update-author";
    }

    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/update-author/{id}")
    public String updateAuthor(@PathVariable("id") Long id, Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            author.setId(id);
            return "update-author";
        }
        authorService.updateAuthor(author);
        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }
    @RateLimited(permits = 40, period = 60)
    @RequestMapping("/remove-author/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, Model model) {
        authorService.deleteAuthor(id);
        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }
    private void processWebhook(Author author) throws Exception {
        List<Webhook> listOfConfiguredWebhooks = webhookService.findAllWebhooks();
        for (Webhook webhook : listOfConfiguredWebhooks) {
            JSONObject webhookObject = createWebhookObject(author);
            new WebhookActionsImpl().pushWebhookEvent(webhook.getUrl(), webhookObject);
        }
    }
    private JSONObject createWebhookObject(Author author) throws JSONException {
        JSONObject webhookObject = new JSONObject();
        webhookObject.put("authorName", author.getName());
        webhookObject.put("authorDescription", author.getDescription());
        return webhookObject;
    }
}
