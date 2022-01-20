package com.adobe.demo.resolvers;

import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import com.adobe.demo.entity.Author;
import com.adobe.demo.entity.Post;

import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class PostFieldResolver implements GraphQLResolver<Post>{
	// Async Resolver
	public CompletableFuture<Author> getAuthor(Post post, DataFetchingEnvironment environment)  {
        DataLoader<Integer, Author> dataLoader = environment.getDataLoader("authorDataLoader");
        return dataLoader.load(post.getAuthorId());
    }
}
