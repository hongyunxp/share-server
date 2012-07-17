package edu.tjpu.share.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConn {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public DBConn() {
		// try {
		// Class.forName(Config.CLASS_NAME);
		//
		// String url =
		// Config.DATABASE_URL+"://"+Config.SERVER_IP+":"+Config.SERVER_PORT+"/"+Config.DATABASE_SID;
		// conn =
		// DriverManager.getConnection(url,Config.USERNAME,Config.PASSWORD);
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		
		String jndi = "jdbc/test"; 
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");//固定，不需要修改
			DataSource ds = (DataSource)envContext.lookup(jndi);
			if(ds != null){
			conn = ds.getConnection();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		
		  
		return conn;
	}

	public void closeConn() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int execOther(final String strSQL, final Object[] params) {

		System.out.println("SQL:> " + strSQL);
		try {

			pstmt = conn.prepareStatement(strSQL);
			// 4����̬Ϊpstmt���ֵ
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}

			int affectedRows = pstmt.executeUpdate();

			return affectedRows;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public ResultSet execQuery(final String strSQL, final Object[] params) {

		System.out.println("SQL:> " + strSQL);
		try {

			pstmt = conn.prepareStatement(strSQL);
			// 4����̬Ϊpstmt����ֵ
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}

			rs = pstmt.executeQuery();

			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
