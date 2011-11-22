package aservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.MajorDao;
import edu.tjpu.share.po.Major;
import edu.tjpu.share.util.XMLUtil;

public class Register2Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public Register2Servlet() {
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

		List<Major> majorList = null;
		MajorDao majorDao = new MajorDao();
		majorList = majorDao.getMajorListByGradeID(gid);

		if (majorList != null) {
			PrintWriter pw = response.getWriter();
			XMLUtil.generateMajorList(pw, majorList);
		}else {
			System.out.println("MajorDao.getMajorListByGradeID()失败！");
		}
	}
}
