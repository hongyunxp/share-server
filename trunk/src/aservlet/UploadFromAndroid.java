package aservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.po.FileForUpload;
import edu.tjpu.share.util.XMLUtil;

public class UploadFromAndroid extends HttpServlet {

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
		List<FileForUpload> fileList = new ArrayList<FileForUpload> ();
		int size = Integer.parseInt(msg[0].substring(msg[0].indexOf("=")+1, msg[0].length()));
		int j = 0;
		for (int i = 0; i < size; i++) {
			FileForUpload file = new FileForUpload();
			file.setUid(Integer.parseInt(msg[j+1].substring(msg[j+1].indexOf("=")+1, msg[j+1].length())));
			file.setUidto(Integer.parseInt(msg[j+2].substring(msg[j+2].indexOf("=")+1, msg[j+2].length())));
			file.setMsg(msg[j+3].substring(msg[j+3].indexOf("=")+1, msg[j+3].length()));
			file.setFname(msg[j+4].substring(msg[j+4].indexOf("=")+1, msg[j+4].length()));
			file.setBase64bytes(msg[j+5].substring(msg[j+5].indexOf("=")+1, msg[j+5].length()));
			fileList.add(file);
			j = j+5;
		}
		
		FileDao fileDao = new FileDao();
		
		String tmp = this.getServletContext().getRealPath("/upload");
		
		boolean status = fileDao.addFileByAndroid(fileList, tmp);
		
		PrintWriter pw = response.getWriter();
		XMLUtil.generateStatus(pw, status);
		
	}

}
