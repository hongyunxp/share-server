package edu.tjpu.share.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tjpu.share.po.User;
import edu.tjpu.share.po.UserVo;
import edu.tjpu.share.util.DBConn;
import edu.tjpu.share.util.FileUtil;
import edu.tjpu.share.util.MD5Util;

public class UserDao {
	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return user
	 */
	public User userLogin(User user) {
		String strSQL = "select * from user where Uname = ? and Upasswd = ? ";

		String uname = user.getUname();
		String upasswd = user.getUpasswd();

		String md5password = MD5Util.MD5Encode(upasswd, "utf-8");

		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { uname,
				md5password });

		User userBack = null;
		try {

			while (rs.next()) {
				userBack = new User();
				userBack.setUid(rs.getInt("Uid"));
				userBack.setUname(rs.getString("Uname"));
				userBack.setUpasswd(rs.getString("Upasswd"));
				userBack.setGmcid(rs.getInt("GMCid"));
				userBack.setUregdate(rs.getTimestamp("Uregdate"));
				userBack.setUavatar(rs.getString("Uavatar"));
				userBack.setXmppUsername(rs.getString("Xmppname"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return userBack;
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	public User register(User user) {
		DBConn dbConn = new DBConn();

		String strSQL = "insert into user(Uname, Upasswd, Uregdate, Uavatar, GMCid,Xmppname) values(?, ?, ?, ?, ?,?)";

		int affectedRows = dbConn
				.execOther(
						strSQL,
						new Object[] { user.getUname(),
								MD5Util.MD5Encode(user.getUpasswd(), "utf-8"),
								user.getUregdate(), user.getUavatar(),
								user.getGmcid() ,""});
		if (affectedRows > 0) {
			User u = userLogin(user);
			dbConn.closeConn();
			return u;
		}
		dbConn.closeConn();
		return null;
	}

	/**
	 * 用户信息修改
	 * 
	 * @param user
	 * @return
	 */
	public User userInfoUpdate(User user) {
		DBConn dbConn = new DBConn();

		String strSQL = "update  user set Uname = ?, Uavatar = ? where Uid = ?";

		int affectedRows = dbConn.execOther(
				strSQL,
				new Object[] { user.getUname(), user.getUavatar(),
						user.getUid() });
		dbConn.closeConn();
		if (affectedRows > 0)
			return user;
		else
			return null;
	}

	/**
	 * 通过用户的id获取用户信息
	 * 
	 * @param uid
	 * @return
	 */
	public User getUserByID(int uid) {
		String strSQL = "select * from user where Uid = ? ";

		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { uid });

		User userBack = null;
		try {

			while (rs.next()) {
				userBack = new User();
				userBack.setUid(rs.getInt("Uid"));
				userBack.setUname(rs.getString("Uname"));
				userBack.setUpasswd(rs.getString("Upasswd"));
				userBack.setGmcid(rs.getInt("GMCid"));
				userBack.setUregdate(rs.getTimestamp("Uregdate"));
				userBack.setUavatar(rs.getString("Uavatar"));
				userBack.setXmppUsername(rs.getString("Xmppname"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return userBack;
	}

	/**
	 * 获取用于传输数据的传输对象列表
	 * 
	 * @param gid
	 * @param mid
	 * @param cid
	 * @return
	 */
	public List<UserVo> getUserListByClasses(int gid, int mid, int cid) {
		// UserForTransfer 的String 类型 avatar 中 存Base64编码的图片
		List<UserVo> backUserForTransfers = new ArrayList<UserVo>();
		int GMCid = getUserGradeMajorClassRelation(gid, mid, cid);

		String strSQL = "select * from user where GMCid = ?";
		DBConn dbConn = new DBConn();

		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { GMCid });
		try {

			while (rs.next()) {

				UserVo transfer = new UserVo();

				transfer.setUid(rs.getInt("Uid"));
				transfer.setUname(rs.getString("Uname"));

				transfer.setUavatar(FileUtil.readFileInBytes(new java.io.File(
						rs.getString("Uavatar"))));

				String[] names = getUsersGMCinfoByID(rs.getInt("Uid"));
				transfer.setGid(gid);
				transfer.setGname(names[0]);

				transfer.setMid(mid);
				transfer.setMname(names[1]);

				transfer.setCid(cid);
				transfer.setCname(names[2]);

				transfer.setXmppUsername(rs.getString("Xmppname"));
				backUserForTransfers.add(transfer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}

		return backUserForTransfers;
	}

	/**
	 * 通过GradeID, MajorID和ClassID获得它们之间的关系号。
	 * 
	 * @param gid
	 * @param mid
	 * @param cid
	 * @return
	 */
	public int getUserGradeMajorClassRelation(int gid, int mid, int cid) {

		String strSQL = "select GMCid from grademajorclass where Gid  = ? and Mid = ? and Cid = ?";
		DBConn dbConn = new DBConn();

		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { gid, mid, cid });
		int GMCid = 0;
		try {

			while (rs.next()) {
				GMCid = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return GMCid;
	}

	/**
	 * 获取user的年级名，专业名和班级名， String[0]是专业名，String[1]是专业名，String[2]是班级名
	 * 
	 * @param uid
	 * @return
	 */
	public String[] getUsersGMCinfoByID(int uid) {
		// 返回String数组中
		// 第一项为Grade年级名
		// 第二项为Major专业名
		// 第三项为Class班级名

		String[] backStr = new String[3];
		DBConn dbConn = new DBConn();

		String strSQL = "select Gid, Mid, Cid from grademajorclass where GMCid = (select GMCid from user where Uid = ?)";

		int Gid = 0;
		int Mid = 0;
		int Cid = 0;
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { uid });
		try {

			while (rs.next()) {
				Gid = rs.getInt("Gid");
				Mid = rs.getInt("Mid");
				Cid = rs.getInt("Cid");
			}

			strSQL = "select grade.Gname, major.Mname, class.Cname from grade, major, class where grade.Gid = ? and major.Mid = ? and class.Cid = ?";
			rs = dbConn.execQuery(strSQL, new Object[] { Gid, Mid, Cid });
			while (rs.next()) {
				backStr[0] = rs.getString(1);
				backStr[1] = rs.getString(2);
				backStr[2] = rs.getString(3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}

		return backStr;
	}

	/**
	 * 如果没有同名则返回true，有则返回false
	 * 
	 * @param uname
	 * @return
	 */
	public boolean checkUserName(String uname) {
		String check = "select Uname from user where Uname=?";
		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(check, new Object[] { uname });

		try {
			if (!rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return false;

	}

	public List<User> getUsersByGMCID(int gmcid) {
		String strSQL = "select * from user where GMCid = ?";

		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { gmcid });

		List<User> users = new ArrayList<User>();
		try {

			while (rs.next()) {
				User userBack = new User();
				userBack.setUid(rs.getInt("Uid"));
				userBack.setUname(rs.getString("Uname"));
				userBack.setUpasswd(rs.getString("Upasswd"));
				userBack.setGmcid(rs.getInt("GMCid"));
				userBack.setUregdate(rs.getTimestamp("Uregdate"));
				userBack.setUavatar(rs.getString("Uavatar"));
				userBack.setXmppUsername(rs.getString("Xmppname"));
				users.add(userBack);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return users;

	}

	public List<User> getALLUsers() {
		String strSQL = "select * from user";

		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] {});

		List<User> users = new ArrayList<User>();
		try {

			while (rs.next()) {
				User userBack = new User();
				userBack.setUid(rs.getInt("Uid"));
				userBack.setUname(rs.getString("Uname"));
				userBack.setUpasswd(rs.getString("Upasswd"));
				userBack.setGmcid(rs.getInt("GMCid"));
				userBack.setUregdate(rs.getTimestamp("Uregdate"));
				userBack.setUavatar(rs.getString("Uavatar"));
				userBack.setXmppUsername(rs.getString("Xmppname"));
				users.add(userBack);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return users;

	}

	public Map<String, Integer> getUserGradeMajorClassByUID(int uid) {
		String strSQL = "select Gid, Mid, Cid from grademajorclass where GMCid in (select GMCid from user where Uid = ? )";

		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { uid });

		Map<String, Integer> map = new HashMap<String, Integer>();
		try {

			while (rs.next()) {
				map.put("Gid", rs.getInt("Gid"));
				map.put("Mid", rs.getInt("Mid"));
				map.put("Cid", rs.getInt("Cid"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return map;

	}

	public boolean userPwdUpdate(String oldPassword,String newPassword, int uid) {
		DBConn dbConn = new DBConn();

		String md5OldPassword = MD5Util.MD5Encode(oldPassword, "utf-8");
		String md5NewPassword = MD5Util.MD5Encode(newPassword, "utf-8");

		String strSQL = "update  user set Upasswd = ? where Uid = ? and Upasswd = ?";

		int affectedRows = dbConn.execOther(strSQL, new Object[] { md5NewPassword,
				uid ,md5OldPassword});
		dbConn.closeConn();
		return affectedRows > 0 ? true : false;
	}
	
	 public boolean userPwdUpdate(String password, int uid) {
         DBConn dbConn = new DBConn();

         String md5password = MD5Util.MD5Encode(password, "utf-8");

         String strSQL = "update  user set Upasswd = ? where Uid = ?";

         int affectedRows = dbConn.execOther(strSQL, new Object[] { md5password,
                         uid });
         dbConn.closeConn();
         return affectedRows > 0 ? true : false;
 }

	public boolean setXMPPName(int uid, String xmppuname) {
		DBConn dbConn = new DBConn();
		String strSQL = "update  user set Xmppname = ? where Uid = ?";

		int affectedRows = dbConn.execOther(strSQL, new Object[] { xmppuname,
				uid });
		dbConn.closeConn();
		return affectedRows > 0 ? true : false;

	}

	public String getXMPPName(int uid) {
		String check = "select Xmppname from user where Uid=?";
		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(check, new Object[] { uid });

		try {
			if (rs.next())
				return rs.getString("Xmppname");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return null;

	}
	
	public String getUserNameById(int uid) {
		String check = "select Uname from user where uid=?";
		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(check, new Object[] { uid });

		try {
			if (rs.next())
				return rs.getString("Uname");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			dbConn.closeConn();
		}
		return null;

	}
}
