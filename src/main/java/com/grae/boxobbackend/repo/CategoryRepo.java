package com.grae.boxobbackend.repo;

import com.grae.boxobbackend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {

}

