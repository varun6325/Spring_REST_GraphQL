package com.adobe.demo.dao;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adobe.demo.entity.Author;

@Repository
public interface AuthorDao extends JpaRepository<Author, UUID> {
}
