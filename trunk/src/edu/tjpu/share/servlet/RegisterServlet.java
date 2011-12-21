package edu.tjpu.share.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.tjpu.share.dao.GradeDao;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.Grade;
import edu.tjpu.share.po.User;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("type");
		//if("show".equals(type)) show(request, response);
		if("add".equals(type)) add(request, response);
	}
	
//	public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		GradeDao gradeDao = new GradeDao();
//		List<Grade> list = gradeDao.getAllGradesList();
//		request.setAttribute("grades", list);
//		request.getRequestDispatcher("registerUI.jsp").forward(request, response);
//	}
	
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserDao userDao = new UserDao();
		
		String strGradeID = request.getParameter("grade");
		String strMajorID = request.getParameter("major");
		String strClassID = request.getParameter("class");
		
		String name = request.getParameter("loginName").trim();
		
		HttpSession session = request.getSession();
		String rand = (String)session.getAttribute("rand");
		String input = request.getParameter("checkunm").trim();
		
		if(!rand.equals(input)) {
			response.sendRedirect("registerUI.jsp?msg=13");
			return;
		}
		
		
		boolean flag = userDao.checkUserName(name);
		if(!flag){
			response.sendRedirect("registerUI.jsp?msg=10");
			return;
		}
		
		
		String password  = request.getParameter("password").trim();
		
		int gradeId = Integer.parseInt(strGradeID);
		int majorId = Integer.parseInt(strMajorID);
		int classId = Integer.parseInt(strClassID);
		
		int gmcId = userDao.getUserGradeMajorClassRelation(gradeId, majorId, classId);
		User u = null;
		if(gmcId > 0) {
			u = new User();
			u.setUname(name);
			u.setGmcid(gmcId);
			u.setUpasswd(password);
			u.setUavatar(this.getServletContext().getRealPath("/avatar")+"/default.jpg");
			u.setUregdate(new Date());
			u = userDao.register(u);
			session = request.getSession();
			session.setAttribute("user", u);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else {
			response.sendRedirect("registerUI.jsp?msg=1");
		}
		
	}

}
