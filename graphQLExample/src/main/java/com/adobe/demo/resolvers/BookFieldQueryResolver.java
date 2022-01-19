package com.adobe.demo.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.demo.dao.PublisherDao;
import com.adobe.demo.entity.Book;
import com.adobe.demo.entity.Publisher;

import graphql.kickstart.tools.GraphQLResolver;

@Component
public class BookFieldQueryResolver implements GraphQLResolver<Book> {
	
	@Autowired
	private PublisherDao  publisherDao;
	
	public Publisher publisher(Book book) {
		System.out.println("publisher for " + book.getId());
		if(book.getPublisherId() != null) {
			return publisherDao.findById(book.getPublisherId()).get();
		}
		return new Publisher(0, "No Publisher");
	}
	
//	public double rating(Book book) {
//		return 9.9;
//	}
}
