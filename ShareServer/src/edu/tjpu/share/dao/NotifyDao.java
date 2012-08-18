package edu.tjpu.share.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.tjpu.share.po.Notify;
import edu.tjpu.share.util.DBConn;

public class NotifyDao {
	/**
	 * 添加通知
	 * 
	 * @param notify
	 * @return
	 */
	public boolean addNotify(Notify notify) {

		DBConn dbConn = new DBConn();

		String strSQL = "insert into notify values(?, ?, ?, ?, ?, ?, ?)";

		int result = dbConn.execOther(
				strSQL,
				new Object[] { null, notify.getUidfrom(), notify.getUidto(),
						notify.getNdate(), 0, notify.getNotify(),
						notify.getFid() });
		dbConn.closeConn();
		return result > 0 ? true : false;
	}

	/**
	 * 读取通知, 若list为null或list，size为0，返回false
	 * 
	 * @param nidList
	 * @return
	 */
	public boolean readNotify(List<Integer> nidList) {

		int result = 0;
		if (nidList == null || nidList.size() < 1) {
			return false;
		}

		DBConn dbConn = new DBConn();
		Connection conn = dbConn.getConn();

		boolean flag = false;
		try {

			flag = conn.getAutoCommit();
			// 开启事务
			conn.setAutoCommit(false);
			for (int i : nidList) {
				String strSQL = "update notify set Nisread = 1 where Nid = ?";
				int temp = dbConn.execOther(strSQL, new Object[] { i });

				if (temp > 0 && result > -1) {
					result = temp;
				}
				if (temp < 1) {
					result = -1;
				}
			}
			// 提交事务
			conn.commit();
			conn.setAutoCommit(flag);

		} catch (SQLException e) {
			try {
				// 回滚事务
				conn.rollback();
				conn.setAutoCommit(flag);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return result > -1 ? true : false;
	}

	/**
	 * 批量删除通知,若list为null或list，size为0，返回false
	 * 
	 * @param nidList
	 * @return
	 */
	public boolean deleteNotifyByIDList(List<Integer> nidList) {
		int result = 0;
		if (nidList == null || nidList.size() < 1) {
			result = -1;
		}
		DBConn dbConn = new DBConn();
		Connection conn = dbConn.getConn();

		boolean flag = false;
		try {

			flag = conn.getAutoCommit();
			// 开启事务
			conn.setAutoCommit(false);
			for (int i : nidList) {
				String strSQL = "delete from notify where Nid = ?";
				int temp = dbConn.execOther(strSQL, new Object[] { i });

				if (temp > 0 && result > -1) {
					result = temp;
				}
				if (temp < 1) {
					result = -1;
				}
			}

			// 提交事务
			conn.commit();
			conn.setAutoCommit(flag);

		} catch (SQLException e) {
			try {
				// 回滚事务
				conn.rollback();
				conn.setAutoCommit(flag);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return result > -1 ? true : false;
	}

	/**
	 * 获取通知列表
	 * 
	 * @param nidList
	 * @return
	 */
	public List<Notify> getNotifyListByIDList(List<Integer> nidList) {
		DBConn dbConn = new DBConn();

		List<Notify> notifies = new ArrayList<Notify>();
		for (int i : nidList) {
			String strSQL = "select * from notify where Nid = ?";
			ResultSet rs = dbConn.execQuery(strSQL, new Object[] { i });

			try {
				while (rs.next()) {
					Notify notify = new Notify();
					notify.setNid(rs.getInt("Nid"));
					notify.setUidfrom(rs.getInt("Uidfrom"));
					notify.setUidto(rs.getInt("Uidto"));
					notify.setNdate(rs.getTimestamp("Ndate"));
					notify.setNisread(rs.getInt("Nisread"));
					notify.setNotify(rs.getString("Notify"));
					notify.setFid(rs.getInt("Fid"));
					notifies.add(notify);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dbConn.closeConn();
			}
		}

		return notifies;
	}

	/**
	 * 读取给该用户的通知
	 * 
	 * @param uid
	 * @return
	 */
	public List<Notify> getNotifyListByUser(int uid) {
		// 读取给该用户的通知
		DBConn dbConn = new DBConn();

		List<Notify> notifies = new ArrayList<Notify>();

		String strSQL = "select * from notify where Uidto = ?";
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { uid });

		try {
			while (rs.next()) {
				Notify notify = new Notify();
				notify.setNid(rs.getInt("Nid"));
				notify.setUidfrom(rs.getInt("Uidfrom"));
				notify.setUidto(rs.getInt("Uidto"));
				notify.setNdate(rs.getTimestamp("Ndate"));
				notify.setNisread(rs.getInt("Nisread"));
				notify.setNotify(rs.getString("Notify"));
				notify.setFid(rs.getInt("Fid"));
				notifies.add(notify);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return notifies;
	}

	public String getMsgByFID(int fid) {
		DBConn dbConn = new DBConn();

		String msg = null;

		String strSQL = "select Notify from notify where Fid = ?";
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { fid });

		try {
			while (rs.next()) {
				msg = rs.getString("Notify");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return msg;
	}

	public Notify getNotifyByFid(int fid) {
		// 读取给该用户的通知
		DBConn dbConn = new DBConn();

		Notify notify = null;

		String strSQL = "select * from notify where Fid = ?";
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { fid });

		try {
			while (rs.next()) {
				notify = new Notify();
				notify.setNid(rs.getInt("Nid"));
				notify.setUidfrom(rs.getInt("Uidfrom"));
				notify.setUidto(rs.getInt("Uidto"));
				notify.setNdate(rs.getTimestamp("Ndate"));
				notify.setNisread(rs.getInt("Nisread"));
				notify.setNotify(rs.getString("Notify"));
				notify.setFid(rs.getInt("Fid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return notify;
	}

}
