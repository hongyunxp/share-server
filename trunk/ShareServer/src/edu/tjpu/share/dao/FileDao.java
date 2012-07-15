package edu.tjpu.share.dao;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.tjpu.share.po.FileForUpload;
import edu.tjpu.share.po.Notify;
import edu.tjpu.share.util.DBConn;

public class FileDao {
	/**
	 * 批量添加文件,若list为null或list，size为0，返回false
	 * 
	 * @param fileBeanList
	 * @return
	 */
	public boolean addFileByServer(List<edu.tjpu.share.po.File> fileBeanList,
			String msg) {

		int result = 0;
		if (fileBeanList == null || fileBeanList.size() < 1) {
			return false;
		}

		DBConn dbConn = new DBConn();
		// Connection conn = dbConn.getConn();
		UserDao userDao = new UserDao();
		NotifyDao notifyDao = new NotifyDao();
		Notify notify = null;

		// boolean flag = false;
		try {

			// flag = conn.getAutoCommit();
			// 开启事务
			// conn.setAutoCommit(false);
			for (edu.tjpu.share.po.File file : fileBeanList) {

				String strSQL = "insert into file values(?, ?, ?, ?, ?, ?, ?)";
				int temp = dbConn.execOther(
						strSQL,
						new Object[] { null, file.getFurl(),
								file.getUploaddate(), file.getUidto(),
								file.getUidfrom(), 0, file.getFname() });

				if (temp > 0 && result > -1) {
					result = temp;
				}
				if (temp < 1) {
					result = -1;
				}

				// Notify

				String sql = "select Fid from file where Furl = ? and Uidfrom = ? and Uidto = ?";
				ResultSet rs = dbConn.execQuery(
						sql,
						new Object[] { file.getFurl(), file.getUidfrom(),
								file.getUidto(), });
				int fid = 0;

				if (msg.equals(""))
					msg = userDao.getUserByID(file.getUidfrom()).getUname()
							+ "给您分享了" + file.getFname();
				while (rs.next()) {
					fid = rs.getInt(1);
					notify = new Notify();
					notify.setFid(fid);
					Date ndate = new Date();
					notify.setNdate(ndate);
					notify.setNisread(0);

					notify.setNotify(msg);
					notify.setUidfrom(file.getUidfrom());
					notify.setUidto(file.getUidto());
					notifyDao.addNotify(notify);
					notify = null;
				}
				// Notify

			}

			// 提交事务
			// conn.commit();
			// conn.setAutoCommit(flag);

		} catch (SQLException e) {
			// try {
			// 回滚事务
			// conn.rollback();
			// conn.setAutoCommit(flag);
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return result > -1 ? true : false;
	}

	/**
	 * 批量添加文件,若list为null或list，size为0，返回false
	 * 
	 * @param uploadFileList
	 * @return
	 */
	public boolean addFileByAndroid(FileForUpload inputUploadFile) {
		int result = 0;
		if (inputUploadFile == null || inputUploadFile.getUidto().size() < 1) {
			return false;
		}

		DBConn dbConn = new DBConn();
		// Connection conn = dbConn.getConn();
		NotifyDao notifyDao = new NotifyDao();
		Notify notify = null;

		// boolean flag = false;
		try {

			// flag = conn.getAutoCommit();
			// 开启事务
			// conn.setAutoCommit(false);
			for (int uidto : inputUploadFile.getUidto()) {

				// 文件日期
				Date uploaddate = new Date();
				// 文件日期

				String strSQL = "insert into file values(?, ?, ?, ?, ?, ?, ?)";
				String fname = URLDecoder.decode(inputUploadFile.getFname(),
						"UTF-8");
				int temp = dbConn.execOther(strSQL, new Object[] { null,
						inputUploadFile.getFurl(), uploaddate, uidto,
						inputUploadFile.getUid(), 0, fname });

				if (temp > 0 && result > -1) {
					result = temp;
				}
				if (temp < 1) {
					result = -1;
				}

				// Notify

				String sql = "select Fid from file where Furl = ? and Uidfrom = ? and Uidto = ?";
				ResultSet rs = dbConn.execQuery(sql, new Object[] {
						inputUploadFile.getFurl(), inputUploadFile.getUid(),
						uidto, });
				int fid = 0;
				while (rs.next()) {
					fid = rs.getInt(1);
					notify = new Notify();
					notify.setFid(fid);
					Date ndate = new Date();
					notify.setNdate(ndate);
					notify.setNisread(0);
					String msg = URLDecoder.decode(inputUploadFile.getMsg(),
							"UTF-8");
					notify.setNotify(msg);
					notify.setUidfrom(inputUploadFile.getUid());
					notify.setUidto(uidto);
					notifyDao.addNotify(notify);
					notify = null;
				}
				// Notify

			}

			// 提交事务
			// conn.commit();
			// conn.setAutoCommit(flag);

		} catch (SQLException e) {
			// try {
			// 回滚事务
			// conn.rollback();
			// conn.setAutoCommit(flag);
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return result > -1 ? true : false;
	}

	/**
	 * 批量删除file, 若list为null或list，size为0，返回false
	 * 
	 * @param fidList
	 * @return
	 */
	public boolean deleteFileByIDList(List<Integer> fidList) {
		int result = 0;
		if (fidList == null || fidList.size() < 1) {
			result = -1;
		}
		DBConn dbConn = new DBConn();
		Connection conn = dbConn.getConn();

		boolean flag = false;
		try {

			flag = conn.getAutoCommit();
			// 开启事务
			conn.setAutoCommit(false);
			for (int i : fidList) {

				String strSQL = "select Furl from file where Fid = ?";
				ResultSet rs = dbConn.execQuery(strSQL, new Object[] { i });
				while (rs.next()) {
					String Furl = rs.getString("Furl");

					// Notify
					String sqlcheckfid = "select Fid from file where Furl = ?";
					ResultSet rscheckfid = dbConn.execQuery(sqlcheckfid,
							new Object[] { Furl });
					while (rscheckfid.next()) {
						String sql = "delete from notify where Fid = ?";
						dbConn.execOther(sql,
								new Object[] { rscheckfid.getInt(1) });
					}
					// Notify

					strSQL = "delete from file where Furl = ?";
					int temp = dbConn.execOther(strSQL, new Object[] { Furl });

					if (temp > 0 && result > -1) {
						result = temp;
					}
					if (temp < 1) {
						result = -1;
					}

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
	 * 获得文件列表
	 * 
	 * @param fidList
	 * @return
	 */
	public List<edu.tjpu.share.po.File> getFileListByIDList(
			List<Integer> fidList) {

		DBConn dbConn = new DBConn();

		List<edu.tjpu.share.po.File> files = new ArrayList<edu.tjpu.share.po.File>();
		for (int i : fidList) {
			String strSQL = "select * from file where Fid = ? order by Uploaddate desc";
			ResultSet rs = dbConn.execQuery(strSQL, new Object[] { i });

			try {
				while (rs.next()) {
					edu.tjpu.share.po.File file = new edu.tjpu.share.po.File();
					file.setFid(rs.getInt("Fid"));
					file.setFurl(rs.getString("Furl"));
					file.setUploaddate(rs.getTimestamp("Uploaddate"));
					file.setUidto(rs.getInt("Uidto"));
					file.setUidfrom(rs.getInt("Uidfrom"));
					file.setIsread(rs.getInt("Isread"));
					file.setFname(rs.getString("fname"));
					files.add(file);

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dbConn.closeConn();
			}
		}

		return files;
	}

	/**
	 * 得到分享给该用户的文件列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<edu.tjpu.share.po.File> getUsersFileListByUserID(int uid) {
		// 得到分享给该用户的文件列表

		DBConn dbConn = new DBConn();

		List<edu.tjpu.share.po.File> files = new ArrayList<edu.tjpu.share.po.File>();

		String strSQL = "select * from file where Uidto = ? order by Uploaddate desc";
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { uid });

		try {
			while (rs.next()) {
				edu.tjpu.share.po.File file = new edu.tjpu.share.po.File();
				file.setFid(rs.getInt("Fid"));
				file.setFurl(rs.getString("Furl"));
				file.setUploaddate(rs.getTimestamp("Uploaddate"));
				file.setUidto(rs.getInt("Uidto"));
				file.setUidfrom(rs.getInt("Uidfrom"));
				file.setIsread(rs.getInt("Isread"));
				file.setFname(rs.getString("fname"));
				files.add(file);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return files;
	}

	/**
	 * 更新文件的已读状态, 若list为null或list，size为0，返回false
	 * 
	 * @param inputSuccessFileList
	 * @return
	 */
	public boolean getFileStatusUpdate(
			List<edu.tjpu.share.po.File> inputSuccessFileList) {
		// 更新文件的已读状态

		int result = 0;
		if (inputSuccessFileList == null || inputSuccessFileList.size() < 1) {
			return false;
		}

		DBConn dbConn = new DBConn();
		Connection conn = dbConn.getConn();

		boolean flag = false;
		try {

			flag = conn.getAutoCommit();
			// 开启事务
			conn.setAutoCommit(false);
			for (edu.tjpu.share.po.File file : inputSuccessFileList) {
				String strSQL = "update file set Isread = 1 where Fid = ?";
				int temp = dbConn.execOther(strSQL,
						new Object[] { file.getFid() });

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
	 * 返回文件分享的时间
	 * 
	 * @param fid
	 * @return
	 */
	public Date getFileSharedTimeByFileID(int fid) {

		DBConn dbConn = new DBConn();

		String strSQL = "select Uploaddate from file where Fid = ? order by Uploaddate desc";
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { fid });

		Date date = null;
		try {
			while (rs.next()) {
				date = rs.getTimestamp(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return date;
	}

	public edu.tjpu.share.po.File getFileByFileID(int fid) {

		DBConn dbConn = new DBConn();
		String strSQL = "select * from file where Fid = ? order by Uploaddate desc";
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { fid });

		edu.tjpu.share.po.File file = null;
		try {
			while (rs.next()) {
				file = new edu.tjpu.share.po.File();
				file.setFid(rs.getInt("Fid"));
				file.setFurl(rs.getString("Furl"));
				file.setUploaddate(rs.getTimestamp("Uploaddate"));
				file.setUidto(rs.getInt("Uidto"));
				file.setUidfrom(rs.getInt("Uidfrom"));
				file.setIsread(rs.getInt("Isread"));
				file.setFname(rs.getString("fname"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return file;
	}

	public List<edu.tjpu.share.po.File> getUsersFilesByUserID(int uid) {
		// 得到分享给该用户的文件列表

		DBConn dbConn = new DBConn();

		List<edu.tjpu.share.po.File> files = new ArrayList<edu.tjpu.share.po.File>();

		String strSQL = "select * from file where Uidfrom = ? order by Uploaddate desc";
		ResultSet rs = dbConn.execQuery(strSQL, new Object[] { uid });

		try {
			while (rs.next()) {
				edu.tjpu.share.po.File file = new edu.tjpu.share.po.File();
				file.setFid(rs.getInt("Fid"));
				file.setFurl(rs.getString("Furl"));
				file.setUploaddate(rs.getTimestamp("Uploaddate"));
				file.setUidto(rs.getInt("Uidto"));
				file.setUidfrom(rs.getInt("Uidfrom"));
				file.setIsread(rs.getInt("Isread"));
				file.setFname(rs.getString("fname"));

				if (files.size() == 0)
					files.add(file);

				int count = 0;

				for (int i = 0; i < files.size(); i++) {
					if (file.getFurl().equals(files.get(i).getFurl()))
						count++;
				}

				if (count == 0) {
					files.add(file);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return files;
	}
}
