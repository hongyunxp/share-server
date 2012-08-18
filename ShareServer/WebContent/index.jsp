<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Share</title>
		<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
		<script type="text/javascript" src="${pageContext.request.contextPath}//script/jquery_treeview/jquery.cookie.js"></script>
		<script type="text/javascript">
	</script>
	</head>

	<frameset rows="100,*,25" framespacing=0 border=0 frameborder=0>
		<frame noresize target="contents" name="TopMenu" scrolling="no" src="top.jsp"/>
		<frameset cols="180,*" id="resize">
			<frame noresize target="main" name="menu" scrolling="yes" src="left.jsp"/>
			<frame noresize name="desktop" scrolling="yes" src="right.jsp"/>
		</frameset>
		<frame noresize name="status_bar" scrolling="no" src="bottom.jsp"/>
	</frameset>

	<noframes>
		<body>
		</body>
	</noframes>
</html>


