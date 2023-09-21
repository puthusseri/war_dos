package com.tyson.useless.system.service;

import java.util.List;

import com.tyson.useless.system.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

	public List<Author> findAllAuthors();

	public Author findAuthorById(Long id);

	public void createAuthor(Author author);

	public void updateAuthor(Author author);

	public void deleteAuthor(Long id);

	public Page<Author> findPaginated(Pageable pageable);

}
