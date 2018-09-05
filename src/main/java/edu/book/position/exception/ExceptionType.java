package edu.book.position.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.ws.rs.core.Response.Status.*;

public enum ExceptionType {
    JSON_MAPPING_EXCEPTION(JsonMappingException.class,BAD_REQUEST),
    VALIDATION_EXCEPTION(ValidationException.class, BAD_REQUEST),
    TRADE_ORDER_EXCEPTION(TradeOrderException.class, INTERNAL_SERVER_ERROR),
    NOT_FOUND_EXCEPTION(NotFoundException.class,NOT_FOUND),
    GENERIC_EXCEPTION(Exception.class, INTERNAL_SERVER_ERROR);

    private static final Map<Class<? extends Throwable>, ExceptionType> MAPPING = new HashMap();

    static {
        Arrays.stream(ExceptionType.values())
                .forEach(exceptionType -> MAPPING.put(exceptionType.exceptionClass, exceptionType));
    }

    private final Class<? extends Throwable> exceptionClass;
    private final Response.Status statusCode;

    ExceptionType(Class<? extends Throwable> exceptionClass, Response.Status statusCode) {
        this.exceptionClass = exceptionClass;
        this.statusCode = statusCode;
    }

    public static ExceptionType getType(Class<? extends Throwable> exceptionClass) {
        ExceptionType exceptionType = MAPPING.get(exceptionClass);
        if (Objects.nonNull(exceptionType))
            return exceptionType;
        else
            return GENERIC_EXCEPTION;
    }

    public Response.Status getStatusCode() {
        return statusCode;
    }
}
