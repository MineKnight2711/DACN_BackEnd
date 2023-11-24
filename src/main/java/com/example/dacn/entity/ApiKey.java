package com.example.dacn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity(name = "ApiKey")
@Table(name = "ApiKey")
public class ApiKey
{
    @Id
    private Integer id;

    @Column(unique=true)
    private String clientId;

    private String apiKey;

}
