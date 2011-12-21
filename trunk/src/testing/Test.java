package testing;

import java.sql.Connection;
import java.util.ArrayList;

import edu.tjpu.share.util.MD5Util;

public class Test {

	ThreadLocal<Connection> t = new ThreadLocal<Connection>();
	ArrayList<String> s = new ArrayList<String>();
	
	public static void main(String[] args) {
		String s = MD5Util.MD5Encode("admin", "utf-8");
		System.out.println(s);
	}
}
