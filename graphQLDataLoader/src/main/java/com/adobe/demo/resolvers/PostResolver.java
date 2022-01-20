package com.adobe.demo.resolvers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.demo.dao.PostDao;
import com.adobe.demo.entity.Post;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class PostResolver implements GraphQLQueryResolver {
	@Autowired
	private PostDao postDao;
	
	public List<Post> posts() {
		return postDao.findAll();
	}
}
