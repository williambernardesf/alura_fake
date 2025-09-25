package br.com.alura.AluraFake.util;

import br.com.alura.AluraFake.exceptions.InvalidCourseStatusException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorItemDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorItemDTO> errors = ex.getBindingResult().getFieldErrors().stream().map(ErrorItemDTO::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorItemDTO>> handleDatabaseValidation(DataIntegrityViolationException ex) {
        List<ErrorItemDTO> errors = List.of(new ErrorItemDTO("database", ex.getMostSpecificCause().getMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InvalidCourseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorItemDTO> handleInvalidCourseStatus(InvalidCourseStatusException ex) {
        ErrorItemDTO error = new ErrorItemDTO("course", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorItemDTO> handleOptionsData(IllegalArgumentException ex) {
        ErrorItemDTO error = new ErrorItemDTO("options", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

}