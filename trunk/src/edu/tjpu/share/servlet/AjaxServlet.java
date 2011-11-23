package edu.tjpu.share.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.tjpu.share.dao.ClassDao;
import edu.tjpu.share.dao.MajorDao;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.Classes;
import edu.tjpu.share.po.Major;

public class AjaxServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json; charset=utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");

		String type = request.getParameter("type");
		if ("major".equals(type))
			getMajor(request, response);
		if ("class".equals(type))
			getClass(request, response);
		if ("user".equals(type))
			getUser(request, response);
	}

	private void getUser(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");

		String loginName = request.getParameter("loginName").trim();

		UserDao userDao = new UserDao();

		boolean flag = userDao.checkUserName(loginName);
		String s = "";
		if (!flag) {
			s = "<font color='red'>用户名已存在！</font>";
		} else {
			s = "<font color='green'>用户名可以注册</font>";
		}

		PrintWriter out;
		try {
			out = response.getWriter();

			out.write(s);
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getMajor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String strGid = request.getParameter("ID");

		int gid = Integer.parseInt(strGid);

		List<Major> majors = new MajorDao().getMajorListByGradeID(gid);

		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		try {
			for (Major m : majors) {
				JSONObject member = new JSONObject().put("id", m.getMid()).put("name", m.getMname()).put("type", "class");
				jsonArray.put(member);
			}

			json.put("datas", jsonArray);
			PrintWriter out = response.getWriter();

			out.write(json.toString());
			out.flush();
			out.close();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void getClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String strMid = request.getParameter("ID");

		//System.out.println(strMid);

		int mid = Integer.parseInt(strMid);

		List<Classes> classes = new ClassDao().getClassListByMajorID(mid);
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		try {
			for (Classes c : classes) {
				JSONObject member = new JSONObject().put("id", c.getCid()).put("name", c.getCname()).put("type", "users");
				jsonArray.put(member);
			}

			json.put("datas", jsonArray);
			PrintWriter out = response.getWriter();

			out.write(json.toString());
			out.flush();
			out.close();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
