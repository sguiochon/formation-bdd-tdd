package fr.axa.formation.domain.errors;

public class DomainException extends RuntimeException {

    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
