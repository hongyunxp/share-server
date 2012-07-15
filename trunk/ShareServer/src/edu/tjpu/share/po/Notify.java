package edu.tjpu.share.po;

import java.util.Date;

public class Notify {
	private int nid;
	private int uidfrom;
	private int uidto;
	private Date ndate;
	private int nisread;
	private String notify;
	private int fid;
	
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public int getNid() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
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
	
	
}
