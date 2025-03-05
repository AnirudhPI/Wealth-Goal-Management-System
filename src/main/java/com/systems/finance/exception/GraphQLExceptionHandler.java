package com.systems.finance.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// handle exceptions globally
@ControllerAdvice
public class GraphQLExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public GraphQLError handleUserNotFoundException(UserNotFoundException e){
        return GraphqlErrorBuilder.newError()
                .message(e.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(InvalidInputException.class)
    public GraphQLError handleInvalidInputException(InvalidInputException e){
        return GraphqlErrorBuilder.newError()
                .message(e.getMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public GraphQLError handleException(Exception e){
        return GraphqlErrorBuilder.newError()
                .message("Internal server error")
                .errorType(ErrorType.INTERNAL_ERROR)
                .build();
    }
}