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

public class AndroidFilter implements Filter {
	//public static final String USER_AGENT = "Mozilla/5.0 (Linux; U; Android 2.2) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		//String ua = request.getHeader("User-Agent");
		//boolean flag = (ua.equals(USER_AGENT));
		String URI = request.getRequestURI();

		boolean flag = URI.contains("LoginServlet") || URI.contains("MessageListServlet")
				|| URI.contains("RegisterServlet") || URI.contains("SelectGMCServlet")
				|| URI.contains("SuccessServlet")
				|| URI.contains("UpdateServlet")
				|| URI.contains("UploadFromAndroid") || URI.contains("UserListServlet")|| URI.contains("UserModifyServlet");
		
		if(!flag){
			response.sendRedirect(request.getContextPath() + "/noLogin.jsp");
			return;
		}else {
			arg2.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
