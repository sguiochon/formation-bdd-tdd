package fr.axa.formation.api.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseViewModel<T> implements Serializable {

    private final Status status;
    private final String message;
    private final T data;

    public ResponseViewModel(final Status status, final String message, final T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return this.status;
    }

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    @JsonProperty("data")
    public T getData() {
        return this.data;
    }
}
