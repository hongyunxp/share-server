<%@ page language="java" import="java.util.*, edu.tjpu.share.po.*, edu.tjpu.share.dao.*" pageEncoding="UTF-8"%>

<HTML>
	<HEAD>
		<TITLE>文件上传</TITLE>

		<%@ include file="/WEB-INF/jsp/Public/commons.jspf"%>
		
		
		<SCRIPT LANGUAGE="javascript" SRC="${pageContext.request.contextPath}/script/jquery_treeview/jquery.treeview.js"></SCRIPT>
		<LINK TYPE="text/css" REL="stylesheet"
			HREF="${pageContext.request.contextPath}/script/jquery_treeview/jquery.treeview.css" />

		<SCRIPT TYPE="text/javascript">
 		// 选择所有
    	function selectAll(checkedValue){
    		$("input[type=checkbox][id=resourceIds]").attr("checked", checkedValue);
    	}
    	
    	$(function(){
    		// 绑定click事件
    		$("[id=resourceIds]").click(function(){
    		
    			// 当前的选中状态
    			var checkedValue = this.checked;
    			
    			// 1，选中一个权限时：
    			if(checkedValue){
				     // a，应该选中他的所有直系上级
				     $(this).parents("li").children("[id=resourceIds]").attr("checked", checkedValue);
				     
				     // b，应该选中他的所有直系下级
				     $(this).siblings("ul").find("[id=resourceIds]").attr("checked", checkedValue);
				}
				// 2，取消选择一个权限时：
				else{
				     // a，应该取消选择 他的所有直系下级
				     $(this).siblings("ul").find("[id=resourceIds]").attr("checked", checkedValue);
				     
				     // b，如果同级的权限都是未选择状态，就应该取消选中他的直接上级，并递归向上做这个操作
				     if( $(this).parent("li").siblings("li").children("[id=resourceIds]:checked").size() == 0 ){
				     	$(this).parent("li").parent("ul").siblings("[id=resourceIds]").attr("checked", checkedValue);
				     	
				     	// 上级的上级也要进行同样的操作（仅当前为第三级时才可以）
				     	if( $(this).parent("li").parent("ul").parent("li").siblings("li").children("[id=resourceIds]:checked").size() == 0 ){
				     		$(this).parent("li").parent("ul").parent("li").parent("ul").siblings("[id=resourceIds]").attr("checked", checkedValue);

				     		if( $(this).parent("li").parent("ul").parent("li").parent("ul").parent("li").siblings("li").children("[id=resourceIds]:checked").size() == 0 ){
					     		$(this).parent("li").parent("ul").parent("li").parent("ul").parent("li").parent("ul").siblings("[id=resourceIds]").attr("checked", checkedValue);
					     	}

					     }
				     }
				}
    		});
    		});
    	
    	$(function(){
    		$("#tree").treeview({

				animated:"fast",
    			collapsed: true
        		});
    		
    	});

    </SCRIPT>
	</HEAD>
	
	<%
	
			GradeDao gradeDao = new GradeDao();
			List<Grade> grades = gradeDao.getAllGradesList();
    		MajorDao majorDao = new MajorDao();
    		ClassDao classDao = new ClassDao();
    		UserDao userDao = new UserDao();
    		
    		User user = (User)request.getSession().getAttribute("user");
	%>
	
	<BODY>

		<!-- 标题显示 -->
		<DIV ID="Title_bar">
			<DIV ID="Title_bar_Head">
				<DIV ID="Title_Head"></DIV>
				<DIV ID="Title">
					<!--页面标题-->
					<IMG BORDER="0" WIDTH="13" HEIGHT="13" SRC="${pageContext.request.contextPath}/style/images/title_arrow.gif" />
					文件上传
				</DIV>
				<DIV ID="Title_End"></DIV>
			</DIV>
		</DIV>

		<!--显示表单内容-->
		<DIV ID=MainArea>
			<form id="form" action="${pageContext.request.contextPath}/UploadServlet"  method="post" enctype="multipart/form-data" >
				
				<DIV CLASS="ItemBlock_Title1">
					<!-- 信息说明 -->
					<DIV CLASS="ItemBlock_Title1">
						<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" />
						文件上传
					</DIV>
				</DIV>
			

				<!-- 表单内容显示 -->
				<DIV CLASS="ItemBlockBorder">
					<DIV CLASS="ItemBlock">
						<TABLE CELLPADDING="0" CELLSPACING="0" CLASS="mainForm">
							<!--表头-->
							<THEAD>
								<TR ALIGN="LEFT" VALIGN="MIDDLE" ID="TableTitle">
									<TD WIDTH="300px" STYLE="padding-left: 7px;">
										<!-- 如果把全选元素的id指定为selectAll，并且有函数selectAll()，就会有错。因为有一种用法：可以直接用id引用元素 -->
										<INPUT TYPE="CHECKBOX" ID="cbSelectAll" onClick="selectAll(this.checked)" />
										<LABEL FOR="cbSelectAll">
											全选用户（选择分享的用户）
										</LABEL>
									</TD>
								</TR>
							</THEAD>

							<!--显示数据列表-->
							<TBODY ID="TableData">
								<TR CLASS="TableDetail1">
									<!-- 显示权限树 -->
									<TD>
										<ul id="tree">
											<% for(Grade g : grades) {%>
												<li id="1" >
													<input type="checkbox" id="resourceIds" value="<%=g.getGid() %>" styleId="cb_<%=g.getGid()%>" />
													<label for="cb_<%=g.getGid() %>">
														<%=g.getGname() %>
													</label>
													<ul>
														<%-- 第二级--%>
														<% 
														List<Major> majors = majorDao.getMajorListByGradeID(g.getGid());
														for( Major m : majors) {%>
															<li id="2" >
																<input type="checkbox" id="resourceIds" value="<%=m.getMid() %>" styleId="cb_<%=m.getMid() %>" />
																<label for="cb_<%=m.getMid() %>">
																	<%=m.getMname() %>
																</label>
																<ul>
																	<%
																	List<Classes> classes = classDao.getClassListByMajorID(m.getMid());
																	for( Classes c : classes) {%>
																		<li id="3"  >
																			<input type="checkbox" id="resourceIds" value="<%=c.getCid() %>" styleId="cb_<%=c.getCid() %>" />
																			<label for="cb_<%=c.getCid() %>">
																				<%=c.getCname() %>
																			</label>
																				<ul>
																			<% 
																			List<User> users = userDao.getUsersByGMCID(userDao.getUserGradeMajorClassRelation(g.getGid(), m.getMid(), c.getCid()));
																			for(User u : users) {
																			if(u.getUid() == user.getUid())continue;
																			%>
																				<li id="4">
																					<input type="checkbox" name="uid" id="resourceIds" value="<%=u.getUid() %>" styleId="cb_<%=u.getUid() %>" />
																					<label for="cb_<%=u.getUid() %>">
																						<%=u.getUname() %>
																					</label>
																				</li>
																				<%} %>
																			</ul>
																		</li>
																	<%} %>
																</ul>
															</li>
														<%} %>
													</ul>
												</li>
											<%} %>
										</ul>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</DIV>
						请选择分享文件	<input type="file" name="file" class="{required:true}"/><br/>
						请填写附带信息<br>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="fileMsg" rows="10" cols="40"></textarea>
						
				</DIV>

				<!-- 表单操作 -->
				<DIV ID="InputDetailBar">
					<INPUT TYPE="image" SRC="${pageContext.request.contextPath}/style/images/save.png" />
					<A HREF="javascript:history.go(-1);"><IMG SRC="${pageContext.request.contextPath}/style/images/goBack.png" />
					</A>
				</DIV>
			</form>
		</DIV>

		<DIV CLASS="Description">
			
		</DIV>

	</BODY>
	
	<%@ include file="/WEB-INF/jsp/Public/returnMsg.jspf"%>
</HTML>
