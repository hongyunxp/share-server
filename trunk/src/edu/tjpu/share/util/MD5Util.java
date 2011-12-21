package edu.tjpu.share.util;

import java.io.UnsupportedEncodingException;

import com.twmacinta.util.MD5;

public class MD5Util {

	public static String MD5Encode(String s, String charset_name){
		String hashback = null;
	    try {
	    	MD5 md5 = new MD5();
			md5.Update(s, charset_name);
			hashback = md5.asHex();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	    
		return hashback;
		
	}
}
