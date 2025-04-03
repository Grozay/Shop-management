package com.mytech.shopmgmt;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.Principal;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session (không tạo mới nếu chưa có)
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        String username = "";
        String loginDate = "";

//         Kiểm tra xem session có tồn tại không
        if (session != null) {
            username = (String) session.getAttribute("username");
            loginDate = (String) session.getAttribute("loginDate");
        }

//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("username")) {
//                    username = cookie.getValue();
//                }
//                if (cookie.getName().equals("loginDate")) {
//                    loginDate = cookie.getValue();
//                }
//            }
//        }

        request.setAttribute("username", username != null ? username : "Guest");
        request.setAttribute("loginDate", loginDate != null ? loginDate : "Don't login yet");
        
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
        requestDispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
