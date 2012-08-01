package edu.tjpu.share.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.FileForUpload;

public class FileUploadServer {
	private ServerSocket server = null;
	private String fileOutPath = null;
	private FileForUpload file = null;

	public FileUploadServer() {
	}

	public synchronized void doService(int PORT, String fileOutPath,
			FileForUpload file) {
		this.fileOutPath = fileOutPath;
		this.file = file;
		try {
			server = new ServerSocket(PORT);
			Thread thread = new Thread(new Service(server));
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class Service implements Runnable {

		private Socket socket = null;
		private ServerSocket server = null;
		private BufferedInputStream bis = null;

		public Service(ServerSocket server) {
			this.server = server;
		}

		public void run() {
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			try {
				if (server != null) {
					socket = server.accept();
					if (socket != null && fileOutPath != null) {
						bis = new BufferedInputStream(socket.getInputStream());
						File fileOut = new File(fileOutPath);
						fos = new FileOutputStream(fileOut);
						bos = new BufferedOutputStream(fos);
						int b = -1;
						while ((b = bis.read()) != -1) {
							bos.write(b);
						}
						bos.flush();
					}
				}

				List<Integer> idToList = file.getUidto();
				Iterator<Integer> iterator = idToList.iterator();
				UserDao userDao = new UserDao();
				while (iterator.hasNext()) {
					int uid = iterator.next();
					String xmppname = userDao.getXMPPName(uid);
					if (xmppname != null) {
						XMPPMsgUtil.sendMsg2SingleUser(xmppname, file, userDao);
					}
				}
			} catch (Exception ex) {
				System.out.println("server 读取数据异常");
				ex.printStackTrace();
			} finally {
				try {
					bis.close();
					bos.close();
					fos.close();
					socket.close();
					if (!server.isClosed())
						server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
