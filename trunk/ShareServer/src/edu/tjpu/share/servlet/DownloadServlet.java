package edu.tjpu.share.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.dao.FileDao;

public class DownloadServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  //服务器相对路径
		response.setContentType("text/html;charset=UTF-8");
	    String strFid = request.getParameter("fid");
	    int fid = Integer.parseInt(strFid);
	    
	    FileDao fileDao = new FileDao();
	    edu.tjpu.share.po.File file = fileDao.getFileByFileID(fid);
	    file.setIsread(1);
	    
	    List<edu.tjpu.share.po.File> files = new ArrayList<edu.tjpu.share.po.File>();
	    files.add(file);
	    
	    // 服务器绝对路径
	   

	    // 检查文件是否存在
	    File obj = new File(file.getFurl());
	    if (!obj.exists()) {
	      response.getWriter().print("指定文件不存在！");
	      return;
	    }

	    // 读取文件名：用于设置客户端保存时指定默认文件名
	    String decodedFirstName = new String(file.getFname().getBytes(),"ISO-8859-1");

	    // 写流文件到前端浏览器
	    ServletOutputStream out = response.getOutputStream();
	    response.setHeader("Content-disposition", "attachment;filename=" + decodedFirstName);
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	    try {
	      bis = new BufferedInputStream(new FileInputStream(file.getFurl()));
	      bos = new BufferedOutputStream(out);
	      byte[] buff = new byte[2048];
	      int bytesRead;
	      while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        bos.write(buff, 0, bytesRead);
	      }
	      
	      fileDao.getFileStatusUpdate(files);
	    } catch (IOException e) {
	      throw e;
	    } finally {
	      if (bis != null)
	        bis.close();
	      if (bos != null)
	        bos.close();
	    }
	}

}
