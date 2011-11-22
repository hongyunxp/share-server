package edu.tjpu.share.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.dao.GradeDao;
import edu.tjpu.share.po.File;
import edu.tjpu.share.po.Grade;
import edu.tjpu.share.po.User;
import edu.tjpu.share.util.PageModel;

public class FileServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("type");
		
		
		if ("listshow".equals(type))
			showFileList(request, response);
		
		if ("del".equals(type))
			delFile(request, response);
		if ("showMyFile".equals(type))
			showMyFile(request, response);
	}

	private void showMyFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		FileDao fileDao = new FileDao();
		List<edu.tjpu.share.po.File> files = fileDao.getUsersFilesByUserID(user.getUid());
		
		
		int currentPage = Integer.parseInt(request.getParameter("current"));
		PageModel<edu.tjpu.share.po.File> pageModel = new PageModel<edu.tjpu.share.po.File>(files, currentPage);
		request.setAttribute("pageModel", pageModel);
		

		String msg = request.getParameter("msg");
		
		request.getRequestDispatcher("filepages/myFilelist.jsp?msg=" + msg).forward(request, response);
	}

	private void delFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String strFid = request.getParameter("fid");
	    int fid = Integer.parseInt(strFid);
	    
	    FileDao fileDao = new FileDao();
	    
	    edu.tjpu.share.po.File file = fileDao.getFileByFileID(fid);
	    
	    java.io.File fio = new java.io.File(file.getFurl());
	    boolean temp = fio.delete();
	    
	    List<Integer> fidList = new ArrayList<Integer>();
	    fidList.add(fid);
	    
	    boolean flag = fileDao.deleteFileByIDList(fidList);
	    
	    int msg = flag ? 8 : 9;
		
		request.getRequestDispatcher("FileServlet?type=showMyFile&current=1&msg=" + msg).forward(request, response);
	}


	private void showFileList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		

		FileDao fileDao = new FileDao();
		List<File> files = fileDao.getUsersFileListByUserID(user.getUid());
		
		int currentPage = Integer.parseInt(request.getParameter("current"));
		PageModel<edu.tjpu.share.po.File> pageModel = new PageModel<edu.tjpu.share.po.File>(files, currentPage);
		request.setAttribute("pageModel", pageModel);

		request.getRequestDispatcher("filepages/list.jsp").forward(request, response);

	}

}
