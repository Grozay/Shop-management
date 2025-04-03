package com.mytech.shopmgmt.dao;

import com.mytech.shopmgmt.db.dbConnecter;
import com.mytech.shopmgmt.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class ProductDao {

    public List<Product> getProducts(){
        EntityManager entityManager = dbConnecter.getEntityManager();
        Query query = entityManager.createNamedQuery("Product.findAll", Product.class);
        return query.getResultList();
    }

    public Product getProductByCode(String code){
        EntityManager entityManager = dbConnecter.getEntityManager();
        Query query = entityManager.createNamedQuery("Product.findByCode", Product.class);
        query.setParameter("code", code);
        return (Product) query.getSingleResult();
    }

    public Product getProductByName(String name){
        EntityManager entityManager = dbConnecter.getEntityManager();
        Query query = entityManager.createNamedQuery("Product.findByName", Product.class);
        query.setParameter("code", "%" + name + "%");
        return (Product) query.getSingleResult();
    }

    public boolean addProduct(Product product){
        EntityManager entityManager = dbConnecter.getEntityManager();
        var trans = entityManager.getTransaction();
        trans.begin();
        entityManager.persist(product);
        trans.commit();
        return true;
    }

    public boolean updateProduct(Product product){
        EntityManager entityManager = dbConnecter.getEntityManager();
        var trans = entityManager.getTransaction();
        trans.begin();
        Product prodUpdated = entityManager.find(Product.class, product.getCode());
        prodUpdated.setName(product.getName());
        prodUpdated.setPrice(product.getPrice());
        prodUpdated.setImagePath(product.getImagePath());
        entityManager.merge(prodUpdated);
        trans.commit();
        return true;
    }

//    public boolean updateProduct(Product product) {
//        if (product == null || product.getCode() == null) {
//            return false; // Hoặc throw exception tùy yêu cầu
//        }
//
//        EntityManager entityManager = null;
//        try {
//            entityManager = dbConnecter.getEntityManager();
//            EntityTransaction trans = entityManager.getTransaction();
//
//            trans.begin();
//            Product prodUpdated = entityManager.find(Product.class, product.getCode());
//
//            if (prodUpdated == null) {
//                trans.rollback();
//                return false; // Không tìm thấy sản phẩm để cập nhật
//            }
//
//            // Cập nhật thông tin
//            prodUpdated.setName(product.getName());
//            prodUpdated.setPrice(product.getPrice());
//            prodUpdated.setImagePath(product.getImagePath());
//
//            // Không cần merge() vì prodUpdated đã là managed entity
//            trans.commit();
//            return true;
//        } catch (Exception e) {
//            if (entityManager != null && entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
//            e.printStackTrace(); // Hoặc dùng logger
//            return false;
//        } finally {
//            if (entityManager != null && entityManager.isOpen()) {
//                entityManager.close(); // Đóng EntityManager nếu không được quản lý bởi container
//            }
//        }
//    }

    public boolean deleteProduct(String code){
        EntityManager entityManager = dbConnecter.getEntityManager();
        Product product = entityManager.find(Product.class, code);
        entityManager.remove(product);
        return true;
    }

}
