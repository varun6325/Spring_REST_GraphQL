package com.adobe.demo.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adobe.demo.entity.Post;

@Repository
public interface PostDao extends JpaRepository<Post, UUID> {
    List<Post> findAllByAuthor_Id(UUID authorId);
}