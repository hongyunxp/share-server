package edu.tjpu.share.po;

import java.io.Serializable;
import java.util.Date;

public class File implements Serializable{

	@Override
	public String toString() {
		return fname+"--"+uploaddate.toString();
	}
	private static final long serialVersionUID = 1L;
	private int fid;
	private String furl;
	private int uidfrom;
	private int uidto;
	private Date uploaddate;
	private int isread;
	private String fname;
	
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public int getIsread() {
		return isread;
	}
	public Date getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getFurl() {
		return furl;
	}
	public void setFurl(String furl) {
		this.furl = furl;
	}
	public int getUidfrom() {
		return uidfrom;
	}
	public void setUidfrom(int uidfrom) {
		this.uidfrom = uidfrom;
	}
	public int getUidto() {
		return uidto;
	}
	public void setUidto(int uidto) {
		this.uidto = uidto;
	}
	public int isIsread() {
		return isread;
	}
	public void setIsread(int isread) {
		this.isread = isread;
	}
	
	
}
