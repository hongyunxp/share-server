package aservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.phprpc.PHPRPC_Server;

import edu.tjpu.share.biz.impl.MessageListImpl;

public class MessageListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		MessageListImpl msgList = new MessageListImpl();
		PHPRPC_Server msgListRPCServ = new PHPRPC_Server();
		msgListRPCServ.add(msgList);
		msgListRPCServ.start(request, response);

	}

}
