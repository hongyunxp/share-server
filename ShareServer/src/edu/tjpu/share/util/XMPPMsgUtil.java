package edu.tjpu.share.util;

import java.util.Properties;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.FileForUpload;

public class XMPPMsgUtil {

	public static void sendMsg2SingleUser(String xmppname, FileForUpload file,
			UserDao userDao) {
		Properties formProperties = new Properties();
		formProperties.put("action", "send");
		formProperties.put("broadcast", "N");
		formProperties.put("username", xmppname);
		formProperties.put("title", userDao.getUserNameById(file.getUid())
				+ "给您分享了文件：" + file.getFname());
		formProperties.put("message", file.getMsg());
		formProperties.put("uri", "");
		try {
			NetUtil.requestPostForm(
					"http://localhost:7070/notification.do",
					formProperties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMsg2SingleUserWithoutFile(String xmppname, FileForUpload file,
			UserDao userDao) {
		Properties formProperties = new Properties();
		formProperties.put("action", "send");
		formProperties.put("broadcast", "N");
		formProperties.put("username", xmppname);
		formProperties.put("title", userDao.getUserNameById(file.getUid())
				+ "给您发送了消息：");
		formProperties.put("message", file.getMsg());
		formProperties.put("uri", "");
		try {
			NetUtil.requestPostForm(
					"http://localhost:7070/notification.do",
					formProperties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
