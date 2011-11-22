package testing;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.tjpu.share.dao.ClassDao;
import edu.tjpu.share.dao.FileDao;
import edu.tjpu.share.dao.GradeDao;
import edu.tjpu.share.dao.MajorDao;
import edu.tjpu.share.dao.NotifyDao;
import edu.tjpu.share.dao.UserDao;
import edu.tjpu.share.po.File;
import edu.tjpu.share.po.FileForDownload;
import edu.tjpu.share.po.FileForUpload;
import edu.tjpu.share.po.Notify;
import edu.tjpu.share.po.User;
import edu.tjpu.share.util.Base64Util;

public class TestingServer {
	private static final int POOL_SIZE = 3;
	private static final int PORT = 9999;
	private ExecutorService pool;
	private ServerSocket serverSocket;
	private List<Socket> mList = new ArrayList<Socket>();// 存放客户端socket

	public TestingServer(int poolsize) {
		try {
			serverSocket = new ServerSocket(PORT);
			pool = Executors.newFixedThreadPool(poolsize);
			System.out.println("Server Started...");
			Socket client = null;
			while (true) {
				client = serverSocket.accept();
				mList.add(client);
				pool.execute(new Handler(client));// 开启一个客户端线程.
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TestingServer(POOL_SIZE);
	}

	public class Handler implements Runnable {
		private Socket socket;
		private User requestUser;// 请求的用户的信息
		private List<File> outputFileList;// 需发送的文件列表
		private List<FileForUpload> inputUploadFileList;// 需接收的上传文件列表
		private List<File> inputSuccessFileList;// 需接收的已读文件列表
		private List<Integer> inputUploadUserIDList;// 被分享的用户列表

		InputStream is = null;
		ObjectInputStream ois = null;
		OutputStream os = null;
		ObjectOutputStream oow = null;
		String flag = "none";
		boolean isreg = false;

		public Handler(Socket socket) {
			try {
				this.socket = (Socket) socket;
				requestUser = new User();
				outputFileList = new ArrayList<File>();
				inputUploadFileList = new ArrayList<FileForUpload>();
				inputSuccessFileList = new ArrayList<File>();
				inputUploadUserIDList = new ArrayList<Integer>();

				is = socket.getInputStream();
				ois = new ObjectInputStream(is);
				os = socket.getOutputStream();
				oow = new ObjectOutputStream(os);

				System.out.println("\t新用户:" + this.socket.getInetAddress()
						+ "\t用户总数:" + mList.size());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {

			try {

				int gid = 0;
				int mid = 0;
				int cid = 0;

				while (true) {
					Object obj = ois.readObject();
					// 收到文字的情况
					if (obj instanceof String) {
						if ("request".equals((String) obj)) {
							flag = "requestsuccess";
							continue;
							// 如果收到upload则接收用户上传的数据
						}
						if ("upload".equals((String) obj)) {

							// 传送分级的用户列表

							// 第一次传送全部年级
							GradeDao gradeDao = new GradeDao();
							oow.writeObject(gradeDao.getAllGradesList());
							oow.flush();
							flag = "gradesend";
							continue;
						}

						// 如果收到success则接更新数据库已读信息
						if ("success".equals((String) obj)) {
							flag = "success";
							continue;
						}

						// 登陆的情况
						if ("login".equals((String) obj)) {
							flag = "login";
							continue;
						}

						// 注册的情况，应先发送年级列表共注册用户选择，同时置isreg为true
						if ("register".equals((String) obj)) {
							GradeDao gradeDao = new GradeDao();
							oow.writeObject(gradeDao.getAllGradesList());
							oow.flush();
							flag = "gradesend";
							isreg = true;
							continue;
						}

						// 修改用户信息的情况
						if ("uerinfoupdate".equals((String) obj)) {
							flag = "uerinfoupdate";
							continue;
						}

						// 客户端要求关闭连接
						// 如果收到closeconnection则退出循环关闭连接
						if ("closeconnection".equals((String) obj))
							break;
					}
					// 收到数字的情况
					if (obj instanceof Integer) {
						// flag为gradesend表示grade列表已发送，应该收到用户所选年级ID
						if ("gradesend".equals(flag)) {
							// 获得用户所选年级ID
							gid = (Integer) obj;

							// 第二次传送相应专业列表
							MajorDao majorDao = new MajorDao();
							oow.writeObject(majorDao.getMajorListByGradeID(gid));
							oow.flush();
							flag = "majorsend";
							continue;
						}

						// flag为majorsend表示major列表已发送，应该收到用户所选专业ID
						if ("majorsend".equals(flag)) {
							// 获得用户所选专业ID
							mid = (Integer) obj;

							// 第三次传送相应班级列表
							ClassDao classDao = new ClassDao();
							oow.writeObject(classDao.getClassListByMajorID(mid));
							oow.flush();
							flag = "classsend";
							continue;
						}

						// flag为classsend表示class列表已发送，应该收到用户所选班级ID
						if ("classsend".equals(flag)) {
							// 获得用户所选班级ID
							cid = (Integer) obj;

							// 如果为用户注册则检索得到相应的gmcid并传回
							if (isreg == true) {
								UserDao userDao = new UserDao();
								int gmcid = userDao
										.getUserGradeMajorClassRelation(gid,
												mid, cid);

								oow.writeObject(gmcid);
								oow.flush();
								flag = "gmcidsend";
							} else {
								// 如果不是用户注册 第四次传送相应用户列表
								UserDao userDao = new UserDao();
								oow.writeObject(userDao.getUserListByClasses(
										gid, mid, cid));
								oow.flush();
								flag = "usersend";
							}
							continue;
						}

					}
					// 收到List的情况
					if (obj instanceof List) {
						// flag为usersend表示user列表已发送，应该收到用户所选用户ID
						if ("usersend".equals(flag)) {
							inputUploadUserIDList = (List<Integer>) obj;

							flag = "comingfile";
							continue;
						}

						// flag为comingfile表示，应该收到用户上传的文件
						if ("comingfile".equals(flag)) {
							inputUploadFileList = (List<FileForUpload>) obj;

							// 组合接收的文件列表与被分享的用户ID列表
							Iterator<Integer> userIDIterator = inputUploadUserIDList
									.iterator();
							Iterator<FileForUpload> fileIterator = inputUploadFileList
									.iterator();
							List<FileForUpload> fileListbuffer = new ArrayList<FileForUpload>();
							NotifyDao notifyDao = new NotifyDao();
							while (fileIterator.hasNext()) {
								FileForUpload filebuffer = fileIterator.next();
								while (userIDIterator.hasNext()) {
									//message写入notify
									Notify notify = new Notify();
									int uidto = userIDIterator.next();
									filebuffer.setUidto(uidto);
									Date ndate = new Date();
									notify.setNdate(ndate);
									notify.setNisread(0);
									notify.setNotify(filebuffer.getMsg());
									notify.setUidfrom(filebuffer.getUid());
									notify.setUidto(uidto);
									notifyDao.addNotify(notify);
									fileListbuffer.add(filebuffer);
								}
							}
							// 把接收的文件转换后写入服务器
							FileDao fileDao = new FileDao();
							fileDao.addFileByServer(Base64Util
									.convertFileforUpload(fileListbuffer,""));
							continue;
						}
						// flag为success表示用户需要更新已读信息
						if ("success".equals(flag)) {
							// 接收success
							inputSuccessFileList = (List<File>) obj;

							// 通过接受的文件更新服务器
							FileDao fileDao = new FileDao();
							fileDao.getFileStatusUpdate(inputSuccessFileList);
							continue;
						}
					}
					if (obj instanceof User) {
						// 用户登陆，登陆失败时传回的userBack为NULL
						if ("login".equals(flag)) {
							User userin = (User) obj;
							UserDao userDao = new UserDao();
							User userBack = userDao.userLogin(userin);
							oow.writeObject(userBack);
							oow.flush();
							continue;
						}

						// 用户注册，注册失败时传回的userBack为NULL
						if ("gmcidsend".equals(flag) && isreg == true) {
							User userin = (User) obj;

							UserDao userDao = new UserDao();
							User userBack = null;
							if (userDao.checkUserName(userin.getUname())) {

								long name = System.currentTimeMillis();
								String avatarurl = System.getProperty("user.dir") + "\\uavatar\\" + name + ".jpg";
								boolean isdone = Base64Util.writeBASE64toFile(
										avatarurl, userin.getUavatar());
								if (isdone) {
									userin.setUavatar(avatarurl);
								} else
									userin.setUavatar(null);

								Date regdate = new Date();
								userin.setUregdate(regdate);

								userBack = userDao.register(userin);
							}
							oow.writeObject(userBack);
							oow.flush();

							isreg = false;
							continue;
						}

						// 用户信息修改,修改失败时传回的userBack为NULL
						if ("uerinfoupdate".equals(flag)) {
							User userin = (User) obj;

							long name = System.currentTimeMillis();
							String avatarurl = System.getProperty("user.dir") + "\\uavatar\\" + name + ".jpg";
							boolean isdone = Base64Util.writeBASE64toFile(
									avatarurl, userin.getUavatar());
							if (isdone) {
								userin.setUavatar(avatarurl);
							} else
								userin.setUavatar(null);

							UserDao userDao = new UserDao();
							User userBack = userDao.userInfoUpdate(userin);
							oow.writeObject(userBack);
							oow.flush();
							continue;
						}

						// 已接受请求，发送分享给该用户的文件列表
						if ("requestsuccess".equals(flag)) {
							requestUser = (User) obj;
							// 根据请求用户的信息，从数据库中获取请求内容
							FileDao fileDao = new FileDao();
							outputFileList = fileDao
									.getUsersFileListByUserID(requestUser
											.getUid());
							/*
							 * 测试用 System.out.println(requestUser.getUname());
							 * outputFileList = new ArrayList<File>();
							 * outputFileList.add(new File());
							 */

							//把File转换成FileForDownload
							List<FileForDownload> realoutputlist = new ArrayList<FileForDownload>();
							UserDao userDao = new UserDao();
							NotifyDao notifyDao = new NotifyDao();
							Iterator<File> fileIterator = outputFileList.iterator();
							while(fileIterator.hasNext()){
								File file = fileIterator.next();
								FileForDownload real = new FileForDownload();
								real.setFname(file.getFname());
								real.setFurl(file.getFurl());
								real.setUnamefrom(userDao.getUserByID(file.getUidfrom()).getUname());
								real.setMsg(notifyDao.getMsgByFID(file.getFid()));
								real.setCdate(file.getUploaddate());
								real.setFid(file.getFid());
								realoutputlist.add(real);
							}
							// 发送请求内容

							oow.writeObject(realoutputlist);
							oow.flush();
							continue;
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					ois.close();
					is.close();
					oow.close();
					os.close();
					mList.remove(socket);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}

}
