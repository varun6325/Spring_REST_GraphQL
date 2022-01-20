package com.adobe.demo.resolvers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.demo.dao.PublisherDao;
import com.adobe.demo.entity.Book;
import com.adobe.demo.entity.Publisher;

import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookFieldQueryResolver implements GraphQLResolver<Book> {

	@Autowired
	private PublisherDao publisherDao;

//	public Publisher publisher(Book book) {
//		System.out.println("publisher for " + book.getId());
//		if(book.getPublisherId() != null) {
//			return publisherDao.findById(book.getPublisherId()).get();
//		}
//		return new Publisher(0, "No Publisher");
//	}

//	public double rating(Book book) {
//		return 9.9;
//	}

	private final ExecutorService service = Executors.newFixedThreadPool(2); // seperate threads than that of Tomcat
																				// threads

	public CompletableFuture<Publisher> publisher(Book book) {
		log.info("Publisher request started for book {} " , book.getId() );
		return CompletableFuture.supplyAsync(() -> {
			Publisher publisher = publisherDao.findById(book.getPublisherId()).get();
			return publisher;
		}, service);
		
	}

//	public CompletableFuture<Publisher> publisher(Book book, DataFetchingEnvironment environment) {
//		DataLoader<Integer, Publisher> dataLoader = environment.getDataLoader("publisherDao");
//		
//		try {
//			return dataLoader.load(book.getPublisherId());
//		} catch (Exception e) {
//			return dataLoader.load(0);
//		}
//	}
}
