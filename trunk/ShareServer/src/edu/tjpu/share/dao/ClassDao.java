package edu.tjpu.share.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.tjpu.share.po.Classes;
import edu.tjpu.share.util.DBConn;

public class ClassDao {
	/**
	 * 添加班级
	 * 
	 * @param classes
	 * @return
	 */
	public boolean addClass(Classes classes) {
		DBConn dbConn = new DBConn();

		String strSQL = "insert into class values(?, ?)";

		int result = dbConn.execOther(strSQL, new Object[] { null, classes.getCname() });
		dbConn.closeConn();
		return result > 0 ? true : false;
	}

	/**
	 * 更新班级
	 * 
	 * @param classes
	 * @return
	 */
	public boolean updateClass(Classes classes) {
		
		DBConn dbConn = new DBConn();

		String strSQL = "update class set Cname = ? where Cid = ?";
		int result = dbConn.execOther(strSQL, new Object[] { classes.getCname(), classes.getCid() });
		dbConn.closeConn();
		return result > 0 ? true : false;
	}

	/**
	 * 通过班级ID删除班级，级联删除
	 * 
	 * @param cid
	 * @return
	 */
	@Deprecated
	public boolean deleteClassByID(int cid) {

		int result = 0;
		Boolean flag = true;
		DBConn dbConn = new DBConn();
		Connection conn = dbConn.getConn();
		try {
			flag = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			//删除user表上与class相关的user
			String strSQL = "delete from  user where Cid = ?";
			result = dbConn.execOther(strSQL, new Object[] { cid});

			//删除Grademajorclass表上的class
			strSQL = "delete from  class where Cid = ?";
			result = dbConn.execOther(strSQL, new Object[] { cid});
			
			strSQL = "delete from  class where Cid = ?";
			result = dbConn.execOther(strSQL, new Object[] { cid});

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(flag);
				dbConn.closeConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result > 0 ? true : false;

	}

	/**
	 * 通过班级ID获得班级
	 * 
	 * @param cid
	 * @return
	 */
	public Classes getClassByID(int cid) {
		DBConn dbConn = new DBConn();

		String strSQL = "select * from class where Cid = ?";
		ResultSet rs  = dbConn.execQuery(strSQL, new Object[] { cid});
		
		Classes backClass = null; 
		
		try {
			if(rs.next()) {
				backClass = new Classes();
				backClass.setCid(cid);
				backClass.setCname(rs.getString("Cname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return backClass;
	}

	/**
	 * 获取参数专业中的所有班级列表
	 * 
	 * @param mid
	 * @return
	 */
	public List<Classes> getClassListByMajorID(int mid) {
		// 使用GMC表（grademajorclass）
		
		DBConn dbConn = new DBConn();
		String strSQL = "select * from class  where Cid in (select Cid from grademajorclass where Mid = ?)";
		ResultSet rs  = dbConn.execQuery(strSQL, new Object[] { mid});
		
		List<Classes> backClass = new ArrayList<Classes>(); 
		
		try {
			while(rs.next()) {
				Classes classes = new Classes();
				classes.setCid(rs.getInt("Cid"));
				classes.setCname(rs.getString("Cname"));
				
				backClass.add(classes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}

		return backClass;
	}

}
