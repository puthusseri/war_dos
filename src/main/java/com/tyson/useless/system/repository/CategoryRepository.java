package com.tyson.useless.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyson.useless.system.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
