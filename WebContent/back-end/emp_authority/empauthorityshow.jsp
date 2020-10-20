<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.employee.model.*"%>
<%@ page import="com.emp_authority.model.*"%>
<%@ page import="com.functionx.model.*"%>
<%
 EmpAuthorityVO empAuthorityVO = (EmpAuthorityVO) request.getAttribute("empAuthorityVO");

 

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
<title>單一會員</title>
</head>
<body>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<table>
		<tr>
			<th>員工編號</th>
			<th>員工帳號</th>
			<th>員工姓名</th>
			<th>薪資</th>
			<th>雇用日期</th>
			<th>email</th>
			<th>狀態</th>
			<th><a href="<%=request.getContextPath() %>/back-end/employee/select_emp.jsp">主頁</a></th>
		</tr>
		<tr>
			<td><%=empAuthorityVO.getEmpno()%></td>
			<td><%=empAuthorityVO.getFuncno()%></td>
			
		</tr>
	</table>
</body>
</html>