package edu.tjpu.share.po;

import java.io.Serializable;
import java.util.List;

public class FileForUpload implements Serializable{
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return fname;
	}
	private	int uid;
	private List<Integer> uidto;
	private String fname;
	private String msg;
	private String furl;
	
	public String getFurl() {
		return furl;
	}
	public void setFurl(String furl) {
		this.furl = furl;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public List<Integer> getUidto() {
		return uidto;
	}
	public void setUidto(List<Integer> uidto) {
		this.uidto = uidto;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
