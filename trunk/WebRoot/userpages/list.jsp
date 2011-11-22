<%@ page language="java" import="java.util.*, edu.tjpu.share.util.*, edu.tjpu.share.po.*, edu.tjpu.share.dao.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>用户列表</TITLE>
		<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
		
		<script type="text/javascript">
		
		function selectPage() {
			location.href="UserServlet?type=list&current=" + $("#current").val();
		}
		</script>
	</HEAD>
	
	<%
			UserDao userDao = new UserDao();
			GradeDao gradeDao = new GradeDao();
			MajorDao majorDao = new MajorDao();
			ClassDao classDao = new ClassDao();
			PageModel<User> model = (PageModel<User>)request.getAttribute("pageModel");
			
			List<User> users = model.getListPages();
		%>
	<BODY>

		<DIV ID="Title_bar">
			<DIV ID="Title_bar_Head">
				<DIV ID="Title_Head"></DIV>
				<DIV ID="Title">
					<!--页面标题-->
					<IMG BORDER="0" WIDTH="13" HEIGHT="13" SRC="${pageContext.request.contextPath}/style/images/title_arrow.gif" />
					用户列表
				</DIV>
				<DIV ID="Title_End"></DIV>
			</DIV>
		</DIV>

		<DIV ID="MainArea" align="center">
<%--		<form action="">--%>
<%--					<select name="grade" id="grade"  >--%>
<%--						<option value="">请选择学院</option>--%>
<%--					</select>--%>
<%--					<select name="major" id="major">--%>
<%--						<option value="">请选择专业</option>--%>
<%--					</select>--%>
<%--					<select name="class" id="class">--%>
<%--						<option value="">请选择班级</option>--%>
<%--					</select>--%>
<%--				<INPUT TYPE="image" SRC="${pageContext.request.contextPath}/style/images/save.png" />--%>
<%--			</form>--%>
		
			<TABLE CELLSPACING="0" CELLPADDING="0" CLASS="TableStyle">

				<!-- 表头-->
				<THEAD align="center">
					<TR ALIGN="CENTER" VALIGN="MIDDLE" ID="TableTitle">
						<TD>
							头像
						</TD>
						<TD>
							用户名
						</TD>
						<TD>
							
							学院
						</TD>
						<TD>
							专业
						</TD>
						<TD>
							班级
						</TD>
						
					</TR>
				</THEAD>

				<!--显示数据列表-->
				<TBODY ID="TableData" CLASS="dataContainer" align="center">
					<%
						for(User u : users) {
							if(u.getUid() == ((User)request.getSession().getAttribute("user")).getUid())continue;
							Map<String, Integer> map = userDao.getUserGradeMajorClassByUID(u.getUid());
							
							String avatar = u.getUavatar();
							avatar = avatar.substring(avatar.lastIndexOf("avatar"), avatar.length());
						%>
						<TR CLASS="TableDetail1 template">
						
							<TD width="10%">
								<img alt="头像" src="<%=avatar %>" width="60"/>
								
							</TD>
							
							<TD width="20%">
								<%=u.getUname() %>
							</TD>
							<TD width="30%">
								<%=gradeDao.getGradeByID(map.get("Gid")).getGname() %>
							</TD>
							<TD width="20%">
								<%=majorDao.getMajorByID(map.get("Mid")).getMname() %>
							</TD>
							<TD width="20%">
								<%=classDao.getClassByID(map.get("Cid")).getCname() %>
							</TD>
							
							
						</TR>
					<%} %>
				</TBODY>
			</TABLE>

			<!-- 其他功能超链接 -->
			<DIV ID="TableTail">
				<DIV ID="TableTail_inside">
					<a href="UserServlet?type=list&current=1">首页</a>    第<select id="current" onchange="selectPage()">
					<%
					
					
					for(int i = 0; i < model.getTotalPages(); i++) {
						String selected = "";
						if((i + 1) == model.getCurrentPage())selected = "selected='selected'";
					%>
					
					<option <%=selected %> value="<%=i+1 %>"><%=i+1 %></option>
					<%} %>
					
					</select>页   <a href="UserServlet?type=list&current=${pageModel.lastPage }">尾页</a>  共${pageModel.totalPages }页
				</DIV>
			</DIV>
		</DIV>
	</BODY>
	
	<%@ include file="/WEB-INF/jsp/Public/returnMsg.jspf"%>
</HTML>
