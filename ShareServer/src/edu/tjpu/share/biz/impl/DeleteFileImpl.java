package edu.tjpu.share.biz.impl;

import java.util.List;

import edu.tjpu.share.biz.IDeleteFile;
import edu.tjpu.share.dao.FileDao;

public class DeleteFileImpl implements IDeleteFile {

	@Override
	public boolean doDeleteFile(List<Integer> fidlist) {
		FileDao fileDao = new FileDao();
		boolean status = fileDao.deleteFileByIDList(fidlist);
		return status;
	}

}
