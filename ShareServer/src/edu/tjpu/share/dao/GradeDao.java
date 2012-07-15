package edu.tjpu.share.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.tjpu.share.po.Grade;
import edu.tjpu.share.util.DBConn;

public class GradeDao {
	/**
	 * 添加年级
	 * @param grade
	 * @return
	 */
	public boolean addGrade(Grade grade){
		DBConn dbConn = new DBConn();

		String strSQL = "insert into grade values(?, ?)";

		int result = dbConn.execOther(strSQL, new Object[] {null, grade.getGname() });
		
		dbConn.closeConn();
		return result > 0 ? true : false;
	}
	
	/**
	 * 更新年级
	 * @param grade
	 * @return
	 */
	public boolean updateGrade(Grade grade){
		DBConn dbConn = new DBConn();

		String strSQL = "update grade set Gname = ? where Gid = ?";
		int result = dbConn.execOther(strSQL, new Object[] { grade.getGname(), grade.getGid() });
		dbConn.closeConn();
		return result > 0 ? true : false;
	}
	
	/**
	 * 删除年级
	 * @param gid
	 * @return
	 */
	@Deprecated
	public boolean deleteGradeByID(int gid){
		return false;
	}
	/**
	 * 通过年级ID获取年级
	 * @param gid
	 * @return
	 */
	public Grade getGradeByID(int gid){
		DBConn dbConn = new DBConn();

		String strSQL = "select * from grade where Gid = ? ";
		ResultSet rs  = dbConn.execQuery(strSQL, new Object[] { gid});
		
		Grade backGrade = null;
		try {
			while(rs.next()) {
				backGrade = new Grade();
				backGrade.setGid(gid);
				backGrade.setGname(rs.getString("Gname"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return backGrade;
	}
	
	/**
	 * 获取年级列表
	 * @return
	 */
	public List<Grade> getAllGradesList(){
		DBConn dbConn = new DBConn();

		String strSQL = "select * from grade";
		ResultSet rs  = dbConn.execQuery(strSQL, new Object[] { });
		
		List<Grade> backGrades = new ArrayList<Grade>(); 
		
		try {
			while(rs.next()) {
				Grade backGrade = new Grade();
				backGrade.setGid(rs.getInt("Gid"));
				backGrade.setGname(rs.getString("Gname"));
				backGrades.add(backGrade);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return backGrades;
	}

}
