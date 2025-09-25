package br.com.alura.AluraFake.exceptions;

public class InvalidCourseStatusException extends RuntimeException {

    public InvalidCourseStatusException(String message) {
        super(message);
    }

}