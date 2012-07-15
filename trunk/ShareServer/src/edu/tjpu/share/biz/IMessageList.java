package edu.tjpu.share.biz;

import java.util.List;

import edu.tjpu.share.po.FileForDownload;

public interface IMessageList {
	public List<FileForDownload> getMessageList(int uid);

}
