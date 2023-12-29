package com.example.dacn.modules.delivery.service;

import com.example.dacn.entity.*;
import com.example.dacn.entity.ids.DeliveryDetailsId;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.delivery.dto.DeliveryDetailsDTO;
import com.example.dacn.modules.delivery.dto.DeliveryWithDetailsDTO;
import com.example.dacn.modules.delivery.repository.DeliveryDetailRepository;
import com.example.dacn.modules.delivery.repository.DeliveryRepository;
import com.example.dacn.modules.orders.service.OrdersService;
import com.example.dacn.modules.transaction.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DeliveryService
{
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DeliveryDetailRepository deliveryDetailRepository;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private AccountService accountService;
    public ResponseModel getDeliveryByAccountId(String accountId)
    {
        List<DeliveryWithDetailsDTO> result= new ArrayList<>();
        List<Delivery> deliveryList = deliveryRepository.findByAccountIdWithDetails(accountId);

        for (Delivery delivery : deliveryList) {
            DeliveryWithDetailsDTO dto=new DeliveryWithDetailsDTO();
            DeliveryDetails deliveryDetails = deliveryDetailRepository.findByDeliveryId(delivery.getDeliveryId());
            DeliveryDetailsDTO detailsDTOs = new DeliveryDetailsDTO();
            if(deliveryDetails!=null)
            {
                detailsDTOs.setOrder(deliveryDetails.getOrder());
            }
            dto.setDelivery(delivery);
            dto.setDetails(detailsDTOs);

            result.add(dto);
        }
        return new ResponseModel("Success",result);

    }

//    public ResponseModel getDeliveryDetailsByDeliverId(String accountId)
//    {
//        List<Delivery> deliveries=deliveryRepository.findByAccountId(accountId);
//        if(!deliveries.isEmpty())
//        {
//            return new ResponseModel("Success",deliveries);
//        }
//        return new ResponseModel("DeliveryNotFound","Không tìm thấy đơn giao hàng");
//    }
    public ResponseModel checkOrder(String orderId)
    {
        DeliveryDetails existsDeliveryDetails=deliveryDetailRepository.findByOrderId(orderId);
        if(existsDeliveryDetails!=null)
        {
            return new ResponseModel("AlreadyAsigned","Đơn hàng đang được thực hiện bởi người khác");
        }
        return new ResponseModel("OK",null);
    }
    public ResponseModel asignOrder(String orderId,String accountId)
    {
        try
        {
            Account deliverAccount=accountService.findById(accountId);
            if(deliverAccount==null)
            {
                return new ResponseModel("AccountNotFound","Không tìm thấy tài khoản");
            }
            Orders ordersDeliver=ordersService.getOrderById(orderId);
            if(ordersDeliver==null)
            {
                return new ResponseModel("OrdersNotFound","Không tìm thấy đơn hàng");
            }

            Delivery newDelivery=new Delivery();
            newDelivery.setDeliveryId("");
            newDelivery.setAccount(deliverAccount);
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            newDelivery.setDateAccepted(currentDate);
            deliveryRepository.save(newDelivery);
            String newDeliveryId=deliveryRepository.findLatestAddressId();
            newDelivery.setDeliveryId(newDeliveryId);
            DeliveryDetails existsDeliveryDetails=deliveryDetailRepository.findExistsDelivery(orderId,accountId);
            if(existsDeliveryDetails!=null)
            {
                Delivery existsDelivery=deliveryRepository.findById(newDeliveryId).orElse(null);
                deliveryRepository.delete(existsDelivery);
                return new ResponseModel("ExistsDelivery","Bạn đã giao đơn này cho shipper này rồi!");
            }
            saveDeliveryDetails(newDelivery,ordersDeliver);
            ordersService.updateOrderStatus(orderId, OrderStatus.STATUSPROCESING);
            return new ResponseModel("Success",newDelivery);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseModel("Fail","Có lỗi xảy ra");
        }
    }
    private void saveDeliveryDetails(Delivery delivery,Orders order)
    {
        DeliveryDetailsId deliveryDetailsId=new DeliveryDetailsId();
        deliveryDetailsId.setDelivery_id(delivery.getDeliveryId());
        deliveryDetailsId.setOrder_id(order.getOrderID());

        DeliveryDetails newDeliveryDetails=new DeliveryDetails();
        newDeliveryDetails.setDeliveryDetailsId(deliveryDetailsId);
        newDeliveryDetails.setOrder(order);
        newDeliveryDetails.setDelivery(delivery);

        deliveryDetailRepository.save(newDeliveryDetails);
    }
    public ResponseModel completeDelivery(String deliveryId){

        Delivery currentDelivery=deliveryRepository.findById(deliveryId).orElse(null);
        if(currentDelivery!=null)
        {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            currentDelivery.setDateDelivered(currentDate);
            deliveryRepository.save(currentDelivery);
            return new ResponseModel("Success","Giao hàng thành công!");
        }
        return new ResponseModel("Fail","Có lỗi xảy ra");
    }

}
