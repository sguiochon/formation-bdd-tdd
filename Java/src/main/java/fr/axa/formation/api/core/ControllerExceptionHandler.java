package fr.axa.formation.api.core;

import fr.axa.formation.api.errors.NoDataFoundException;
import fr.axa.formation.domain.errors.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private static final String SERVICE_UNAVAILABLE = "Service indisponible";
    private static final String NOT_FOUND = "Pas de donnée trouvée";
    private static final String VALIDATION_ERROR = "Erreur lors de la validation des données";

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ResponseViewModel<Object>> processValidationError(MethodArgumentNotValidException exception) {
        final Map<String, Collection<String>> errors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        TreeMap::new,
                        Collectors.mapping(
                                FieldError::getDefaultMessage,
                                Collectors.toCollection(TreeSet::new)
                        )
                ));
        return ViewModelFactory.badRequest(VALIDATION_ERROR, errors);
    }

    @ExceptionHandler({ DomainException.class })
    public ResponseEntity<ResponseViewModel<Object>> processServiceException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        final String message;
        if (StringUtils.isEmpty(exception.getMessage())) {
            message = SERVICE_UNAVAILABLE;
        } else {
            message = exception.getMessage();
        }
        return ViewModelFactory.error(message);
    }

    @ExceptionHandler({NoDataFoundException.class })
    public ResponseEntity<ResponseViewModel<Object>> processNoDataFoundException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        final String message;
        if (StringUtils.isEmpty(exception.getMessage())) {
            message = NOT_FOUND;
        } else {
            message = exception.getMessage();
        }
        return ViewModelFactory.noData(message);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ResponseViewModel<Object>> processRuntimeException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        return ViewModelFactory.error(SERVICE_UNAVAILABLE);
    }

}
