package edu.tjpu.share.po;

import java.io.Serializable;

public class UserVo implements Serializable{
	@Override
	public String toString() {
		return uname+" "+mname+"专业";
	}
	private static final long serialVersionUID = 1L;
	
	
	private int uid;
	private String uname;
	private String upasswd;
	private int gid;
	private String gname;
	private int mid;
	private String mname;
	private int cid;
	private String cname;
	private int gmcid;
	private byte[] uavatar;
	
	
	
	public String getUpasswd() {
		return upasswd;
	}
	public void setUpasswd(String upasswd) {
		this.upasswd = upasswd;
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
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public byte[] getUavatar() {
		return uavatar;
	}
	public void setUavatar(byte[] uavatar) {
		this.uavatar = uavatar;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getGmcid() {
		return gmcid;
	}
	public void setGmcid(int gmcid) {
		this.gmcid = gmcid;
	}
	
}
