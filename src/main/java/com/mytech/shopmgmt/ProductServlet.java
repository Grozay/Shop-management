package com.mytech.shopmgmt;

import com.mytech.shopmgmt.dao.ProductDao;
import com.mytech.shopmgmt.helper.ServletHelper;
import com.mytech.shopmgmt.models.Product;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/products")
@MultipartConfig // Thêm annotation này để hỗ trợ multipart
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao;
    private ServletContext servletContext;

    public ProductServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = getServletContext();
        productDao = new ProductDao(servletContext); // Truyền ServletContext vào ProductDao
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                ServletHelper.forward(request, response, "add_product");
                break;
            case "update":
                String code = request.getParameter("code");
                Product product = productDao.getProductByCode(code);
                if (product != null) {
                    request.setAttribute("product", product);
                    ServletHelper.forward(request, response, "edit_product");
                } else {
                    ServletHelper.forward(request, response, "error");
                }
                break;
            case "delete":
                String codeDelete = request.getParameter("code");
                productDao.deleteProduct(codeDelete);
                response.sendRedirect("products"); // Chuyển hướng sau khi xóa
                break;
            default:
                List<Product> listProducts = productDao.getProducts();
                request.setAttribute("listProducts", listProducts);
                ServletHelper.forward(request, response, "product");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action"); // Sử dụng getParameter để lấy action từ URL
        System.out.println("Action nhận được: " + action);

        String code = getPartAsString(request.getPart("code"));
        String name = getPartAsString(request.getPart("name"));
        String priceStr = getPartAsString(request.getPart("price"));
        Part filePart = request.getPart("image");

        Product product = new Product();
        product.setCode(code);
        product.setName(name);

        // Kiểm tra và xử lý giá trị price
        if (priceStr != null && !priceStr.trim().isEmpty()) {
            try {
                double price = Double.parseDouble(priceStr);
                product.setPrice(price);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Giá sản phẩm không hợp lệ!");
                ServletHelper.forward(request, response, "error");
                return;
            }
        } else {
            request.setAttribute("error", "Vui lòng nhập giá sản phẩm!");
            response.sendRedirect("error.jsp");
            return;
        }

        // Thực hiện hành động thêm hoặc cập nhật
        if ("update".equals(action)) {
            // Kiểm tra xem có file ảnh mới được tải lên không
            if (filePart != null && filePart.getSize() > 0) {
                productDao.updateProduct(product, filePart); // Gọi phương thức update có xử lý ảnh
            } else {
                productDao.updateProduct(product); // Gọi phương thức update chỉ cập nhật thông tin sản phẩm
            }
        } else {
            productDao.addProduct(product, filePart);
        }

        ServletHelper.redirect(request, response, "products");
    }

    // Hàm tiện ích để chuyển Part thành String
    private String getPartAsString(Part part) throws IOException {
        if (part == null) {
            return null;
        }
        try (InputStream inputStream = part.getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8).trim();
        }
    }
}