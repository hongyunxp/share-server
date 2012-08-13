package aservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.phprpc.PHPRPC_Server;

import edu.tjpu.share.biz.impl.UserModifyImpl;

/**
 * Servlet implementation class UserModifyServlet
 */
@WebServlet("/servlet/UserModifyServlet")
public class UserModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		UserModifyImpl userModify = new UserModifyImpl();
		PHPRPC_Server usrmdfyRPCServer= new PHPRPC_Server();
		usrmdfyRPCServer.add(userModify);
		usrmdfyRPCServer.start(request, response);
	}

}
