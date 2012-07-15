package edu.tjpu.share.biz.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import edu.tjpu.share.biz.IUserLogin;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;
import edu.tjpu.share.po.UserVo;
import edu.tjpu.share.util.FileUtil;

public class UserLoginImpl implements IUserLogin {

	@Override
	public UserVo doLogin(UserVo in, boolean needAvatar) {
		User userBackGet = null;
		UserVo userBack = null;
		try {

			UserDao userDao = new UserDao();
			User userIn = new User();
			BeanUtils.copyProperties(userIn, in);
			userBackGet = userDao.userLogin(userIn);
			userBack = new UserVo();

			BeanUtils.copyProperties(userBack, userBackGet);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if (userBackGet == null) {
			System.out.println(in.getUname() + "登录失败！");
		} else {

			if (needAvatar && userBack != null) {
				try {
					userBack.setUavatar(FileUtil
							.readFileInBytes(new java.io.File(userBackGet
									.getUavatar())));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				userBack.setUavatar(null);
			}
		}
		return userBack;
	}
}
