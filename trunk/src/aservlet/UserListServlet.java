package aservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.UserForTransfer;
import edu.tjpu.share.util.XMLUtil;

public class UserListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public UserListServlet() {
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
		int gid = Integer.parseInt(msg[0].substring(msg[0].indexOf("=")+1, msg[0].length()));
		int mid = Integer.parseInt(msg[1].substring(msg[1].indexOf("=")+1, msg[1].length()));
		int cid = Integer.parseInt(msg[2].substring(msg[2].indexOf("=")+1, msg[2].length()));
		
		UserDao userDao = new UserDao();
		List<UserForTransfer> userList = userDao.getUserListByClasses(gid, mid, cid);

	
		
		if (userList != null) {
			PrintWriter pw = response.getWriter();
			XMLUtil.generateUserList(pw, userList);
		}else {
			System.out.println("userDao.getUserListByClasses()失败！");
		}
	}

}
