package edu.tjpu.share.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtil {

	public static byte[] readFileInBytes(File fileIn) throws IOException {
		Long fileLength = fileIn.length();
		if (fileLength > Integer.MAX_VALUE) {
			throw new IOException("File Too Large to Read!" + fileIn.getName());
		}
		FileInputStream is = new FileInputStream(fileIn);
		byte[] bytes = new byte[fileLength.intValue()];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file "
					+ fileIn.getName());
		}

		is.close();
		return bytes;
	}

	public static void wirteByteToFile(byte[] b, String filePath) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			fos.write(b);
			fos.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void writeObjectToFile(Object o, String sFilePath) {
		FileOutputStream fos = null;
		ObjectOutputStream ooS = null;
		try {
			fos = new FileOutputStream(sFilePath);
			ooS = new ObjectOutputStream(fos);
			ooS.writeObject(o);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (ooS != null) {
					ooS.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Object ReadObjectFromFile(String sFilePath) {
		Object serializeResult = null;
		FileInputStream fiStream = null;
		ObjectInputStream oiStream = null;
		try {
			fiStream = new FileInputStream(sFilePath);
			oiStream = new ObjectInputStream(fiStream);
			serializeResult = oiStream.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fiStream != null)
					fiStream.close();
				if (oiStream != null) {
					oiStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return serializeResult;

	}

}
