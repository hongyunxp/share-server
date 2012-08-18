<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Share导航菜单</title>
		<link type="text/css" rel="stylesheet" href="style/blue/menu.css" />
		<script type="text/javascript">
			function menuClick(topMenuDiv){
				$(".MenuLevel2").not($(topMenuDiv).siblings("ul")).hide();
				$(topMenuDiv).siblings("ul").toggle();
			}
		</script>
	</head>
	<body style="margin: 0">
		<div id="Menu">
			<ul id="MenuUl">
				<%-- 显示顶级菜单 --%>
				
						<li class="level1">
							<div class="level1Style" onClick="menuClick(this)">
								<img src="style/images/MenuIcon/address.gif" class="Icon"/>
								<label style="CURSOR:hand">用户操作</label>
							</div>

							<%-- 显示二级菜单 --%>
							<ul style="display: none;" class="MenuLevel2">
								
										<li class="level2">
											<div class="level2Style">
												<img src="style/images/MenuIcon/menu_arrow_single.gif" />
												<a target="desktop" href="userpages/modifyPwd.jsp" > 修改密码</a>
											</div>
										</li>
										
										<li class="level2">
											<div class="level2Style">
												<img src="style/images/MenuIcon/menu_arrow_single.gif" />
												<a target="desktop" href="UserServlet?type=list&current=1" > 用户列表</a>
											</div>
										</li>
										
										<li class="level2">
											<div class="level2Style">
												<img src="style/images/MenuIcon/menu_arrow_single.gif" />
												<a target="desktop" href="userpages/modifyAvatar.jsp" > 修改头像</a>
											</div>
										</li>
									
							</ul>
						</li>
                        
                        <%-- 显示顶级菜单 --%>
				
						<li class="level1">
							<div class="level1Style" onClick="menuClick(this)">
								<img src="style/images/MenuIcon/FUNC20070.gif" class="Icon" />
								<label style="CURSOR:hand">文件操作</label>
							</div>
                            

							<%-- 显示二级菜单 --%>
							<ul style="display: none;" class="MenuLevel2">
								
										<li class="level2">
											<div class="level2Style">
												<img src="style/images/MenuIcon/menu_arrow_single.gif" />
												<a target="desktop" href="filepages/uploadFile.jsp" > 文件上传</a>
											</div>
										</li>
                                        
                                        <li class="level2">
											<div class="level2Style">
												<img src="style/images/MenuIcon/menu_arrow_single.gif" />
												<a target="desktop" href="FileServlet?type=listshow&current=1" > 文件下载</a>
											</div>
										</li>
										
										<li class="level2">
											<div class="level2Style">
												<img src="style/images/MenuIcon/menu_arrow_single.gif" />
												<a target="desktop" href="FileServlet?type=showMyFile&current=1" > 我的上传</a>
											</div>
										</li>
									
							</ul>
						</li>
					
			</ul>
		</div>
	</body>
</html>
