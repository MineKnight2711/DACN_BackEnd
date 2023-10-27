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
    private String categoryID;
    @Column(name = "categoryName")
    private String categoryName;
    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToMany (mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dish> dishes;
}
