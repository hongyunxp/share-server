package edu.tjpu.share.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tjpu.share.po.User;

public class LoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		String URI = request.getRequestURI();

		boolean flag = URI.contains("filepages") || URI.contains("userpages")
				|| URI.contains("index.jsp") || URI.contains("DownloadServlet")
				|| URI.contains("FileServlet")
				|| URI.contains("UpdateAvatarServlet")
				|| URI.contains("UploadServlet") || URI.contains("UserServlet");

		if (!flag)
			arg2.doFilter(request, response);
		else {
			User user = (User) request.getSession().getAttribute("user");
			if (user == null || user.getUid() < 1) {
				response.sendRedirect(request.getContextPath() + "/noLogin.jsp");
				return;
			} else {
				arg2.doFilter(request, response);
			}
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
