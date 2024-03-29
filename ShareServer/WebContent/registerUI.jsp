<%@ page language="java" import="java.util.*,edu.tjpu.share.util.*, edu.tjpu.share.po.*, edu.tjpu.share.dao.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户注册</title>
		
		<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
		
	</head>

	<script type="text/javascript">
	
	function changeimg()   
	  {   
	  var cn = document.getElementById("cn");   
	  cn.src="image.jsp?"+Math.random();//随机生成一个数字，让图片缓冲区认为不是同一个缓冲区    
	  } 

	
	function getMajor() {
		//alert("ds");
		var gradeID = $("#grade");
			
		//alert(gradeID);
			var select = document.getElementById("major").options.length;
			//alert(select);
			for ( var j = 1; j < select; j++) {
				//alert(j);
<%--			 $("#major option[index='"+ j +"']").remove(); --%>
			 $("#major option:last").remove();
			}

		
		$.getJSON("AjaxServlet",{type : "major" , ID : gradeID.val()},function callback(data){
				var majors = data.datas; 
				
				for ( var i = 0; i < majors.length; i++) {
					//alert(majors[i].id);
					$("#major").append("<option value='" + majors[i].id  + "'>" + majors[i].name + "</option>");
				}
			}

		);
	}

	function getClass() {
		
		//alert("ds");
		//alert(gradeID);
			var select = document.getElementById("class").options.length;
			//alert(select);
			for ( var j = 1; j < select; j++) {
				//alert(j);
<%--			 $("#major option[index='"+ j +"']").remove(); --%>
			 $("#class option:last").remove();
			}
		$.getJSON("AjaxServlet",{type : "class" , ID : $("#major").val()},function callback(data){
				var classes = data.datas; 
				
				for ( var i = 0; i < classes.length; i++) {
					//alert(majors[i].id);
					$("#class").append("<option value='" +classes[i].id  + "'>" + classes[i].name + "</option>");
				}
			}

		);
		
	}

function getUser() {
		
		//alert("ds");
		var loginName = $("#loginName").val();

		loginName = $.trim(loginName);
		if(loginName.length == 0 )return;
		
		$.post("AjaxServlet",{type : "user" , loginName : $("#loginName").val()},function callback(data){
			
			$("#result").html(data);
			}

		);
		
	}
		
	</script>
	<body>
	
    
   
	
	

		<!-- 标题显示 -->
		<div id="Title_bar">
			<div id="Title_bar_Head">
				<div id="Title_Head"></div>
				<div id="Title">
					<!--页面标题-->
					<img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" />
					用户注册
				</div>
				<div id="Title_End"></div>
			</div>
		</div>

		<!--显示表单内容-->
		<div id=MainArea>
			<form action="RegisterServlet" method="post" id="form">
			<input type="hidden" name="type" value="add"/>
				
				<div class="ItemBlock_Title1">
					<!-- 信息说明 -->
					<div class="ItemBlock_Title1">
						<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" />
						用户信息
					</div>
				</div>

				<!-- 表单内容显示 -->
				<div class="ItemBlockBorder">
					<div class="ItemBlock">
						<table cellpadding="0" cellspacing="0" class="mainForm">
							<tr>
								<td width="100">
									班级信息
								</td>
								
								<%
									GradeDao gradeDao = new GradeDao();
								List<Grade> list = gradeDao.getAllGradesList();
								%>
								<td>
									<select name="grade" id="grade" onChange="getMajor()"  class="{required:true}">
										<option value="" selected="selected">请选择学院</option>
										
										
										
										<%for(Grade g : list) { %>
											<option value="<%=g.getGid() %>"><%=g.getGname() %></option>
									<%} %>
									</select>
									<select name="major" id="major" onChange="getClass()"  class="{required:true}">
										<option value="">请选择专业</option>
										
									</select>
									<select name="class" id="class" class="{required:true}">
										<option value="">请选择班级</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									用户名
								</td>
								<td>
									<input type="text" id="loginName" class="required" name="loginName" onBlur="getUser()"></input><span id="result"></span>
									* （用户名唯一）
								</td>
							</tr>
							<tr>
								<td>
									密码
								</td>
								<td>
									<input type="password" name="password"  id="password" class="{required:true,minlength:5}" ></input>
									
								</td>
							</tr>
							
							<tr>
								<td>
									确认密码
								</td>
								<td>
									<input type="password" name="repassword" class="{required:true,minlength:5,equalTo:'#password'}"></input>
									
								</td>
							</tr>
							
							<tr>
					            <td>验证码：</td>
					            <td align="left"><img border=0 src="image.jsp" name="checkunmimg" id="cn"/><a href="javascript:changeimg()">看不清？换一换</a></td>
					            <td><div class="Validform_checktip"></div></td>
       						 </tr>
							
							 <tr>
					           	<td>输入验证码：</td>
					          	<td align="left"><input type="text"  name="checkunm" class="{required:true,minlength:4}"/></td>
					           	<td><div class="Validform_checktip"></div></td>
       						 </tr>
							
						</table>
						<DIV ID="InputDetailBar">
					<INPUT TYPE="image" SRC="${pageContext.request.contextPath}/style/images/save.png" />
						<A HREF="loginUI.jsp"><IMG SRC="${pageContext.request.contextPath}/style/images/goBack.png" />
					</A>
					</DIV>
					</div>
				</div>

			</form>
		</div>

	
		
	</body>
	
	<%@ include file="/WEB-INF/jsp/Public/returnMsg.jspf"%>
	
</html>
