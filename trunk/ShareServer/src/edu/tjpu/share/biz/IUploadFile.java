package edu.tjpu.share.biz;

import edu.tjpu.share.po.FileForUpload;

public interface IUploadFile {
	public int doUploadFile(FileForUpload file,boolean withoutFile);
}
