package com.mytech.shopmgmt.models;

import jakarta.persistence.*;

@Entity
@Table(name = "CartLine")
public class CartLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopCartId", nullable = false)
    private ShopCart shopCart;

    @ManyToOne
    @JoinColumn(name = "productCode", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private Double price;


    // Constructors
    public CartLine() {
        super();
    }

    public CartLine(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}