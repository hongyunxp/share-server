package edu.tjpu.share.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IpTimeStamp {
	public static String getIpTimeRand(String filename) {
		StringBuilder sbf = new StringBuilder();
		/*
		 * if(ip!=null){ String a[]=ip.split("\\."); //根据点来拆分ip地址，但点要转义 for(int
		 * i=0;i<a.length;i++){ sbf.append(IpTimeStamp.addZero(a[i], 3));
		 * //调用补零的方法，每块ip不足三位的自动补足到三位 }
		 */
		if (filename != null) {
			sbf.append(IpTimeStamp.getTimeStamp()); // 用this来调用外部的方法
			Random random = new Random(); // 要产生随机数
			for (int i = 0; i < 3; i++) { // 产生三位随机数
				sbf.append(random.nextInt(10)); // 每位随机数都不超过10
			}
			sbf.append(filename.substring(filename.lastIndexOf(".")));
		}
		return sbf.toString();
	}

	private static String getTimeStamp() { // 返回时间戳
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	@SuppressWarnings("unused")
	private static String addZero(String str, int len) { // 自动补零的方法，参数为指定的字符串和长度
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.length() < len) {
			s.insert(0, "0"); // 在零的位置上进行补零操作
		}
		return s.toString();
	}

}