package edu.tjpu.share.biz.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.tjpu.share.biz.IMessageList;
import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.dao.NotifyDao;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.FileForDownload;

public class MessageListImpl implements IMessageList {

	@Override
	public List<FileForDownload> getMessageList(int uid, int offset) {
		List<edu.tjpu.share.po.File> fileList;
		List<FileForDownload> filefordownloadList = new ArrayList<FileForDownload>();

		FileDao fileDao = new FileDao();
		NotifyDao notifyDao = new NotifyDao();
		UserDao userDao = new UserDao();
		if (offset == 0) {
			fileList = fileDao.getUsersFileListByUserID(uid, offset, 10);
		} else {
			fileList = fileDao.getUsersFileListByUserID(uid, offset, offset+10);
		}

		Iterator<edu.tjpu.share.po.File> fileIterator = fileList.iterator();
		while (fileIterator.hasNext()) {
			FileForDownload download = new FileForDownload();
			edu.tjpu.share.po.File file = fileIterator.next();

			download.setCdate(file.getUploaddate());
			download.setFid(file.getFid());
			download.setFname(file.getFname());
			StringBuilder sb = new StringBuilder(file.getFurl());
			String path = sb.subSequence(
					sb.indexOf(File.separator + "ShareServer"), sb.length())
					.toString();

			download.setFurl(path);
			download.setMsg(notifyDao.getMsgByFID(file.getFid()));
			download.setUnamefrom(userDao.getUserByID(file.getUidfrom())
					.getUname());

			filefordownloadList.add(download);
		}

		return filefordownloadList;

	}

}
