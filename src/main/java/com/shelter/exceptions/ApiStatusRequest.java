package com.shelter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiStatusRequest {
    private int statusCode;
    private String language;
}
