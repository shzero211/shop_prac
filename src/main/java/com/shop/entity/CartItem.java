package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cart_item")
public class CartItem {
@Id
@GeneratedValue
@Column(name = "cart_item_id")
private Long id;

@ManyToOne
@JoinColumn(name="cart_id")
private Cart cart;

@ManyToOne
@JoinColumn(name="item_id")
private Item item;

private int count;
}
