package com.example.dacn.modules.apikey.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.apikey.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/apikey")
public class ApiKeyController
{
    @Autowired
    private ApiKeyService apiKeyService;
    @PostMapping
    public ResponseModel hashedApiKey(@RequestParam("apiKey") String apiKey, @RequestParam("clientId") String clientId)
    {
        return apiKeyService.createApiKey(apiKey,clientId);
    }
    @PostMapping("/validate")
    public ResponseModel validateApiKey(@RequestParam("apiKey") String apiKey,
                                        @RequestParam("hashedApiKey") String hashedApiKey,
                                        @RequestParam("clientId") String clientId,
                                        @RequestParam("hashClientId") String hashClientId)
    {
        return apiKeyService.validateApiKey(apiKey,hashedApiKey,clientId,hashClientId);
    }
}
