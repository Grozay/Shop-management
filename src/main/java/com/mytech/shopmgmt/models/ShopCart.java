package com.mytech.shopmgmt.models;

import jakarta.persistence.*;
import com.mytech.shopmgmt.models.Customer;

import java.util.List;

@Entity
@Table(name = "ShopCarts")
public class ShopCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cartId;
    @OneToOne
    Customer customer;

    Double total;
    @OneToMany(mappedBy = "shopCart")
    List<CartLine> cartLineList;

    // Constructor mặc định (yêu cầu của JPA)
    public ShopCart() {
        // Không cần khởi tạo gì trong này
        super();
    }

    // Constructor đầy đủ tham số (giữ nguyên để sử dụng trong code của bạn)
    public ShopCart(Long cartId, Customer customer, Double total, List<CartLine> cartLineList) {
        this.cartId = cartId;
        this.customer = customer;
        this.total = total;
        this.cartLineList = cartLineList;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<CartLine> getCartLineList() {
        return cartLineList;
    }

    public void setCartLineList(List<CartLine> cartLineList) {
        this.cartLineList = cartLineList;
    }
}