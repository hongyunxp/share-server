package edu.tjpu.share.po;

import java.io.Serializable;
import java.util.Date;

public class FileForDownload implements Serializable{

	private static final long serialVersionUID = 1L;
	private int fid;
	private String furl;
	private String unamefrom;
	private String msg;
	private String fname;
	private Date cdate;
	
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public String getFurl() {
		return furl;
	}
	public void setFurl(String furl) {
		this.furl = furl;
	}
	public String getUnamefrom() {
		return unamefrom;
	}
	public void setUnamefrom(String unamefrom) {
		this.unamefrom = unamefrom;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
