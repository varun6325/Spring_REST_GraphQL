package com.adobe.demo.cfg;


import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.demo.dao.AuthorDao;
import com.adobe.demo.entity.Author;

import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;

@Component
public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {

	@Autowired
	private AuthorDao authorDao;
	

    @Override
    public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String userId = httpServletRequest.getHeader("user-id");

        DefaultGraphQLServletContext servletContext = DefaultGraphQLServletContext.createServletContext()
                .with(httpServletRequest)
                .with(httpServletResponse)
                .with(buildDataLoaderRegistry())
                .build();

        return new CustomGraphQLContext(userId, servletContext);
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
        return DefaultGraphQLWebSocketContext.createWebSocketContext()
                .with(session)
                .with(handshakeRequest)
                .build();
    }

    @Override
    public GraphQLContext build() {
        throw new IllegalStateException("UnSupported");
    }
    
    private DataLoaderRegistry buildDataLoaderRegistry() {
        DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
        DataLoader<UUID, Author> authorLoader =
            new DataLoader<>(
                authorIds -> supplyAsync(() -> authorDao.findAllById(authorIds)));
        dataLoaderRegistry.register("authorDataLoader", authorLoader);
        return dataLoaderRegistry;
      }
}