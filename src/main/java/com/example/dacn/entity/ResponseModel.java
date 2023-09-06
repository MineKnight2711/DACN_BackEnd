package com.example.dacn.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseModel {
    private UUID id;
    private String apiVersion;
    private String message;
    private Object data;

    public ResponseModel(String message, Object data) {
        this.id = UUID.randomUUID();
        this.apiVersion = "1.0";
        this.message = message;
        this.data = data;
    }
}
