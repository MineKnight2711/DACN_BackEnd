package com.example.dacn.modules.apikey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyService {
    @Autowired
    private ApiKeyRepository apiKeyRepository;


    public boolean isApiValid(String clientId, String apiKey)
    {
        if(apiKeyRepository.isValidApiKey(clientId,apiKey))
        {
            return true;
        }
        return false;
    }

}