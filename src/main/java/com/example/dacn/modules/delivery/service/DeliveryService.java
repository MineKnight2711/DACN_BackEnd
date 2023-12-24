package com.example.dacn.modules.delivery.service;

import com.example.dacn.entity.*;
import com.example.dacn.entity.ids.DeliveryDetailsId;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.delivery.dto.DeliveryDetailsDTO;
import com.example.dacn.modules.delivery.dto.DeliveryWithDetailsDTO;
import com.example.dacn.modules.delivery.repository.DeliveryDetailRepository;
import com.example.dacn.modules.delivery.repository.DeliveryRepository;
import com.example.dacn.modules.dish.dto.DishFavoriteCountDTO;
import com.example.dacn.modules.orders.dto.OrderDetailsDTO;
import com.example.dacn.modules.orders.service.OrdersService;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private ModelMapper modelMapper;
    List<DeliveryWithDetailsDTO> mapTuplesToDTO(List<Tuple> tuples) {

        return tuples.stream().map(tuple -> {
            DeliveryWithDetailsDTO dto = new DeliveryWithDetailsDTO();

            dto.setDelivery(tuple.get("delivery", Delivery.class));

            // Specify DeliveryDetails.class instead of just Type
            dto.setDetails(tuple.get("details", List.class));

            return dto;
        }).collect(Collectors.toList());
    }
    public ResponseModel getDeliveryByAccountId(String accountId)
    {
        List<DeliveryWithDetailsDTO> result= new ArrayList<>();
        List<Delivery> deliveryList = deliveryRepository.findByAccountIdWithDetails(accountId);

        for (Delivery delivery : deliveryList) {
            DeliveryWithDetailsDTO dto=new DeliveryWithDetailsDTO();
            List<DeliveryDetails> deliveryDetails = deliveryDetailRepository.findByDeliveryId(delivery.getDeliveryId());
            List<DeliveryDetailsDTO> detailsDTOs = modelMapper.map(deliveryDetails, new TypeToken<List<DeliveryDetailsDTO>>() {}.getType());
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
    public ResponseModel acceptOrder(String orderId,String accountId)
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
                return new ResponseModel("ExistsDelivery","Bạn đã nhận đơn hàng này rồi!");
            }
            saveDeliveryDetails(newDelivery,ordersDeliver);
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
}
