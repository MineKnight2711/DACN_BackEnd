package com.example.dacn.modules.apikey;

import com.example.dacn.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    @Query("SELECT CASE WHEN COUNT(k) > 0 THEN TRUE ELSE FALSE END FROM ApiKey k WHERE k.clientId = :clientId AND k.apiKey = :apiKey")
    boolean isValidApiKey( String clientId, String apiKey);
}
