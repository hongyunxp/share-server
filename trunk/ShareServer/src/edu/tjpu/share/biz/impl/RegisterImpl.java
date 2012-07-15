package edu.tjpu.share.biz.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import edu.tjpu.share.biz.IRegister;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.User;
import edu.tjpu.share.po.UserVo;
import edu.tjpu.share.util.FileUtil;

public class RegisterImpl implements IRegister {

	@Override
	public UserVo doRegister(UserVo in) {
		UserDao userDao = new UserDao();

		if (!userDao.checkUserName(in.getUname())) {
			UserVo userNull = new UserVo();
			userNull.setUid(0);
			return userNull;
		}

		int gmc = userDao.getUserGradeMajorClassRelation(in.getGid(),
				in.getMid(), in.getCid());

		Date uregdate = new Date();
		URL base = this.getClass().getResource("");
		String filePath = null;
		try {
			filePath = new File(base.getFile(), "../../../../../../../avatar/"
					+ "/default.jpg").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User u = new User();
		try {
			BeanUtils.copyProperties(u, in);
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		} catch (InvocationTargetException e2) {
			e2.printStackTrace();
		}
		u.setUregdate(uregdate);
		u.setUavatar(filePath);
		u.setGmcid(gmc);

		User userBack = userDao.register(u);
		UserVo realBack = new UserVo();

		if (userBack != null) {
			try {
				BeanUtils.copyProperties(realBack, userBack);
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
			try {
				realBack.setUavatar(FileUtil.readFileInBytes(new java.io.File(
						userBack.getUavatar())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("UserDao.register()失败！");
		}
		return realBack;
	}

}
