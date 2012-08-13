package edu.tjpu.share.biz.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.tjpu.share.biz.IUserModify;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;
import edu.tjpu.share.po.UserVo;
import edu.tjpu.share.util.FileUtil;
import edu.tjpu.share.util.IpTimeStamp;

public class UserModifyImpl implements IUserModify {

	@Override
	public boolean doModifyPassword(UserVo user) {
		UserDao userDao = new UserDao();
		//getXmppUsername为新密码
		return userDao.userPwdUpdate(user.getUpasswd(),user.getXmppUsername(), user.getUid());
	}

	@Override
	public boolean doUpdateAvatar(UserVo userVo) {
		URL base = this.getClass().getResource("");
		String filePath = null;
		try {
			filePath = new File(base.getFile(), "../../../../../../../avatar/")
					.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 文件存储、路径
		//getXmppUsername为头像文件后缀名
		String furl = filePath + File.separator
				+ IpTimeStamp.getIpTimeRand(userVo.getXmppUsername());
		FileUtil.wirteByteToFile(userVo.getUavatar(), furl);
		
		UserDao userDao = new UserDao();
		User user = new User();
		user.setUid(userVo.getUid());
		user.setUavatar(furl);
		user.setUname(userVo.getUname());
		if(userDao.userInfoUpdate(user)==null){
			return false;
		}else{
			return true;
		}
	}

}
