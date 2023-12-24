package com.example.dacn.modules.delivery.repository;

import com.example.dacn.entity.DeliveryDetails;
import com.example.dacn.entity.ids.DeliveryDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetails,DeliveryDetailsId>
{
    @Query("SELECT details FROM DeliveryDetails details WHERE details.order.orderID=:orderId AND details.delivery.account.accountID=:accountId")
    DeliveryDetails findExistsDelivery(String orderId, String accountId);
    @Query("SELECT details FROM DeliveryDetails details WHERE details.delivery.deliveryId=:deliveryId")
    List<DeliveryDetails> findByDeliveryId(String deliveryId);
}
