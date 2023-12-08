package com.example.dacn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Entity(name = "Dish")
@Table(name = "Dish")
public class Dish {
    @Id
    private String dishID;
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
    @Column(name = "dateCreate")
    private Date dateCreate;
    // quan he nhieu nhieu tới bảng orders
    @ManyToMany
    @JoinTable(name = "OrderDetail",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    @JsonIgnore
    private List<Orders> orders;

    //Quan hệ 1 nhiều tới bảng category
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "categoryID",nullable = true)
    private Category category;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Favorite> favorites;

    //Quan hệ 1 nhiều tới bảng cart
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Cart> carts;

}
