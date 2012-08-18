package edu.tjpu.share.po;

import java.io.Serializable;
import java.util.Date;

public class FileLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private int lid;
	private int fid;
	private String furl;
	private int uidfrom;
	private int uidto;
	private Date uploaddate;
	private int isread;
	private String fname;
	private int nid;
	private Date ndate;
	private int nisread;
	private String notify;

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
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

	public Date getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}

	public int getIsread() {
		return isread;
	}

	public void setIsread(int isread) {
		this.isread = isread;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public Date getNdate() {
		return ndate;
	}

	public void setNdate(Date ndate) {
		this.ndate = ndate;
	}

	public int getNisread() {
		return nisread;
	}

	public void setNisread(int nisread) {
		this.nisread = nisread;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
