package com.tyson.useless.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyson.useless.system.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
