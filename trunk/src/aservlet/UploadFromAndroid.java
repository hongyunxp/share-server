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
		for (int i = 0; i < size; i++) {
			FileForUpload file = new FileForUpload();
			file.setUid(Integer.parseInt(msg[i+1].substring(msg[i+1].indexOf("=")+1, msg[i+1].length())));
			file.setUidto(Integer.parseInt(msg[i+2].substring(msg[i+2].indexOf("=")+1, msg[i+2].length())));
			file.setMsg(msg[i+3].substring(msg[i+3].indexOf("=")+1, msg[i+3].length()));
			file.setFname(msg[i+4].substring(msg[i+4].indexOf("=")+1, msg[i+4].length()));
			file.setBase64bytes(msg[i+5].substring(msg[i+5].indexOf("=")+1, msg[i+5].length()));
			fileList.add(file);
		}
		
		FileDao fileDao = new FileDao();
		
		String tmp = this.getServletContext().getRealPath("/upload");
		
		boolean status = fileDao.addFileByAndroid(fileList, tmp);
		
		PrintWriter pw = response.getWriter();
		XMLUtil.generateStatus(pw, status);
		
	}

}
