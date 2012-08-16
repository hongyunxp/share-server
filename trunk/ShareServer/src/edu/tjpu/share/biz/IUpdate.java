package edu.tjpu.share.biz;

import edu.tjpu.share.po.FileForDownload;

public interface IUpdate {
	public FileForDownload doCheckUpdate(int localVersion);
}
