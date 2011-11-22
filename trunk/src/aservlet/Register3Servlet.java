package aservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.ClassDao;
import edu.tjpu.share.po.Classes;
import edu.tjpu.share.util.XMLUtil;

public class Register3Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public Register3Servlet() {
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
		int mid = Integer.parseInt(msg[0].substring(msg[0].indexOf("=")+1, msg[0].length()));

		List<Classes> classList = null;
		ClassDao classDao = new ClassDao();
		classList = classDao.getClassListByMajorID(mid);

		if (classList != null) {
			PrintWriter pw = response.getWriter();
			XMLUtil.generateClassList(pw, classList);
		}else {
			System.out.println("ClassDao.getClassListByMajorID()失败！");
		}
	}
}
