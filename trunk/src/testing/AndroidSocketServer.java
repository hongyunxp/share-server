package testing;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AndroidSocketServer
 * 
 * @author Crystal E-mail: tonyduandi@gmail.com
 * @version 创建时间：2011 April 5, 15:21:35 
 */
public class AndroidSocketServer {

	private static final int PORT = 9999;// 端口监听
	private List<Socket> mList = new ArrayList<Socket>();// 存放客户端socket
	private ServerSocket server = null;
	private ExecutorService mExecutorService = null;// 线程池

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AndroidSocketServer();
	}

	public AndroidSocketServer() {
		try {
			server = new ServerSocket(PORT);
			mExecutorService = Executors.newCachedThreadPool();// 创建一个线程池
			System.out.println("Server Start...");
			Socket client = null;
			while (true) {
				client = server.accept();
				mList.add(client);
				mExecutorService.execute(new Service(client));// 开启一个客户端线程.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public class Service implements Runnable {

		private Socket socket;
		private BufferedReader in = null;
		private String msg = "";

		public Service(Socket socket) {
			this.socket = socket;
			try {
				in = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));
				msg = "user:" + this.socket.getInetAddress() + " come total:"
						+ mList.size();
				this.sendmsg();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			// TODO Auto-generated method stub
			try {
				while (true) {
					if ((msg = in.readLine()) != null) {
						if (msg.equals("exit")) {
							System.out.println("sssssssssss");
							mList.remove(socket);
							in.close();
							msg = "user:" + socket.getInetAddress()
									+ " exit total:" + mList.size();
							socket.close();
							this.sendmsg();
							break;
						} else {
							msg = socket.getInetAddress() + " : " + msg;
							this.sendmsg();
						}
					}

				}
			} catch (Exception ex) {
				System.out.println("server 读取数据异常");
				ex.printStackTrace();
			}
		}

		/**
		 * 发送消息给所有客户端
		 */
		public void sendmsg() {
			System.out.println(msg);
			int num = mList.size();
			for (int i = 0; i < num; i++) {
				Socket mSocket = mList.get(i);
				PrintWriter pout = null;
				try {
					pout = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(mSocket.getOutputStream())),
							true);
					pout.println(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

