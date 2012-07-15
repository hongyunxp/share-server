package edu.tjpu.share.po;

import java.io.Serializable;

public class Classes implements Serializable{

	@Override
	public String toString() {
		return cname;
	}
	private static final long serialVersionUID = 1L;
	private int cid;
	private String cname;
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
	
}
