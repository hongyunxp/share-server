<%@ page language="java" import="java.util.*, edu.tjpu.share.po.*, edu.tjpu.share.dao.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改密码</title>
		<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
	</head>
	
	<%
	UserDao userDao = new UserDao();
	GradeDao gradeDao = new GradeDao();
	MajorDao majorDao = new MajorDao();
	ClassDao classDao = new ClassDao();
	
	User user = (User)request.getSession().getAttribute("user");
	Map<String, Integer> map = userDao.getUserGradeMajorClassByUID(user.getUid());
	
	%>
	
	<body>

		<!-- 标题显示 -->
		<div id="Title_bar">
			<div id="Title_bar_Head">
				<div id="Title_Head"></div>
				<div id="Title">
					<!--页面标题-->
					<img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" />
					修改密码
				</div>
				<div id="Title_End"></div>
			</div>
		</div>

		<!--显示表单内容-->
		<div id=MainArea>
			<form action="${pageContext.request.contextPath}/UserServlet?type=modify" method="post" id="form">
				
				<div class="ItemBlock_Title1">
					<!-- 信息说明 -->
					<div class="ItemBlock_Title1">
						<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" />
						修改密码
					</div>
				</div>

				<!-- 表单内容显示 -->
				<div class="ItemBlockBorder">
					<div class="ItemBlock">
						<table cellpadding="0" cellspacing="0" class="mainForm">
						
							<tr>
								<td>
									姓名
								</td>
								<td>
									<input type="text" value="<%=user.getUname() %>" disabled="disabled"></input>
									
								</td>
							</tr>
							
							<tr>
								<td width="100">
									班级信息
								</td>
								<td>
									<input type="text" value="<%=gradeDao.getGradeByID(map.get("Gid")).getGname() %>" disabled="disabled"/>
									<input type="text" value="<%=majorDao.getMajorByID(map.get("Mid")).getMname() %>" disabled="disabled"/>
									<input type="text" value="<%=classDao.getClassByID(map.get("Cid")).getCname() %>" disabled="disabled"/>
								</td>
							</tr>
							
							<tr>
								<td>
									原始密码
								</td>
								<td>
									<input type="password" name="oldpassword" class="{required:true,minlength:5}"></input>
									
								</td>
							</tr>
							
							<tr>
								<td>
									新密码
								</td>
								<td>
									<input type="password" name="newpassword"  id="newpassword" class="{required:true,minlength:5}" ></input>
									
								</td>
							</tr>
							
							<tr>
								<td>
									确认新密码
								</td>
								<td>
									<input type="password" name="repassword" class="{required:true,minlength:5,equalTo:'#newpassword'}"></input>
									
								</td>
							</tr>
							
						</table>
						
					</div>
				</div>
				<DIV ID="InputDetailBar">
					<INPUT TYPE="image" SRC="${pageContext.request.contextPath}/style/images/save.png" />
					<A HREF="javascript:history.go(-1);"><IMG SRC="${pageContext.request.contextPath}/style/images/goBack.png" />
					</A>
				</DIV>
				
			</form>
		</div>

		<div class="Description">
			
		</div>

	</body>
	<%@ include file="/WEB-INF/jsp/Public/returnMsg.jspf"%>
</html>
