package com.example.dacn.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Data
@Getter
@Setter
@Entity(name = "Dish")
@Table(name = "Dish")
public class Dish {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long dishID;
    @Column(name = "dishName")
    private String dishName;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Min(value = 0, message = "Số lượng tồn ít nhất là 0 !")
    @Column(name = "inStock")
    private int inStock;
    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "categoryID",nullable = true)
    private Category category;

}
