package edu.tjpu.share.dao;

import edu.tjpu.share.po.FileLog;
import edu.tjpu.share.util.DBConn;

public class FileLogDao {
	public boolean addNewLog(FileLog fileLog) {
		DBConn dbConn = new DBConn();

		String strSQL = "INSERT INTO filelog(Fid,Furl,Uploaddate,Uidto,Uidfrom,Isread,Fname,Nid,Ndate,Nisread,Notify) VALUES(?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

		int result = dbConn.execOther(
				strSQL,
				new Object[] {fileLog.getFid(),fileLog.getFurl(),fileLog.getUploaddate(),fileLog.getUidto(),fileLog.getUidfrom(),fileLog.getIsread(),fileLog.getFname(),fileLog.getNid(),fileLog.getNdate(),fileLog.getNisread(),fileLog.getNotify() });
		dbConn.closeConn();
		return result > 0 ? true : false;
	}
}
