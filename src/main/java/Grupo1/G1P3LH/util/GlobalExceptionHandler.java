package Grupo1.G1P3LH.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DTOApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        DTOApiResponse<String> response = new DTOApiResponse<>(404, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DTOApiResponse<List<String>>> handleConstraintViolationException(
            ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        DTOApiResponse<List<String>> response = new DTOApiResponse<>(400, errors, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DTOApiResponse<List<String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        DTOApiResponse<List<String>> response = new DTOApiResponse<>(400, errors, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DTOApiResponse<String>> handleGlobalException(Exception ex) {
        DTOApiResponse<String> response = new DTOApiResponse<>(500, "Ocurrió un error interno.", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DTOApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        DTOApiResponse<String> response = new DTOApiResponse<>(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
