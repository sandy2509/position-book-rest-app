package edu.book.position.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    private static Logger LOGGER = LoggerFactory.getLogger(GenericExceptionMapper.class);

    public GenericExceptionMapper() {
    }

    @Override
    public Response toResponse(Throwable exception) {
        LOGGER.info("Creating response with Exception:[{}]", exception);

        ExceptionType exceptionType = ExceptionType.getType(exception.getClass());
        if (exceptionType == ExceptionType.GENERIC_EXCEPTION) {
            exception.printStackTrace();
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(exception.getMessage());
        return Response.status(exceptionType.getStatusCode())
                .type(MediaType.APPLICATION_JSON).entity(exceptionResponse)
                .build();
    }
}