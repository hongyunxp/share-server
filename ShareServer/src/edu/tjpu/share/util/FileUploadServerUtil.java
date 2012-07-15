package edu.tjpu.share.util;

public class FileUploadServerUtil {
	private static FileUploadServer server_instance = null;

	private FileUploadServerUtil() {
	}

	public synchronized static FileUploadServer getInstance() {
		if (server_instance == null) {
			server_instance = new FileUploadServer();
		}
		return server_instance;
	}
}
