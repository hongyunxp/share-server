package aservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;
import edu.tjpu.share.util.XMLUtil;

public class Register4Servlet extends HttpServlet {

	

	private static final long serialVersionUID = 1L;


	public Register4Servlet() {
		super();
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		BufferedReader br = request.getReader();
		String message = br.readLine();
		String[] msg = message.split("&");
		String uname = msg[0].substring(msg[0].indexOf("=")+1, msg[0].length());
		String passwd = msg[1].substring(msg[1].indexOf("=")+1, msg[1].length());
		int gid = Integer.parseInt(msg[2].substring(msg[2].indexOf("=")+1, msg[2].length()));
		int mid = Integer.parseInt(msg[3].substring(msg[3].indexOf("=")+1, msg[3].length()));
		int cid = Integer.parseInt(msg[4].substring(msg[4].indexOf("=")+1, msg[4].length()));
		
		UserDao userDao = new UserDao();
		int gmc = userDao.getUserGradeMajorClassRelation(gid, mid, cid);
		
		User user = new User();
		user.setGmcid(gmc);
		user.setUname(uname);
		user.setUpasswd(passwd);
		Date uregdate = new Date();
		user.setUregdate(uregdate);
		//java.io.File file = new java.io.File("/AndroidShareServer/avatar/default.jpg")
		user.setUavatar("C:\\Users\\Crystal\\Workspaces\\MyEclipse 10\\AndroidShareServer\\uavatar\\1321002257630.jpg");
		
		if(!userDao.checkUserName(uname)){
			User userb = new User();
			userb.setUid(0);
			PrintWriter pw = response.getWriter();
			XMLUtil.generateUser(pw, userb);
		}
		
		User userBack = userDao.register(user);
		
		if (userBack != null) {
			PrintWriter pw = response.getWriter();
			XMLUtil.generateUser(pw, userBack);
		}else {
			System.out.println("UserDao.register()失败！");
		}
	}

}
