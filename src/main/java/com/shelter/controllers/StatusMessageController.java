package com.shelter.controllers;

import com.shelter.data.entities.ApiStatus;
import com.shelter.data.repositories.ApiStatusRepository;
import com.shelter.exceptions.ApiMessage;
import com.shelter.exceptions.ApiStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status-message")
public class StatusMessageController {
    private final ApiStatusRepository apiStatusRepository;

    public StatusMessageController(ApiStatusRepository apiStatusRepository) {
        this.apiStatusRepository = apiStatusRepository;
    }

    @GetMapping
    public ResponseEntity<ApiMessage> getStatusMessage(@RequestBody ApiStatusRequest request) {
        int statusCode = request.getStatusCode();
        String language = request.getLanguage();

        ApiStatus apiStatus = apiStatusRepository.findByStatusCodeAndLanguage(statusCode, language);

        if (apiStatus != null) {
            String message = apiStatus.getMessage();
            ApiMessage apiMessage = new ApiMessage(statusCode, message);
            return ResponseEntity.ok(apiMessage);
        }

        // Handle the case where the status code or language is not found
        return ResponseEntity.notFound().build();
    }
}
