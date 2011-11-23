package aservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.dao.NotifyDao;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.FileForDownload;
import edu.tjpu.share.util.XMLUtil;

public class MessageListServlet extends HttpServlet {

	

	private static final long serialVersionUID = 1L;

	public MessageListServlet() {
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
		int uid = Integer.parseInt(msg[0].substring(msg[0].indexOf("=")+1, msg[0].length()));

		List<edu.tjpu.share.po.File> fileList;
		List<FileForDownload> filefordownloadList = new ArrayList<FileForDownload>();
		
		FileDao fileDao = new FileDao();
		NotifyDao notifyDao = new NotifyDao();
		UserDao userDao = new UserDao();
		fileList = fileDao.getUsersFileListByUserID(uid);
		
		Iterator<edu.tjpu.share.po.File> fileIterator = fileList.iterator();
		while(fileIterator.hasNext()){
			FileForDownload download = new FileForDownload();
			edu.tjpu.share.po.File file = fileIterator.next();
			
			download.setCdate(file.getUploaddate());
			download.setFid(file.getFid());
			download.setFname(file.getFname());
			//TODO文件路径
			StringBuffer  realUrl = request.getRequestURL();
			StringBuilder sb = new StringBuilder();
			sb.append(realUrl.substring(0, realUrl.lastIndexOf("ForShare/")));
			String fuell = file.getFurl();
			fuell = fuell.replace("\\ForShare\\upload", "/ForShare/upload");
			int i = fuell.lastIndexOf("ForShare");
			int j =  fuell.length();
			sb.append(fuell.substring(i,j));
			//realUrl.append(file.getFurl());
			download.setFurl(sb.toString());
			download.setMsg(notifyDao.getMsgByFID(file.getFid()));
			download.setUnamefrom(userDao.getUserByID(file.getUidfrom()).getUname());
			
			filefordownloadList.add(download);
		}
		
		
			PrintWriter pw = response.getWriter();
			XMLUtil.generateFileList(pw, filefordownloadList);

	}

}
