<%@ page language="java" import="java.util.*,edu.tjpu.share.util.*, edu.tjpu.share.po.*, edu.tjpu.share.dao.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>文件列表</TITLE>
		<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
		
		<script type="text/javascript">
		
		function selectPage() {
			location.href="FileServlet?type=listshow&current=" + $("#current").val();
		}
		</script>
	</HEAD>
	
	<%
	
	PageModel<File> model = (PageModel<File>)request.getAttribute("pageModel");
	List<File> files = model.getListPages();
	UserDao userDao = new UserDao();
	%>
	
	<BODY>

		<DIV ID="Title_bar">
			<DIV ID="Title_bar_Head">
				<DIV ID="Title_Head"></DIV>
				<DIV ID="Title">
					<!--页面标题-->
					<IMG BORDER="0" WIDTH="13" HEIGHT="13" SRC="${pageContext.request.contextPath}/style/images/title_arrow.gif" />
					我的分享
				</DIV>
				<DIV ID="Title_End"></DIV>
			</DIV>
		</DIV>

		<DIV ID="MainArea">
			<TABLE CELLSPACING="0" CELLPADDING="0" CLASS="TableStyle">

				<!-- 表头-->
				<THEAD align="center">
					<TR ALIGN="CENTER" VALIGN="MIDDLE" ID="TableTitle">
						<TD>
							文件名称
						</TD>
						<TD>
							文件来源
						</TD>
						<TD>
							文件分享时间
						</TD>
						<TD>
							文件状态
						</TD>
						
						<TD>
							文件下载
						</TD>
					</TR>
				</THEAD>

				<!--显示数据列表-->
				<TBODY ID="TableData" CLASS="dataContainer" align="center">
					<%for(File f : files) { %>
						<TR CLASS="TableDetail1 template">
							<TD width="40%">
								<%=f.getFname() %>
							</TD>
							
							<TD width="20%">
								<%=userDao.getUserByID(f.getUidfrom()) %>
							</TD>
							<TD  width="20%">
								<%=f.getUploaddate() %>
							</TD>
							<TD  width="10%">
								<%=f.getIsread() == 0 ? "未下载" : "已下载" %>
							</TD>
							<TD  width="10%">
								<a href= "DownloadServlet?fid=<%=f.getFid() %>">下载</a> 
							</TD>
						</TR>
					<%} %>
				</TBODY>
			</TABLE>

			<!-- 其他功能超链接 -->
			<DIV ID="TableTail">
				<DIV ID="TableTail_inside" align="center">
					<a href="FileServlet?type=listshow&current=1">首页</a>    第<select id="current" onchange="selectPage()">
					<%
					
					
					for(int i = 0; i < model.getTotalPages(); i++) {
						String selected = "";
						if((i + 1) == model.getCurrentPage())selected = "selected='selected'";
					%>
					
					<option <%=selected %> value="<%=i+1 %>"><%=i+1 %></option>
					<%} %>
					
					</select>页   <a href="FileServlet?type=listshow&current=${pageModel.lastPage }">尾页</a>  共${pageModel.totalPages }页
				</DIV>
			</DIV>
		</DIV>
	</BODY>
	<%@ include file="/WEB-INF/jsp/Public/returnMsg.jspf"%>
</HTML>
