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
    protected ResponseEntity<Object> handleAnimalNotFoundException(AnimalNotFoundException ex, HttpServletRequest request) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, 4041);
        return buildResponseEntity(apiError);
    }



    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


}


