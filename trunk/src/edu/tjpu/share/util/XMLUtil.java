package edu.tjpu.share.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import edu.tjpu.share.po.Classes;
import edu.tjpu.share.po.FileForDownload;
import edu.tjpu.share.po.Grade;
import edu.tjpu.share.po.Major;
import edu.tjpu.share.po.User;
import edu.tjpu.share.po.UserForTransfer;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class XMLUtil {
	public static void generateUser(PrintWriter pw, User user) {
		Document document = DocumentHelper.createDocument();

		Element userElement = document.addElement("user");

		Element idElement = userElement.addElement("id");
		idElement.setText(user.getUid() + "");
		if (user.getUid() != 0) {
			Element nameElement = userElement.addElement("name");
			nameElement.setText(user.getUname());

			Element passwdElement = userElement.addElement("passwd");
			passwdElement.setText(user.getUpasswd());

			Element avatarElement = userElement.addElement("avatar");
			avatarElement.setText(user.getUavatar());

			Element regdateElement = userElement.addElement("regdate");
			regdateElement.setText(user.getUregdate().toString());

			Element gmcElement = userElement.addElement("gmc");
			gmcElement.setText(user.getGmcid() + "");
		}
		// document.addDocType("catalog", null, "file://c:/Dtds/catalog.dtd");
		try {
			XMLWriter output = new XMLWriter(pw);
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void generateAcademyList(PrintWriter pw,
			List<Grade> academyList) {
		Document document = DocumentHelper.createDocument();

		Iterator<Grade> academyIterator = academyList.iterator();

		Element academylistElement = document.addElement("academyList");
		while (academyIterator.hasNext()) {
			Grade academy = academyIterator.next();

			Element academyElement = academylistElement.addElement("academy");

			Element idElement = academyElement.addElement("id");
			idElement.setText(academy.getGid() + "");

			Element nameElement = academyElement.addElement("name");
			nameElement.setText(academy.getGname());

		}
		try {
			XMLWriter output = new XMLWriter(pw);
			// PrintWriter pw2 = new PrintWriter(System.out);
			// XMLWriter output2 = new XMLWriter(pw2);
			output.write(document);
			output.close();
			// output2.write(document);
			// output2.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void generateMajorList(PrintWriter pw, List<Major> majorList) {
		Document document = DocumentHelper.createDocument();

		Iterator<Major> majorIterator = majorList.iterator();

		Element majorListElement = document.addElement("majorList");
		while (majorIterator.hasNext()) {
			Major major = majorIterator.next();

			Element majorElement = majorListElement.addElement("major");

			Element idElement = majorElement.addElement("id");
			idElement.setText(major.getMid() + "");

			Element nameElement = majorElement.addElement("name");
			nameElement.setText(major.getMname());

		}
		try {
			XMLWriter output = new XMLWriter(pw);
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void generateClassList(PrintWriter pw, List<Classes> classList) {
		Document document = DocumentHelper.createDocument();

		Iterator<Classes> classIterator = classList.iterator();

		Element classListElement = document.addElement("classList");
		while (classIterator.hasNext()) {
			Classes classes = classIterator.next();

			Element classElement = classListElement.addElement("class");

			Element idElement = classElement.addElement("id");
			idElement.setText(classes.getCid() + "");

			Element nameElement = classElement.addElement("name");
			nameElement.setText(classes.getCname());

		}
		try {
			XMLWriter output = new XMLWriter(pw);
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void generateUserList(PrintWriter pw,
			List<UserForTransfer> userList) {
		Document document = DocumentHelper.createDocument();

		Iterator<UserForTransfer> userIterator = userList.iterator();

		Element userListElement = document.addElement("userList");
		while (userIterator.hasNext()) {
			UserForTransfer user = userIterator.next();

			Element userElement = userListElement.addElement("user");

			Element idElement = userElement.addElement("id");
			idElement.setText(user.getUid() + "");

			Element nameElement = userElement.addElement("name");
			nameElement.setText(user.getUname());

			Element avatarElement = userElement.addElement("avatar");
			avatarElement.setText(user.getUavatar());

			Element mnameElement = userElement.addElement("mname");
			mnameElement.setText(user.getMname());

		}
		try {
			XMLWriter output = new XMLWriter(pw);
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void generateFileList(PrintWriter pw,
			List<FileForDownload> fileList) {
		Document document = DocumentHelper.createDocument();

		Iterator<FileForDownload> fileIterator = fileList.iterator();

		Element fileListElement = document.addElement("fileList");
		while (fileIterator.hasNext()) {
			FileForDownload file = fileIterator.next();

			Element fileElement = fileListElement.addElement("file");

			Element idElement = fileElement.addElement("fid");
			idElement.setText(file.getFid() + "");

			Element nameElement = fileElement.addElement("fname");
			nameElement.setText(file.getFname());

			Element urlElement = fileElement.addElement("furl");
			urlElement.setText(file.getFurl());

			Element unamefromElement = fileElement.addElement("unamefrom");
			unamefromElement.setText(file.getUnamefrom());

			Element msgfromElement = fileElement.addElement("msg");
			msgfromElement.setText(file.getMsg());

			Element cdatefromElement = fileElement.addElement("cdate");
			cdatefromElement.setText(file.getCdate().toString());
		}
		try {
			XMLWriter output = new XMLWriter(pw);
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void generateStatus(PrintWriter pw, boolean status) {
		Document document = DocumentHelper.createDocument();

		Element statusElement = document.addElement("status");
		if (status)
			statusElement.addText("1");
		else
			statusElement.addText("0");
		try {
			XMLWriter output = new XMLWriter(pw);
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
