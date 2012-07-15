package edu.tjpu.share.biz.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.tjpu.share.biz.IUploadFile;
import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.po.FileForUpload;
import edu.tjpu.share.util.FileUploadServerUtil;
import edu.tjpu.share.util.IpTimeStamp;
import edu.tjpu.share.util.PortUtil;

public class UploadFileImpl implements IUploadFile {

	@Override
	public int doUploadFile(FileForUpload file) {
		FileDao fileDao = new FileDao();
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
		FileUploadServerUtil.getInstance().doService(port, furl);
		// 文件存储、路径
		
		boolean status = fileDao.addFileByAndroid(file);

		return status?port:0;
	}

}
