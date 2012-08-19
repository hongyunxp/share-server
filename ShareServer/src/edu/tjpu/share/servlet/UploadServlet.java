package edu.tjpu.share.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;
import edu.tjpu.share.util.XMPPMsgUtil;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileload = new ServletFileUpload(factory);

			// 设置最大文件尺寸，这里是100MB
			fileload.setSizeMax(100 * 1024 * 1024);

			@SuppressWarnings("unchecked")
			List<FileItem> files = fileload.parseRequest(request);
			
			if(files == null || files.size() < 2) {
				//TODO 传返回参数 上传失败。。没有选择用户
				request.getRequestDispatcher("FileServlet?type=showMyFile&msg=11&current=1").forward(request, response);
				return;
			}

			// 循环迭代分析每一个表单项
			Iterator<FileItem> iterator = files.iterator();
			
			List<edu.tjpu.share.po.File> fileBeans = new ArrayList<edu.tjpu.share.po.File>();
			List<Integer> userIDs = new ArrayList<Integer>();
			String filename = "";
			String filepath = "";

			User user = (User) request.getSession().getAttribute("user");

			FileDao fileDao = new FileDao();
			
			String fileMsg = "";

			while (iterator.hasNext()) {

				FileItem item = iterator.next();

				// 如果当前项是普通表单项。
				if (item.isFormField()) {
					String name = item.getFieldName();
					if (name.equals("uid")) {
						String strU_uid = item.getString("utf-8");
						int u_uid = Integer.parseInt(strU_uid);
						userIDs.add(u_uid);

					}
					
					if (name.equals("fileMsg")) {
						fileMsg = item.getString("utf-8").trim();
					}

					//System.out.println("普通控件 ， 名为: " + item.getFieldName() + "值为: ");

				} else {
					// 获得获得文件名，此文件名包括路径
					filename = item.getName();
					if (filename != null && !filename.isEmpty()) {
						
						String temp = filename.substring(filename.lastIndexOf("."), filename.length());
						long time = new Date().getTime();

						File filetoserver = new File(this.getServletContext().getRealPath("/upload"), time + temp);
						item.write(filetoserver);
						if (filetoserver.getName() != null) {
							filepath = this.getServletContext().getRealPath("/upload")+ "/" + time + temp;
						}
					}
				}

			}
			
			if(filepath == "") {
				//TODO 传返回参数 上传失败。。没有选择文件
				response.sendRedirect("FileServlet?type=showMyFile&msg=12&current=1");
				return;
			}
			UserDao userDao = new UserDao();
			filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
			for(int i : userIDs) {
				edu.tjpu.share.po.File f = new edu.tjpu.share.po.File();
				f.setFname(filename);
				f.setFurl(filepath);
				f.setUidfrom(user.getUid());
				f.setUidto(i);
				f.setUploaddate(new Date());
				f.setIsread(0);
				fileBeans.add(f);
				//xmpp
				int uid = i;
				String xmppname = userDao.getXMPPName(uid);
				if (xmppname != null) {
					String tmpMsg;
					if (fileMsg.equals(""))
						tmpMsg = userDao.getUserByID(user.getUid()).getUname()
								+ "给您分享了" + filename;
					else
						tmpMsg = fileMsg;
					XMPPMsgUtil.sendMsg2SingleUserWithoutFile(xmppname, tmpMsg,user.getUid(), userDao);
				}
				//xmpp
			}
			
			boolean flag = fileDao.addFileByServer(fileBeans, fileMsg);
			
			
			
			
			
			//TODO 根据返回值 返回页面参数
			int msg = flag ? 6 : 7;
			request.getRequestDispatcher("FileServlet?type=showMyFile&current=1&msg=" + msg).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
