package com.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistDto;
import com.shop.dto.OrderItemDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.entity.Member;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.ItemRepositoryCustom;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
private final ItemRepository itemRepository;
private final MemberRepository memberRepository;
private final OrderRepository orderRepository;
private final ItemImgRepository itemImgRepository;

public Long order(OrderDto orderDto,String email) {
	Item item=itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
	Member member=memberRepository.findByEmail(email);
	
	List<OrderItem> orderItemList=new ArrayList<>();
	OrderItem orderItem=OrderItem.createOrderItem(item, orderDto.getCount());
	orderItemList.add(orderItem);
	Order order=Order.createOrder(member, orderItemList);
	orderRepository.save(order);
	return order.getId();
}

@Transactional(readOnly = true)
public Page<OrderHistDto> getOrderList(String email,Pageable pageable){
	List<Order> orders=orderRepository.findOrders(email, pageable);
	
	Long totalCount=orderRepository.countOrder(email);
	
	List<OrderHistDto> orderHistDtos=new ArrayList<>();
	
	for(Order order:orders) {
		OrderHistDto orderHistDto=new OrderHistDto(order);
		List<OrderItem> orderItems=order.getOrderItems();
		for(OrderItem orderItem:orderItems) {
			ItemImg itemImg=itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(),"Y");
			OrderItemDto orderItemDto=new OrderItemDto(orderItem,itemImg.getImgUrl());
			orderHistDto.addOrderItemDto(orderItemDto);
		}
		orderHistDtos.add(orderHistDto);
	}
	return new PageImpl<OrderHistDto>(orderHistDtos,pageable,totalCount);
}
}
