package com.mytech.shopmgmt;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		PrintWriter outPrintWriter = response.getWriter();
		outPrintWriter.append("You login with: " + username + " :: " + password);
		outPrintWriter.append("You login with: " + username + " :: " + password);

		if ("admin".equals(username) && "123".equals(password)) {
			HttpSession session = request.getSession(true);
//			Cookie ckUsername = new Cookie("username", username);
//			Cookie ckLoginDate = new Cookie("loginDate", String.valueOf(System.currentTimeMillis()));
//			response.addCookie(ckUsername);
//			response.addCookie(ckLoginDate);

			session.setAttribute("username", username);
			session.setAttribute("loginDate", String.valueOf(java.time.LocalDate.now()));

			// Đặt thời gian sống của session (tùy chọn, ví dụ: 24 giờ)
			session.setMaxInactiveInterval(24 * 60 * 60); // 24 giờ tính bằng giây

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
			requestDispatcher.forward(request, response);
		} else {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
			requestDispatcher.forward(request, response);
		}
	}

}
