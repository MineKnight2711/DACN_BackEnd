package com.example.dacn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Table(name = "Favorite")
@Entity(name = "Favorite")
public class Favorite
{
    @Id
    private String favoriteID;

    @ManyToOne
    @JoinColumn(name = "accountID",nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "dishID",nullable = false)
    private Dish dish;
}
