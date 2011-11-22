package aservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;
import edu.tjpu.share.util.Base64Util;
import edu.tjpu.share.util.XMLUtil;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


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
		String upasswd = msg[1].substring(msg[1].indexOf("=")+1, msg[1].length());
		String needavatarstr = msg[2].substring(msg[2].indexOf("=")+1, msg[2].length());
		boolean needavatar = true;
		if("1".equals(needavatarstr)){
			needavatar = true;
		}else if("0".equals(needavatarstr)){
			needavatar = false;
		}
		//String uname = request.getAttribute("uname").toString().trim();
		//String upasswd = request.getAttribute("upasswd").toString().trim();
		
		User user = new User();
		user.setUname(uname);
		user.setUpasswd(upasswd);
		
		UserDao userDao = new UserDao();
		User userBack = userDao.userLogin(user);
		
		
		
		if(userBack == null){
			System.out.println("登录失败！");
		}else {
			
			if(needavatar){
			java.io.File file = new java.io.File(userBack.getUavatar());
			userBack.setUavatar(Base64Util.readFileInBASE64(file));
			}
			PrintWriter pw = response.getWriter();
			XMLUtil.generateUser(pw,userBack);
		}
	}


}
