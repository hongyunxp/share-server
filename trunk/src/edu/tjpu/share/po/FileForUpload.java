package edu.tjpu.share.po;

import java.io.Serializable;

public class FileForUpload implements Serializable{
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return fname;
	}
	private String Base64bytes;
	private	int uid;
	private int uidto;
	private String fname;
	private String msg;
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getBase64bytes() {
		return Base64bytes;
	}
	public void setBase64bytes(String base64bytes) {
		Base64bytes = base64bytes;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getUidto() {
		return uidto;
	}
	public void setUidto(int uidto) {
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
