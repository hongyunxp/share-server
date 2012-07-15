package edu.tjpu.share.po;

import java.io.Serializable;

public class Grade implements Serializable{

	@Override
	public String toString() {
		return gname;
	}
	private static final long serialVersionUID = 1L;
	private int gid;
	private String gname;
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
	
}
