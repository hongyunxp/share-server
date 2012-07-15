package edu.tjpu.share.biz;

import java.util.List;

import edu.tjpu.share.po.Classes;
import edu.tjpu.share.po.Grade;
import edu.tjpu.share.po.Major;

public interface ISelectGMC {
	public List<Grade> getAcademyList();
	public List<Major> getMajorList(int gid);
	public List<Classes> getClassList(int mid);
}
