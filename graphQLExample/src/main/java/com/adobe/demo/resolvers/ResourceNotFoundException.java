package com.adobe.demo.resolvers;

import java.util.List;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class ResourceNotFoundException extends RuntimeException implements GraphQLError {

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorClassification getErrorType() {
		return null;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
