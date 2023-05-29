package com.shelter.exceptions;

import com.shelter.data.entities.ApiStatus;
import com.shelter.data.repositories.ApiStatusRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApiStatusRepository apiStatusRepository;

    public RestExceptionHandler(ApiStatusRepository apiStatusRepository) {
        this.apiStatusRepository = apiStatusRepository;
    }

    @ExceptionHandler(AnimalNotFoundException.class)
    protected ResponseEntity<String> handleAnimalNotFoundException(AnimalNotFoundException ex, HttpServletRequest request) {

        /*ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, 4041);
        return buildResponseEntity(apiError);*/

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        /*ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, 4042);
        return buildResponseEntity(apiError);*/
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(WalkNotFoundException.class)
    protected ResponseEntity<Object> handleWalkNotFoundException(WalkNotFoundException ex, HttpServletRequest request) {
        /*ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, 4043);
        return buildResponseEntity(apiError);*/
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }



    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


}


