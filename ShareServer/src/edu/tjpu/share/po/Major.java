package edu.tjpu.share.po;

import java.io.Serializable;

public class Major implements Serializable{

	@Override
	public String toString() {
		return mname;
	}
	private static final long serialVersionUID = 1L;
	private int mid;
	private String mname;
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
	
}
