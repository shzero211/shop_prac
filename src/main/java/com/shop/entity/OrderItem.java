package com.shop.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Setter;

import lombok.Getter;

@Entity
@Getter
@Setter
public class OrderItem extends BaseTimeEntity {
@Id
@GeneratedValue
@Column(name = "order_item_id")
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "item_id")
private Item item;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="order_id")
private Order order;

private int orderPrice;

private int count;


public static OrderItem createOrderItem(Item item,int count) {
	OrderItem orderItem=new OrderItem();
	orderItem.setItem(item);
	orderItem.setCount(count);
	orderItem.setOrderPrice(item.getPrice());
	item.removeStock(count);
	return orderItem;
}
public int getTotalPrice() {
	return orderPrice*count;
}

public void cancel() {
	this.getItem().addStock(count);
}
}
