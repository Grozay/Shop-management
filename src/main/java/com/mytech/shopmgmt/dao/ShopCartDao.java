package com.mytech.shopmgmt.dao;

import com.mytech.shopmgmt.models.Customer;
import com.mytech.shopmgmt.models.Product;
import jakarta.persistence.EntityManager;
import com.mytech.shopmgmt.db.dbConnecter;

public class ShopCartDao {
    public boolean addShopCart(Customer customer, Product product, int quantity) {
        EntityManager entityManager = dbConnecter.getEntityManager();
        //1. lấy giỏ hàng từ customer
        //2. tạo cartline tu product với quantity
        //3. them carrt line vaof shopcart
        //luuw
        return true;
    }
}
