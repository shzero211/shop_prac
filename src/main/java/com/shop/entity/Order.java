package com.shop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.shop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
	
@Id
@GeneratedValue
@Column(name = "order_id")
private Long id;

@ManyToOne
@JoinColumn(name = "member_id")
private Member member;

@OneToMany(mappedBy = "order")
private List<OrderItem> orderItems=new ArrayList<>();

private LocalDateTime orderDate;

@Enumerated(EnumType.STRING)
private OrderStatus orderStatus;

private LocalDateTime regTime;

private LocalDateTime updateTime;

}