package com.shelter.data.repositories;

import com.shelter.data.entities.ApiStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiStatusRepository extends JpaRepository<ApiStatus, Long> {
    ApiStatus findByStatusCode(int i);

    ApiStatus findByStatusCodeAndLanguage(int statusCode, String language);
}
