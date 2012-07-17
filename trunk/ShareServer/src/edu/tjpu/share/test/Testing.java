package edu.tjpu.share.test;

import java.io.File;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.util.FileUploadServerUtil;
import edu.tjpu.share.util.PortUtil;

public class Testing {
	public static void main(String[] args) {

		StringBuilder sb = new StringBuilder("C:" + File.separator + "DEV"
				+ File.separator + "apache-tomcat-7.0.27" + File.separator
				+ "wtpwebapps" + File.separator + "ShareServer"
				+ File.separator + "upload20120705135932017.prop");
		String path = sb.subSequence(
				sb.indexOf(File.separator + "ShareServer"), sb.length())
				.toString();
		System.out.println(path);

		/*
		for (int i = 0; i <100; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					int port = PortUtil.getPort();
					//FileUploadServerUtil.getInstance().doService(port, "R:"+port+".txt");
					FileUploadClient client = new FileUploadClient("127.0.0.1",port);
					client.doUpload("R:1.txt");
				}
			});
			t.start();
			*/
		UserDao userDao = new UserDao();
		String xmpp = userDao.getXMPPName(2);
		System.out.println(xmpp);
		}
		
	}

