package aservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.GradeDao;
import edu.tjpu.share.po.Grade;
import edu.tjpu.share.util.XMLUtil;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public RegisterServlet() {
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

		List<Grade> academyList = null;
		GradeDao gradeDao = new GradeDao();
		academyList = gradeDao.getAllGradesList();

		if (academyList != null) {
			PrintWriter pw = response.getWriter();
			XMLUtil.generateAcademyList(pw, academyList);
		}else {
			System.out.println("GradeDao.getAllGradesList()失败！");
		}
	}

}
