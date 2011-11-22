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
import edu.tjpu.share.util.XMLUtil;

public class SuccessServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SuccessServlet() {
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
		List<Integer> fidlist = new ArrayList<Integer> ();
		for (int i = 0; i < msg.length; i++) {
			fidlist.add(Integer.parseInt(msg[i].substring(msg[i].indexOf("=")+1, msg[i].length()))); 
		}
		
//		List<File> inputSuccessFileList = new ArrayList<File>();
		
//		Iterator<Integer> fidIterator = fidlist.iterator();
//		while(fidIterator.hasNext()){
//			int i = fidIterator.next();
//			File file = new File();
//			file.setFid(i);
//			inputSuccessFileList.add(file);
//		}
		FileDao fileDao = new FileDao();
		
		//boolean status = fileDao.getFileStatusUpdate(inputSuccessFileList);
		
		
		boolean status = fileDao.deleteFileByIDList(fidlist);
		
		PrintWriter pw = response.getWriter();
		XMLUtil.generateStatus(pw, status);
	}

}
