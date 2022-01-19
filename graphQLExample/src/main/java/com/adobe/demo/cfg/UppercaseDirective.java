package com.adobe.demo.cfg;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public class UppercaseDirective implements SchemaDirectiveWiring {
	  @Override
	  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
	    GraphQLFieldDefinition field = env.getElement();
	    GraphQLFieldsContainer parentType = env.getFieldsContainer();
	    DataFetcher original = env.getCodeRegistry().getDataFetcher(parentType, field);
	    
	    DataFetcher dataFetcher =  DataFetcherFactories.wrapDataFetcher(original, (fetchEnv, value) -> {
	    		return ((String)value).toUpperCase();
	    });
	    
	    FieldCoordinates coords = FieldCoordinates.coordinates(parentType, field);
	    env.getCodeRegistry().dataFetcher(coords, dataFetcher);
	    return field;
	  }
	}