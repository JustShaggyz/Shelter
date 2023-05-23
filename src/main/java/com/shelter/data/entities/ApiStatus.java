package com.shelter.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "api_status")
public class ApiStatus extends BaseEntity{
    @Column(name = "status_code")
    private int statusCode;

    @Column(name = "language")
    private String language;

    @Column(name = "message")
    private String message;


}
