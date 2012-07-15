package edu.tjpu.share.biz.impl;

import java.util.List;

import edu.tjpu.share.biz.ISelectGMC;
import edu.tjpu.share.dao.ClassDao;
import edu.tjpu.share.dao.GradeDao;
import edu.tjpu.share.dao.MajorDao;
import edu.tjpu.share.po.Classes;
import edu.tjpu.share.po.Grade;
import edu.tjpu.share.po.Major;

public class SelectGMCImpl implements ISelectGMC {

	@Override
	public List<Grade> getAcademyList() {
		List<Grade> academyList = null;
		GradeDao gradeDao = new GradeDao();
		academyList = gradeDao.getAllGradesList();
		if (academyList != null) {
			return academyList;
		}else {
			System.out.println("GradeDao.getAllGradesList()失败！");
		}
		return null;
	}

	@Override
	public List<Major> getMajorList(int gid) {
		List<Major> majorList = null;
		MajorDao majorDao = new MajorDao();
		majorList = majorDao.getMajorListByGradeID(gid);
		if (majorList != null) {
			return majorList;
		}else {
			System.out.println("MajorDao.getMajorListByGradeID()失败！");
		}
		return null;
	}

	@Override
	public List<Classes> getClassList(int mid) {
		List<Classes> classList = null;
		ClassDao classDao = new ClassDao();
		classList = classDao.getClassListByMajorID(mid);

		if (classList != null) {
			return classList;
		}else {
			System.out.println("ClassDao.getClassListByMajorID()失败！");
		}
		return null;
	}

}
