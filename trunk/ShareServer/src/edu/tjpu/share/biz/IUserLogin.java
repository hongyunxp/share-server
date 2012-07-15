package edu.tjpu.share.biz;

import edu.tjpu.share.po.UserVo;

public interface IUserLogin {

	public UserVo doLogin(UserVo in,boolean needAvatar);
}
