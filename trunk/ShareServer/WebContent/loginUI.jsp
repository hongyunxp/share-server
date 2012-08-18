<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Share</title>
   <%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
	<LINK href="${pageContext.request.contextPath}//style/blue/login.css" type=text/css rel=stylesheet>
	<script type="text/javascript">
		if( window.parent != window ){ // 如果在frame中显示，就变成在顶级窗口中显示登录
			window.parent.location.href = window.location.href;
		}

		function submit() {

			document.form1.submit();
			
		}
	</script>
</head>

<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 CLASS=PageBody >


<!-- 显示表单 -->
<form id="form"  action="LogServlet" focus="loginName" method="post">
	<input type="hidden" name="type" value="login"/>
    <div id="CenterAreaBg">
        <div id="CenterArea">
            <div id="LogoImg"><img border="0" src="${pageContext.request.contextPath}//style/blue/images/logo.png" /></div>
            <div id="LoginInfo">
                <table BORDER=0 CELLSPACING=0 CELLPADDING=0 width=100%>
                	<tr>
                		<td colspan="3" align="center"><!-- 显示错误 -->
							<font color="red">${error }</font>
                		</td>
                	</tr>
                    <tr>
                        <td width=60 class="Subject"><img border="0" src="${pageContext.request.contextPath}//style/blue/images/login/userId.gif" /></td>
                        <td>
                        	<input type="text" name="loginName" size="20" tabindex="1"  />
                        </td>
                        <td rowspan="2" style="padding-left:10px;">
                        	<input type="image" tabindex="3" src="${pageContext.request.contextPath}//style/blue/images/login/userLogin_button.gif" />
                        </td>
                         <td rowspan="2" style="padding-left:10px;">
                        	<a href="${pageContext.request.contextPath}//registerUI.jsp"><img tabindex="3" src="${pageContext.request.contextPath}//style/blue/images/login/userReg_button.gif" /></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="Subject"><img border="0" src="${pageContext.request.contextPath}//style/blue/images/login/password.gif" /></td>
                        <td><input type="password" name="password"   size="20" tabindex="2"  /></td>
                    </tr>
                </table>
            </div>
            <div id="CopyRight"><a href="javascript:void(0)">&copy; 2011 版权所有 share</a></div>
        </div>
    </div>
    </form>
</body>

</html>

