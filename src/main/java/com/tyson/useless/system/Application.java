package com.tyson.useless.system;

import java.util.Arrays;

import com.tyson.useless.system.constant.ConfigurationConstants;
import com.tyson.useless.system.repository.UserRepository;
import com.tyson.useless.system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tyson.useless.system.entity.Author;
import com.tyson.useless.system.entity.Book;
import com.tyson.useless.system.entity.Category;
import com.tyson.useless.system.entity.Publisher;
import com.tyson.useless.system.entity.Role;
import com.tyson.useless.system.entity.User;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private BookService bookService;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initialCreate() {
		return (args) -> {

			var book = new Book("978-0-307-35214-9", "Quiet", "0307352145", "The Power of Introverts in a World That..");
			book.addAuthors(new Author("Susan Cain", "A World That Can’t Stop Talking"));
			book.addCategories(new Category("Life Lesson"));
			book.addPublishers(new Publisher("Crown"));
			bookService.createBook(book);

			var book1 = new Book("978-1847941831", "Atomic Habits", "1847941834", "Atomic Habits is a step-by-step manual for changing routines");
			book1.addAuthors(new Author("James Clear", "Speaker focused on habits, decision making, and continuous improvement"));
			book1.addCategories(new Category("Self-Help for Happiness"));
			book1.addPublishers(new Publisher("Random House Business Books"));
			bookService.createBook(book1);

			var book2 = new Book("978-1786330895", "Ikigai", "178633089X", "Welcome to Okinawa in Japan, where the inhabitants live for longer than in any other place in the world");
			book2.addAuthors(new Author("Héctor García", "His most well-known books are Ikigai, A Geek in Japan, and Ichigo Ichie"));
			book2.addCategories(new Category("Meditation"));
			book2.addPublishers(new Publisher("Hutchinson"));
			bookService.createBook(book2);

			var user = new User("admin", "admin", ConfigurationConstants.USER_NAME, passwordEncoder.encode(ConfigurationConstants.PASSWORD),
					Arrays.asList(new Role("ROLE_ADMIN")));
			userRepository.save(user);

		};
	}
}
