package com.mytech.shopmgmt.models;


import jakarta.persistence.*;
import org.eclipse.persistence.annotations.RangePartition;

@Entity(name = "Product")
@Table(name = "Products")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
@NamedQuery(name = "Product.findByCode", query = "SELECT p FROM Product p WHERE p.code = :code")
@NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name LIKE :name")
public class Product {
    @Id
    private String code;
    @Column(name = "name", nullable = false, length = 256, unique = false)
    private String name;
    private double price;
    private String imagePath;

    public Product() {
        super();
    }

    public Product(String code, String name, double price, String imagePath) {
        super();
        this.code = code;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
