package com.india.kamsid.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.india.kamsid.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

	Optional<Category> findByCatgName(String categoryName);

}
