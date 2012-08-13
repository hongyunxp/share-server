package edu.tjpu.share.biz.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import edu.tjpu.share.biz.IUploadFile;
import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.FileForUpload;
import edu.tjpu.share.util.FileUploadServerUtil;
import edu.tjpu.share.util.IpTimeStamp;
import edu.tjpu.share.util.PortUtil;
import edu.tjpu.share.util.XMPPMsgUtil;

public class UploadFileImpl implements IUploadFile {

	@Override
	public int doUploadFile(FileForUpload file, boolean withoutFile) {
		FileDao fileDao = new FileDao();
		if(withoutFile){
			StringBuilder sb = new StringBuilder();
			Random random = new Random(); // 要产生随机数
			for (int i = 0; i < 10; i++) { // 产生三位随机数
				sb.append(random.nextInt(10)); // 每位随机数都不超过10
			}
			file.setFname("没有可下载的文件.msg");
			file.setFurl(File.separator + "ShareServer"+File.separator+sb.toString());
			
			boolean status = fileDao.addFileByAndroid(file);
			//xmpp
			if(status){
			List<Integer> idToList = file.getUidto();
			Iterator<Integer> iterator = idToList.iterator();
			UserDao userDao = new UserDao();
			while (iterator.hasNext()) {
				int uid = iterator.next();
				String xmppname = userDao.getXMPPName(uid);
				if (xmppname != null) {
					XMPPMsgUtil.sendMsg2SingleUserWithoutFile(xmppname, file, userDao);
				}
			}
			}
			//xmpp
			return status?0:-1;
		}else{
		URL base = this.getClass().getResource("");
		String filePath = null;
		try {
			filePath = new File(base.getFile(), "../../../../../../../upload/")
					.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 文件存储、路径
		String furl = filePath + File.separator
				+ IpTimeStamp.getIpTimeRand(file.getFname());
		file.setFurl(furl);
		int port = PortUtil.getPort();
		FileUploadServerUtil.getInstance().doService(port, furl,file);
		// 文件存储、路径
		
		boolean status = fileDao.addFileByAndroid(file);

		return status?port:-1;
		}
	}}
