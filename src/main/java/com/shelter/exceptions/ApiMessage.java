package com.shelter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiMessage {
    private int statusCode;
    private String message;
}
