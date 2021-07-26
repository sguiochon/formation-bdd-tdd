package fr.axa.formation.api.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Map;

public class ViewModelFactory {

    private ViewModelFactory() {
        throw new IllegalStateException("Do not instantiate this class");
    }

    public static <T> ResponseEntity<ResponseViewModel<T>> success(final T data, final String message) {
        final ResponseViewModel<T> response = new ResponseViewModel<>(Status.SUCCESS, message, data);
        return responseEntityFactory(response, HttpStatus.OK);
    }

    public static ResponseEntity<ResponseViewModel<Object>> success(final String message) {
        return success(null, message);
    }

    public static ResponseEntity<ResponseViewModel<Object>> error(final String message) {
        final ResponseViewModel<Object> response = new ResponseViewModel<>(Status.ERROR, message, null);
        return responseEntityFactory(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ResponseViewModel<Object>> badRequest(final String message, final Map<String, Collection<String>> entries) {
        final ResponseViewModel<Object> response = new ResponseViewModel<>(Status.ERROR, message, entries);
        return responseEntityFactory(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ResponseViewModel<Object>> badRequest(final String message) {
        return badRequest(message, null);
    }

    public static ResponseEntity<ResponseViewModel<Object>> noData(final String message) {
        final ResponseViewModel<Object> response = new ResponseViewModel<>(Status.ERROR, message, null);
        return responseEntityFactory(response, HttpStatus.NOT_FOUND);
    }

    private static <T> ResponseEntity<ResponseViewModel<T>> responseEntityFactory(ResponseViewModel<T> response, HttpStatus httpStatus) {
        return new ResponseEntity<>(response, null, httpStatus);
    }
}
