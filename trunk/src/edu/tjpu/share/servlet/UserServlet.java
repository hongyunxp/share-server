package edu.tjpu.share.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;
import edu.tjpu.share.util.PageModel;

public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("type");
		
		if ("list".equals(type))
			listUser(request, response);
		if ("modify".equals(type))
			modifyUser(request, response);
	}

	
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserDao userDao = new UserDao();
		List<User> users = userDao.getALLUsers();
		
		
		int currentPage = Integer.parseInt(request.getParameter("current"));
		PageModel<User> pageModel = new PageModel<User>(users, currentPage);
		request.setAttribute("pageModel", pageModel);
		
		request.getRequestDispatcher("userpages/list.jsp").forward(request, response);
	}

	private void modifyUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String oldpassword = request.getParameter("oldpassword").trim();
		String newpassword = request.getParameter("newpassword").trim();
		
		User user = (User)request.getSession().getAttribute("user");
		
		if(!user.getUpasswd().equals(oldpassword)) {
			request.getRequestDispatcher("userpages/modifyPwd.jsp?msg=3").forward(request, response);
			return;
		}
		
		UserDao userDao = new UserDao();
		boolean flag = userDao.userPwdUpdate(newpassword, user.getUid());
		user.setUpasswd(newpassword);
		request.getSession().setAttribute("user", user);
		
		int msg = flag ? 2 : 3;
		
		request.getRequestDispatcher("userpages/modifyPwd.jsp?msg=" + msg).forward(request, response);
	}

}
