<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>TopMenu</title>
	<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
	<LINK href="${pageContext.request.contextPath}//style/blue/top.css" type=text/css rel=stylesheet>
	
	<script type="text/javascript">
		//思路： 定时做（每5分钟执行一次）：从服务器端取出我的任务数量，然后更新相关的显示
	</script>
	<style type="text/css">
		#messageArea{
			color: white;
			font-size: 14px;
			font-weight: bold;
		}
	</style>
</head>

<body CLASS=PageBody leftmargin=0 topmargin=0 marginwidth=0 marginheight=0>
	
	<div id="Head1">
		<div id="Logo">
        	<iframe name="autoRefashion" src="" width="0" height="0"></iframe>
			<a id="msgLink" href="javascript:void(0)"></a>
            <font color="#0000CC" style="color:#F1F9FE; font-size:28px; font-family:Arial Black, Arial">Share</font> 
			<!--<img border="0" src="css/blue/images/logo.png" />-->
        </div>
		<div id="Head1Right">
			<div id="Head1Right_UserName">
                <img border="0" width="13" height="14" src="${pageContext.request.contextPath}//style/images/top/user.gif" /> 您好，<b>${user.uname }</b>
			</div>
			
		</div>
        <div id="Head1Right_SystemButton">
            <a href="LogServlet?type=logout" target="_parent">
                <img width="78" height="20" alt="退出系统" src="${pageContext.request.contextPath}//style/blue/images/top/logout.gif" />
            </a>
        </div>
       
	</div>
    
    <div id="Head2">
        
        <div id="Head2_FunctionList" style="text-align: left">
        	
        </div>
    </div>

</body>

</html>
