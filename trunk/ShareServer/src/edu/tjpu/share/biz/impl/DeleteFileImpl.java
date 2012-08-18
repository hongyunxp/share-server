package edu.tjpu.share.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import edu.tjpu.share.biz.IDeleteFile;
import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.dao.FileLogDao;
import edu.tjpu.share.dao.NotifyDao;
import edu.tjpu.share.po.File;
import edu.tjpu.share.po.FileLog;
import edu.tjpu.share.po.Notify;
import edu.tjpu.share.util.FileUtil;

public class DeleteFileImpl implements IDeleteFile {

	@Override
	public boolean doDeleteFile(List<Integer> fidlist) {
		FileDao fileDao = new FileDao();
		NotifyDao notifyDao = new NotifyDao();
		Iterator<Integer> fidIterator = fidlist.iterator();
		while(fidIterator.hasNext()){
			int fid = fidIterator.next();
			File file = fileDao.getUseless(fid);
			Notify notify = notifyDao.getNotifyByFid(fid);
			if(file!=null&&notify!=null){
			FileLog fileLog = new FileLog();
			try {
				BeanUtils.copyProperties(fileLog, file);
				BeanUtils.copyProperties(fileLog, notify);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			FileLogDao fileLogDao = new FileLogDao();
			fileLogDao.addNewLog(fileLog);
			FileUtil.deleteFileAt(fileLog.getFurl());
			}
		}
		boolean status = fileDao.deleteFileByIDList(fidlist);
		return status;
	}

}
