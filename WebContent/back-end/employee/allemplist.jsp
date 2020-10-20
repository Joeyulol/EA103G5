<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.employee.model.*"%>
<%
    EmployeeService empSvc = new EmployeeService();
	List<EmployeeVO> list = empSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">
table {
	border-collapse: collapse
}

td, th {
	padding: 5px 20px;
	border: 1px solid #ccc
}

th {
	background-color: #fce8e2
}
</style>
<title>員工列表</title>
</head>
<body>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
	<table>
		<tr>
			<th>員工編號</th>
			<th>員工帳號</th>
			<th>員工姓名</th>
			<th>薪資</th>
			<th>到職日期</th>
			<th>email</th>
			<th>狀態</th>
			<th>修改</th>
			<th><a href="<%=request.getContextPath() %>/back-end/employee/select_emp.jsp">主頁</a></th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="employeeVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${employeeVO.empno}</td>
				<td>${employeeVO.empacc}</td>
				<td>${employeeVO.empname}</td>
				<td>${employeeVO.empsalary}</td>
				<td>${employeeVO.hiredate}</td>
				<td>${employeeVO.empemail}</td>
				<td>${(employeeVO.empdelete==0)?'啟用':'停用'}</td>
				<td>
			  		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/employee/employee.do" >
			     		<input type="submit" value="修改">
			     		<input type="hidden" name="empno"  value="${employeeVO.empno}">
			     		<input type="hidden" name="action"	value="forupdate">
			     	</FORM>
				</td>
				<td>
			  		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/employee/employee.do" >
			     		<input type="submit" value="修改">
			     		<input type="hidden" name="empno"  value="${employeeVO.empno}">
			     		<input type="hidden" name="action"	value="authority">
			     	</FORM>
				</td>
				
			</tr>
			
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>
</body>
</html>