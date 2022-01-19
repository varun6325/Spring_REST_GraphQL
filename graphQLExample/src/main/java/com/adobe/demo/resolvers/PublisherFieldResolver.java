package com.adobe.demo.resolvers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.demo.dao.BookDao;
import com.adobe.demo.entity.Book;
import com.adobe.demo.entity.Publisher;

import graphql.kickstart.tools.GraphQLResolver;

@Component
public class PublisherFieldResolver implements GraphQLResolver<Publisher> {
	
	@Autowired
	private BookDao bookDao;
	
	public List<Book> books(Publisher pub) {
		return bookDao.getByPubId(pub.getId());
	}
}
