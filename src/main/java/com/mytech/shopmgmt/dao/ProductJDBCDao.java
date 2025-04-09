package com.mytech.shopmgmt.dao;

import com.mytech.shopmgmt.db.dbConnecter;
import com.mytech.shopmgmt.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//data access object
public class ProductJDBCDao {

    private final String SELECT_ALL_PRODUCT = "SELECT * FROM t3shop.product";
    private final String SELECT_PRODUCT_BY_CODE = "SELECT * FROM t3shop.product WHERE code = ?";
    private final String UPDATE_PRODUCT_BY_PRODCUT_CODE = "UPDATE t3shop.product SET name = ?, price = ?, imagePath = ? WHERE code = ?";
    private final String DELETE_PRODUCT_BY_CODE = "DELETE FROM t3shop.product WHERE code = ?";
    private final String INSERT_PRODUCT = "INSERT INTO t3shop.product (code, name, price, imagePath) VALUES (?, ?, ?, ?)";

    public ArrayList<Product> getProducts() {
        ArrayList<Product> listProduct = new ArrayList<Product>();
        try {
            Connection connection = dbConnecter.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String imagePath = resultSet.getString("imagePath");

                Product product = new Product(code, name, price, imagePath);
                listProduct.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listProduct;
    }

    public Product getProductByCode(String code) {
        Connection connection = dbConnecter.getConnection();
        Product product = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_CODE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String imagePath = resultSet.getString("imagePath");
                product = new Product(code, name, price, imagePath);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public boolean updateProductByCode(Product product) {
        try {
            Connection connection = dbConnecter.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_BY_PRODCUT_CODE);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getImagePath());
            preparedStatement.setString(4, product.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean deleteProduct(String code) {
        try {
            Connection connection = dbConnecter.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_CODE);
            preparedStatement.setString(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean addProduct(Product product) {
        try {
            Connection connection = dbConnecter.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getImagePath());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
