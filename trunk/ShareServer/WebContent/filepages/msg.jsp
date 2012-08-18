<%@ page language="java" import="java.util.*,edu.tjpu.share.util.*, edu.tjpu.share.po.*, edu.tjpu.share.dao.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>附带信息</TITLE>
		<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
		
		<script type="text/javascript">
		
		</script>
	</HEAD>
	
	<%
	int fid = Integer.parseInt(request.getParameter("fid"));
	
	%>
	
	<BODY>

		<DIV ID="Title_bar">
			<DIV ID="Title_bar_Head">
				<DIV ID="Title_Head"></DIV>
				<DIV ID="Title">
					<!--页面标题-->
					<IMG BORDER="0" WIDTH="13" HEIGHT="13" SRC="${pageContext.request.contextPath}/style/images/title_arrow.gif" />
					消息内容
				</DIV>
				<DIV ID="Title_End"></DIV>
			</DIV>
		</DIV>

		<DIV ID="MainArea" align="center">
		
			<textarea rows="10" cols="40" readonly="readonly"><%=new NotifyDao().getMsgByFID(fid) %></textarea><br>
			<A HREF="javascript:history.go(-1);"><IMG SRC="${pageContext.request.contextPath}/style/images/goBack.png" />

			<!-- 其他功能超链接 -->
			
		</DIV>
	</BODY>
	<%@ include file="/WEB-INF/jsp/Public/returnMsg.jspf"%>
</HTML>
