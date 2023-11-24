package com.example.dacn.modules.orders.service;

import com.example.dacn.entity.Dish;
import com.example.dacn.entity.OrderDetail;
import com.example.dacn.entity.Orders;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.entity.ids.OrderDetailsId;
import com.example.dacn.modules.dish.service.DishService;
import com.example.dacn.modules.orders.dto.OrderDishDTO;
import com.example.dacn.modules.orders.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService
{
    @Autowired
    private DishService dishService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    public void saveOrderDetails(Orders order, List<OrderDishDTO> listDish)
    {

        String dishId="";
        for(OrderDishDTO dishDto : listDish){
            System.out.println("Dish id:"+(dishId+=","+dishDto.getDishId()));
            Dish dish = dishService.findById(dishDto.getDishId());
            if(dish!=null)
            {
                OrderDetailsId newOrderDetailsId=new OrderDetailsId();
                OrderDetail newOrderDetail=new OrderDetail();
                newOrderDetailsId.setOrder_id(order.getOrderID());
                newOrderDetailsId.setDish_id(dish.getDishID());
                newOrderDetail.setOrderDetailID(newOrderDetailsId);
                newOrderDetail.setOrder(order);
                newOrderDetail.setDish(dish);
                newOrderDetail.setAmount(dishDto.getQuantity());
                newOrderDetail.setPrice(dish.getPrice()*dishDto.getQuantity());
                orderDetailRepository.save(newOrderDetail);
            }

        }
    }

    public ResponseModel getOrderDetails(String orderID)
    {
        List<OrderDetail> orderDetails=orderDetailRepository.getOrderDetailByOrderId(orderID);
        if(!orderDetails.isEmpty())
        {
            return new ResponseModel("Success",orderDetails);
        }
        return new ResponseModel("Fail","Không tìm thấy chi tiết đơn hàng!");
    }
}
