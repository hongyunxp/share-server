package edu.tjpu.share.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;

public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		if ("login".equals(type))
			login(request, response);
		if ("logout".equals(type))
			logout(request, response);
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = new User();
		user.setUname(request.getParameter("loginName").trim());
		user.setUpasswd(request.getParameter("password").trim());
		
		UserDao userDao = new UserDao();
		user = userDao.userLogin(user);
		HttpSession session = request.getSession();
		if(user == null) {
			request.setAttribute("error", "用户名或密码错误！");
			request.getRequestDispatcher("loginUI.jsp").forward(request, response);
			return;
		}
		session.setAttribute("user", user);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();

			session.invalidate();
			response.sendRedirect("logout.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
