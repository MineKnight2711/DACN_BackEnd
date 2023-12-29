package com.example.dacn.modules.orders.service;

import com.example.dacn.entity.*;

import com.example.dacn.modules.account.service.AccountService;

import com.example.dacn.modules.dish.service.DishService;
import com.example.dacn.modules.orders.dto.OrderDetailsDTO;
import com.example.dacn.modules.orders.dto.OrderDishDTO;
import com.example.dacn.modules.orders.dto.OrdersDTO;

import com.example.dacn.modules.orders.dto.ReviewOrderDTO;
import com.example.dacn.modules.orders.repository.OrdersRepository;

import com.example.dacn.modules.payment.service.PaymentService;
import com.example.dacn.modules.transaction.OrderStatus;
import com.example.dacn.modules.voucher.service.VoucherService;
import com.example.dacn.modules.voucher_account.service.AccountVoucherService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private AccountVoucherService accountVoucherService;
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;
    public Orders getOrderById(String orderID)
    {
        Orders resultOrders=ordersRepository.findById(orderID).orElse(null);
        if(resultOrders!=null)
        {
            return resultOrders;
        }
        return null;
    }
    public ResponseModel getAllOrders()
    {
        List<OrderDetailsDTO> resultOrders = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findAll();

        for (Orders order : ordersList) {


            List<OrderDetail> orderDetailList = ordersRepository.findOrdersDetailByOrderID(order.getOrderID());
            PaymentDetails paymentDetails=paymentService.getPaymentDetailsByOrderId(order.getOrderID());
            OrderDetailsDTO orderDetailsDTO=new OrderDetailsDTO();
            orderDetailsDTO.setOrder(order);
            if(paymentDetails!=null){
                orderDetailsDTO.setPaymentMethod(paymentDetails.getPayment().getPaymentMethod());
            }
            else{
                orderDetailsDTO.setPaymentMethod("COD");
            }
            List<OrderDetailsDTO.DetailsDTO> detailsDTOs = modelMapper.map(orderDetailList, new TypeToken<List<OrderDetailsDTO.DetailsDTO>>() {}.getType());

            orderDetailsDTO.setDetailList(detailsDTOs);

            resultOrders.add(orderDetailsDTO);
        }
        return new ResponseModel("Success",resultOrders);
    }
    public Orders createOrder(String accountID, OrdersDTO ordersDTO) {
        ordersDTO.setOrderID("");

            Account acc = accountService.findById(accountID);
            if (acc == null) {
                return null;
            }
            Orders newOrder=ordersDTO.toEntity();
            if(ordersDTO.getVoucherId()!=null)
            {
                Voucher voucher=voucherService.findById(ordersDTO.getVoucherId());
                if(voucher!=null)
                {
                    newOrder.setVoucher(voucher);
                }
            }

            newOrder.setAccount(acc);
            ordersRepository.save(newOrder);
            String newOrderId=ordersRepository.findLatestOrderId();
            newOrder.setOrderID(newOrderId);
            System.out.println("Order id :"+newOrder.getOrderID());
            return newOrder;

    }
    public boolean updateOrder(String orderId) {
        Orders order=ordersRepository.findById(orderId).orElse(null);
        if(order!=null){
            order.setStatus(OrderStatus.STATUSPAID);
            if(order.getVoucher()!=null)
            {
                accountVoucherService.deleteVoucher(order.getAccount().getAccountID(),order.getVoucher().getVoucherID());
            }
            List<OrderDetail> detailList=ordersRepository.findOrdersDetailByOrderID(orderId);
            if(!detailList.isEmpty())
            {
                double orderTotal=0.0;
                for (OrderDetail detail:detailList)
                {
                    orderTotal+=detail.getPrice();
                }
                accountService.updateAccountPoints(order.getAccount().getAccountID(),orderTotal);
            }
            ordersRepository.save(order);
            return true;
        }
        return false;
    }
    public boolean cancelOrder(String orderId) {
        Orders order=ordersRepository.findById(orderId).orElse(null);
        if(order!=null){
            order.setStatus(OrderStatus.STATUSCANCEL);

            ordersRepository.save(order);
            return true;
        }
        return false;
    }

    public ResponseModel getOrderByAccountID(String accountId)
    {
        List<OrderDetailsDTO> resultOrders = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findOrdersByAccountId(accountId);

        for (Orders order : ordersList) {


            List<OrderDetail> orderDetailList = ordersRepository.findOrdersDetailByOrderID(order.getOrderID());
            PaymentDetails paymentDetails=paymentService.getPaymentDetailsByOrderId(order.getOrderID());
            OrderDetailsDTO orderDetailsDTO=new OrderDetailsDTO();
            orderDetailsDTO.setOrder(order);
            orderDetailsDTO.setPaymentMethod(paymentDetails.getPayment().getPaymentMethod());
            List<OrderDetailsDTO.DetailsDTO> detailsDTOs = modelMapper.map(orderDetailList, new TypeToken<List<OrderDetailsDTO.DetailsDTO>>() {}.getType());

            orderDetailsDTO.setDetailList(detailsDTOs);

            resultOrders.add(orderDetailsDTO);
        }
        if(!ordersList.isEmpty())
        {
            return new ResponseModel("Success",resultOrders);
        }
        return new ResponseModel("Fail","Tài khoản chưa có đơn hàng!");
    }
    public ResponseModel getOrderByOrderState(String accountId,String orderState)
    {
        List<OrderDetailsDTO> resultOrders = new ArrayList<>();
        List<Orders> ordersList=ordersRepository.findOrdersByOrderState(accountId,orderState);

        for (Orders order : ordersList) {

            List<OrderDetail> orderDetailList = ordersRepository.findOrdersDetailByOrderID(order.getOrderID());

            OrderDetailsDTO orderDetailsDTO=new OrderDetailsDTO();
            orderDetailsDTO.setOrder(order);

            List<OrderDetailsDTO.DetailsDTO> detailsDTOs = modelMapper.map(orderDetailList, new TypeToken<List<OrderDetailsDTO.DetailsDTO>>() {}.getType());

            orderDetailsDTO.setDetailList(detailsDTOs);

            resultOrders.add(orderDetailsDTO);
        }
        if(!ordersList.isEmpty())
        {
            return new ResponseModel("Success",resultOrders);
        }
        return new ResponseModel("Fail","Tài khoản chưa có đơn hàng!");

    }
    public ResponseModel reviewOrder(ReviewOrderDTO dto)
    {
        Orders order=ordersRepository.findById(dto.getOrderId()).orElse(null);
        if(order!=null)
        {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            order.setFeedBack(dto.getFeedBack());
            order.setScore(dto.getScore());
            order.setStatus(OrderStatus.STATUSRATED);
            order.setDateFeedBack(currentDate);
            ordersRepository.save(order);
            return new ResponseModel("Success",order);
        }
        return new ResponseModel("Fail","Không tìm thấy đơn hàng");
    }

    public boolean deleteOrder(String orderId)
    {
        Orders order=ordersRepository.findById(orderId).orElse(null);
        if(order!=null){

            ordersRepository.delete(order);
            return true;
        }
        return false;
    }

    public ResponseModel updateOrderStatus(String orderId ,String status)
    {
        Orders order=ordersRepository.findById(orderId).orElse(null);
        if(order!=null){
            order.setStatus(status);
            ordersRepository.save(order);
            return new ResponseModel("Success","Cập nhật đơn hàng thành công");
        }
        return new ResponseModel("Fail","Cập nhật đơn hàng thất bại");
    }
}
