package com.example.dacn.entity;

import com.example.dacn.entity.ids.DeliveryDetailsId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Getter
@Setter
@Entity(name="DeliveryDetails")
@Table(name="DeliveryDetails")
public class DeliveryDetails
{
    @EmbeddedId
    private DeliveryDetailsId deliveryDetailsId;

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false,insertable=false, updatable=false)
    private Delivery delivery;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id",nullable = false,insertable=false, updatable=false)
    private Orders order;
}
