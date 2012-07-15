package edu.tjpu.share.biz.impl;

import java.util.Iterator;
import java.util.List;

import edu.tjpu.share.biz.IUserList;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.UserVo;

public class UserListImpl implements IUserList {

	@Override
	public List<UserVo> getUserList(int gid, int mid, int cid,int uid) {
		UserDao userDao = new UserDao();
		List<UserVo> userList = userDao.getUserListByClasses(gid, mid,
				cid);
		Iterator<UserVo> userListIterator =  userList.iterator();
		while(userListIterator.hasNext()){
			UserVo user = userListIterator.next();
			if(user.getUid() == uid){
				userListIterator.remove();
			}
		}
		return userList;
	}

}
