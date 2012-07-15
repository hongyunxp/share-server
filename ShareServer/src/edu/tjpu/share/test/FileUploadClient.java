package edu.tjpu.share.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileUploadClient {
	private String HOST = null;
	private int PORT = 0;
	private Socket socket = null;
	FileInputStream fis = null;
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;

	public FileUploadClient(String HOST, int PORT) {
		this.HOST = HOST;
		this.PORT = PORT;
	}

	public void doUpload(String FileInUrl) {
		if (FileInUrl != null && HOST != null && PORT != 0) {
			File fileIn = new File(FileInUrl);
			try {
				fis = new FileInputStream(fileIn);
				bis = new BufferedInputStream(fis);
				socket = new Socket(HOST, PORT);
				bos = new BufferedOutputStream(socket.getOutputStream());
				byte[] b = new byte[Long.valueOf(fileIn.length()).intValue()];
				while (bis.read(b) != -1) {
					bos.write(b);
				}
				bos.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					bos.close();
					bis.close();
					socket.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
