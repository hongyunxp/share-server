package edu.tjpu.share.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;

public class UpdateAvatarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileload = new ServletFileUpload(factory);

			// 设置最大文件尺寸，这里是4MB
			fileload.setSizeMax(4 * 1024 * 1024);

			@SuppressWarnings("unchecked")
			List<FileItem> files = fileload.parseRequest(request);
			
			if(files == null || files.size() < 1) {
				//TODO 传返回参数 上传失败。。没有选择用户
				request.getRequestDispatcher("userpages/modifyAvatar.jsp").forward(request, response);
				return;
			}

			
			// 循环迭代分析每一个表单项
			Iterator<FileItem> iterator = files.iterator();

			User user = (User) request.getSession().getAttribute("user");
			
			String avatar = "";

			while (iterator.hasNext()) {

				FileItem item = iterator.next();

				// 如果当前项是普通表单项。
				if (item.isFormField()) {
					// response.sendRedirect("register_error.jsp");
				} else {
					// 获得获得文件名，此文件名包括路径
					String filename = item.getName();
					String temp = filename.substring(filename.lastIndexOf("."), filename.length());

					if (filename != null && !filename.isEmpty()) {
					
						long time = new Date().getTime();
						File filetoserver = new File(this.getServletContext().getRealPath("/avatar"), time + temp);
						item.write(filetoserver);
						avatar = this.getServletContext().getRealPath("/avatar")+ "/" + time + temp;
					}
				}

			}
			User u = new User();
			u.setUavatar("");
			
			user.setUavatar(avatar);
			UserDao userDao = new UserDao();
			u = userDao.userInfoUpdate(user);
			
			int msg = u.getUavatar().equals(user.getUavatar()) ? 4 : 5;
			
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("userpages/modifyAvatar.jsp?msg=" + msg).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
