package edu.tjpu.share.po;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

	@Override
	public String toString() {
		return uname;
	}
	private static final long serialVersionUID = 1L;
	private int uid;
	private String uname;
	private int gmcid;

	private String upasswd;
	private Date uregdate;
	private String uavatar;
	public String getUpasswd() {
		return upasswd;
	}
	public void setUpasswd(String upasswd) {
		this.upasswd = upasswd;
	}
	public Date getUregdate() {
		return uregdate;
	}
	public void setUregdate(Date uregdate) {
		this.uregdate = uregdate;
	}
	public String getUavatar() {
		return uavatar;
	}
	public void setUavatar(String uavatar) {
		this.uavatar = uavatar;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getGmcid() {
		return gmcid;
	}
	public void setGmcid(int gmcid) {
		this.gmcid = gmcid;
	}
	
	
	

}
