package edu.tjpu.share.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sun.misc.BASE64Decoder;
import edu.tjpu.share.po.FileForUpload;

public class Base64Util {

	// 将 b 进行 BASE64 编码
	public static String BASE64Encoder(byte[] b) {
		if (b.length == 0)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(b);
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static byte[] BASE64Decoder(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/** */
	/**
	 * 读文件到BASE64字符串
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFileInBASE64(File file) throws IOException {
		if (file.exists() && file.isFile()) {
			long fileLength = file.length();
			if (fileLength > 0L) {
				BufferedInputStream fis = new BufferedInputStream(
						new FileInputStream(file));
				byte[] b = new byte[(int) fileLength];
				while (fis.read(b) != -1) {
				}
				fis.close();
				fis = null;

				return BASE64Encoder(b);
			}
		} else {
			return null;
		}
		return null;
	}

	/** */
	/**
	 * 将BASE64字符串转换成二进制写入文件
	 * 
	 * @param filePath
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static boolean writeBASE64toFile(String filePath, String base64)
			throws IOException {
		File file = new File(filePath);
		synchronized (file) {
			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(filePath));
			fos.write(BASE64Decoder(base64));
			fos.flush();
			fos.close();
		}
		return true;

	}

	public static List<edu.tjpu.share.po.File> convertFileforUpload(
			List<FileForUpload> inputUploadFileList,String baseUrl) throws IOException {
		List<edu.tjpu.share.po.File> FileListbuffer = new ArrayList<edu.tjpu.share.po.File>();

		Iterator<FileForUpload> iterator = inputUploadFileList.iterator();
		while (iterator.hasNext()) {
			edu.tjpu.share.po.File filebuffer = new edu.tjpu.share.po.File();
			FileForUpload uploadbuffer = iterator.next();
			filebuffer.setUidfrom(uploadbuffer.getUid());
			filebuffer.setUidto(uploadbuffer.getUidto());
			filebuffer.setFname(uploadbuffer.getFname());

			long name = System.currentTimeMillis();
			//TODO文件路径
			String furl = baseUrl + name
					+ uploadbuffer.getFname();
			if (!"".equals(uploadbuffer.getBase64bytes())
					&& uploadbuffer.getBase64bytes() != null) {
				writeBASE64toFile(furl, uploadbuffer.getBase64bytes());
				filebuffer.setFurl(furl);
			}
			Date uploaddate = new Date();
			filebuffer.setUploaddate(uploaddate);
		}
		return FileListbuffer;
	}

}
