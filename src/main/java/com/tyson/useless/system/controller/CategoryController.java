package com.tyson.useless.system.controller;

import com.tyson.useless.system.entity.Category;
import com.tyson.useless.system.ratelimit.RateLimited;
import com.tyson.useless.system.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryController {
	final CategoryService categoryService;
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/categories")
	public String findAllCategories(Model model) {
		model.addAttribute("categories", categoryService.findAllCategories());
		return "list-categories";
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/category/{id}")
	public String findCategoryById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", categoryService.findCategoryById(id));
		return "list-category";
	}

	@GetMapping("/addCategory")
	public String showCreateForm(Category category) {
		return "add-category";
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/add-category")
	public String createCategory(Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-category";
		}
		categoryService.createCategory(category);
		model.addAttribute("category", categoryService.findAllCategories());
		return "redirect:/categories";
	}

	@GetMapping("/updateCategory/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", categoryService.findCategoryById(id));
		return "update-category";
	}

	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/update-category/{id}")
	public String updateCategory(@PathVariable("id") Long id, Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			category.setId(id);
			return "update-category";
		}
		categoryService.updateCategory(category);
		model.addAttribute("category", categoryService.findAllCategories());
		return "redirect:/categories";
	}
	@RateLimited(permits = 40, period = 60)
	@RequestMapping("/remove-category/{id}")
	public String deleteCategory(@PathVariable("id") Long id, Model model) {
		categoryService.deleteCategory(id);
		model.addAttribute("category", categoryService.findAllCategories());
		return "redirect:/categories";
	}
}
