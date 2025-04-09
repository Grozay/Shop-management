package com.mytech.shopmgmt.dao;

import com.mytech.shopmgmt.db.dbConnecter;
import com.mytech.shopmgmt.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // Kích thước tệp tối đa 10MB
public class ProductDao {
    private static final String UPLOAD_DIR_NAME = "uploads"; // Tên thư mục lưu trữ tệp tải lên
    private String uploadPath;
    private ServletContext servletContext;

    // Hàm khởi tạo nhận ServletContext
    public ProductDao(ServletContext servletContext) {
        this.servletContext = servletContext;
        // Lấy đường dẫn thực tế của web application
        String webAppPath = servletContext.getRealPath("");
        uploadPath = webAppPath + File.separator + UPLOAD_DIR_NAME;
        System.out.println("Đường dẫn thư mục uploads: " + uploadPath);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            System.out.println("Thư mục chưa tồn tại, đang tạo...");
            uploadDir.mkdirs();
            System.out.println("Đã tạo thư mục: " + uploadDir.exists());
        } else {
            System.out.println("Thư mục đã tồn tại.");
        }
    }

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getProducts() {
        EntityManager entityManager = dbConnecter.getEntityManager();
        Query query = entityManager.createNamedQuery("Product.findAll", Product.class);
        return query.getResultList();
    }

    // Lấy sản phẩm theo mã
    public Product getProductByCode(String code) {
        EntityManager entityManager = dbConnecter.getEntityManager();
        Query query = entityManager.createNamedQuery("Product.findByCode", Product.class);
        query.setParameter("code", code);
        try {
            return (Product) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // Lấy sản phẩm theo tên
    public Product getProductByName(String name) {
        EntityManager entityManager = dbConnecter.getEntityManager();
        Query query = entityManager.createNamedQuery("Product.findByName", Product.class);
        query.setParameter("code", "%" + name + "%");
        try {
            return (Product) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // Thêm sản phẩm mới kèm khả năng tải lên ảnh
    public boolean addProduct(Product product, Part filePart) throws IOException {
        EntityManager entityManager = dbConnecter.getEntityManager();

        try {
            // Xử lý tải lên ảnh nếu có
            if (filePart != null && filePart.getSize() > 0) {
                String imagePath = uploadImage(filePart);
                product.setImagePath(imagePath);
            }

            // Lưu sản phẩm vào cơ sở dữ liệu
            var trans = entityManager.getTransaction();
            trans.begin();
            entityManager.persist(product);
            trans.commit();
            return true;

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Hoàn tác nếu có lỗi
            }
            throw new IOException("Lỗi khi thêm sản phẩm: " + e.getMessage());
        } finally {
            entityManager.close(); // Đóng kết nối
        }
    }

    // Hàm riêng để xử lý việc tải lên ảnh
    private String uploadImage(Part filePart) throws IOException {
        String fileName = extractFileName(filePart); // Lấy tên tệp gốc
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName; // Tạo tên tệp duy nhất
        String filePath = uploadPath + File.separator + uniqueFileName;

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(filePath)); // Sao chép tệp vào thư mục
        }

        // Trả về đường dẫn tương đối để lưu vào database
        return UPLOAD_DIR_NAME + "/" + uniqueFileName;
    }

    // Trích xuất tên tệp từ Part
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                String fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
                return fileName;
            }
        }
        return "unknown"; // Trả về "unknown" nếu không tìm thấy
    }

    // Cập nhật thông tin sản phẩm (không bao gồm ảnh)
    public boolean updateProduct(Product product) {
        EntityManager entityManager = dbConnecter.getEntityManager();
        var trans = entityManager.getTransaction();
        trans.begin();
        Product prodUpdated = entityManager.find(Product.class, product.getCode());
        if (prodUpdated != null) {
            prodUpdated.setName(product.getName());
            prodUpdated.setPrice(product.getPrice());
            entityManager.merge(prodUpdated);
            trans.commit();
            return true;
        }
        return false;
    }

    // Cập nhật thông tin sản phẩm và ảnh
    public boolean updateProduct(Product product, Part filePart) throws IOException {
        EntityManager entityManager = dbConnecter.getEntityManager();
        var trans = entityManager.getTransaction();
        trans.begin();
        Product prodUpdated = entityManager.find(Product.class, product.getCode());
        if (prodUpdated != null) {
            prodUpdated.setName(product.getName());
            prodUpdated.setPrice(product.getPrice());

            // Xử lý tải lên ảnh mới
            if (filePart != null && filePart.getSize() > 0) {
                // Xóa ảnh cũ nếu có (tùy chọn)
                if (prodUpdated.getImagePath() != null && !prodUpdated.getImagePath().isEmpty()) {
                    String oldImagePath = servletContext.getRealPath("/") + File.separator + prodUpdated.getImagePath();
                    File oldFile = new File(oldImagePath);
                    if (oldFile.exists()) {
                        oldFile.delete();
                        System.out.println("Đã xóa ảnh cũ: " + oldImagePath);
                    }
                }
                String newImagePath = uploadImage(filePart);
                prodUpdated.setImagePath(newImagePath);
            }

            entityManager.merge(prodUpdated);
            trans.commit();
            return true;
        }
        return false;
    }

    // Xóa sản phẩm theo mã
    public boolean deleteProduct(String code) {
        EntityManager entityManager = dbConnecter.getEntityManager();
        var trans = entityManager.getTransaction();
        trans.begin();
        Product product = entityManager.find(Product.class, code);
        if (product != null) {
            // Xóa ảnh cũ nếu có
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                String oldImagePath = servletContext.getRealPath("/") + File.separator + product.getImagePath();
                File oldFile = new File(oldImagePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                    System.out.println("Đã xóa ảnh cũ: " + oldImagePath);
                }
            }
            entityManager.remove(product);
            trans.commit();
            return true;
        }
        return false;
    }
}