package edu.tjpu.share.dao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.tjpu.share.po.Major;
import edu.tjpu.share.util.DBConn;
public class MajorDao {
	
	/**
	 * 添加专业
	 * @param major
	 * @return
	 */
	public boolean addMajor(Major major){
		DBConn dbConn = new DBConn();

		String strSQL = "insert into major values(?, ?)";

		int result = dbConn.execOther(strSQL, new Object[] {null, major.getMname()});
		dbConn.closeConn();
		return result > 0 ? true : false;
	}
	
	/**
	 * 通过专业ID更新专业
	 * @param major
	 * @return
	 */
	public boolean updateMajor(Major major){
		
		DBConn dbConn = new DBConn();

		String strSQL = "update major set Mname = ? where Mid = ?";
		int result = dbConn.execOther(strSQL, new Object[] { major.getMname(), major.getMid() });
		dbConn.closeConn();
		return result > 0 ? true : false;
	}
	
	/**
	 * 通过专业id删除专业
	 * @param mid
	 * @return
	 */
	@Deprecated
	public boolean deleteMajorByID(int mid){
		return false;
	}
	
	/**
	 * 通过专业id获得专业
	 * @param mid
	 * @return
	 */
	public Major getMajorByID(int mid){
		DBConn dbConn = new DBConn();

		String strSQL = "select * from major where Mid = ? ";
		ResultSet rs  = dbConn.execQuery(strSQL, new Object[] { mid});
		
		Major backMajor = null;
		try {
			while(rs.next()) {
				backMajor = new Major();
				backMajor.setMid(mid);
				backMajor.setMname(rs.getString("Mname"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return backMajor;
	}
	
	/**
	 * 获得分享中与年级id相关的专业列表
	 * @param gid
	 * @return
	 */
	public List<Major> getMajorListByGradeID(int gid){
		//使用GMC表（grademajorclass）
		DBConn dbConn = new DBConn();

		String strSQL = "select * from major where Mid in(select Mid from grademajorclass where Gid = ?)";
		ResultSet rs  = dbConn.execQuery(strSQL, new Object[] { gid });
		
		List<Major> backMajors = new ArrayList<Major>(); 
		
		try {
			while(rs.next()) {
				Major major = new Major();
				major.setMid(rs.getInt("Mid"));
				major.setMname(rs.getString("Mname"));
				backMajors.add(major);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbConn.closeConn();
		}

		return backMajors;
	}
}
