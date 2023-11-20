package com.example.dacn.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@Embeddable
public class OrderDetailsId implements Serializable {
    @Column(name = "dish_id")
    private String dish_id;
    @Column(name = "order_id")
    private String order_id;

}
