package com.mytech.shopmgmt;

import com.mytech.shopmgmt.dao.ProductDao;
//import com.mytech.shopmgmt.dao.ProductJDBCDao;
import com.mytech.shopmgmt.helper.ServletHelper;
import com.mytech.shopmgmt.models.Product;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private ProductJDBCDao productJDBCDaos;
	private ProductDao productDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
//		productJDBCDaos = new ProductJDBCDao();
		productDao = new ProductDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
				if (product != null){
					request.setAttribute("product", product);
					ServletHelper.forward(request, response, "edit_product");
				}else{
					ServletHelper.forward(request, response, "error");
				}
				break;

			case "delete":
				String codeDelete = request.getParameter("code");
				productDao.deleteProduct(codeDelete);
				break;
			default:
				List<Product> listProducts = productDao.getProducts();
				request.setAttribute("listProducts", listProducts);
				ServletHelper.forward(request, response, "product");
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String priceString = request.getParameter("price");
		double price = Double.parseDouble(priceString);
		String image = "";
		Product product = new Product(code, name, price, image);
		if ("update".equals(action)){
			productDao.updateProduct(product);
		}else{
			productDao.addProduct(product);
		}
		ServletHelper.redirect(request, response, "products");
	}

}