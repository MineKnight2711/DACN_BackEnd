package com.example.dacn.modules.apikey;

import com.example.dacn.entity.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyService {
    @Value("${api.key}")
    private String storedApiKey;

    @Value("${client.id}")
    private String storedClientId;
    private final BCryptPasswordEncoder passwordEncoder;

    public ApiKeyService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseModel createApiKey(String apiKey,String clientId)
    {
        String hashedApiKey=passwordEncoder.encode(apiKey);
        String hashedClientId=passwordEncoder.encode(clientId);
//        ApiKey apiKeyEntity=new ApiKey();
//        apiKeyEntity.setApiKey(hashedApiKey);
//        apiKeyEntity.setClientId(hashedClientId);
        System.out.println("Api key:"+hashedApiKey);
        System.out.println("Client id:"+hashedClientId);
        return new ResponseModel("Success",String.format("Api key : %s - ClientId : %s",hashedApiKey,hashedClientId));
    }
    public ResponseModel validateApiKey(String apiKey,String hashedApiKey,String clientId,String hashedClientId)
    {
        if(passwordEncoder.matches(apiKey, hashedApiKey)&&passwordEncoder.matches(clientId, hashedClientId))
        {
            return new ResponseModel("Success","Api key validate successfully");
        }
        return new ResponseModel("Fail",null);
    }
    public boolean isApiValid(String apiKey, String clientId)
    {
        if(apiKey.equals(storedApiKey)&&clientId.equals(storedClientId))
        {
            System.out.println("Api key truyen vao :"+apiKey+"-"+"Client id truyen vao"+clientId);
            System.out.println("Api key luu tru :"+storedApiKey+"-"+"Client id luu tru"+storedClientId);
            return true;
        }
        return false;
    }

}