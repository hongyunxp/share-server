package edu.tjpu.share.biz;

import edu.tjpu.share.po.UserVo;

public interface IUserModify {
public boolean doModifyPassword(UserVo user);
public boolean doUpdateAvatar(UserVo user);
}
