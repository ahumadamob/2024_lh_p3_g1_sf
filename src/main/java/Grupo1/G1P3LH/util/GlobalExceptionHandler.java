package Grupo1.G1P3LH.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DTOResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        DTOResponse<String> response = new DTOResponse<>(404, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DTOResponse<List<String>>> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        DTOResponse<List<String>> response = new DTOResponse<>(400, errors, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Puedes agregar más métodos para manejar otras excepciones específicas
}