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
@Entity(name="Category")
@Table(name="Category")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long categoryID;
    @Column(name = "categoryName")
    private String categoryName;

    @OneToMany (mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dish> dishes;
}
