package edu.tjpu.share.biz;

import java.util.List;

import edu.tjpu.share.po.UserVo;

public interface IUserList {
	public List<UserVo> getUserList(int gid, int mid, int cid,int uid);
}
