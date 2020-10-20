<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.employee.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<title>選擇查詢</title>
</head>
<jsp:useBean id="empSvc" class="com.employee.model.EmployeeService" />
<body>
<!-- 錯誤驗證尚未完成 -->
	<c:if test="${not empty errMsgs}">  
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<table>
		<tr>
			<th>資料查詢</th>
			<th>輸入員工編號</th>
			<th>選擇員工編號</th>
			<th>選擇員工姓名</th>
			<th>新增員工</th>
		</tr>
		<tr>
			<th>
				<a href="<%=request.getContextPath() %>/back-end/employee/allemplist.jsp">查看全部員工</a>
			</th>
			<th><form method="post" action="<%=request.getContextPath() %>/employee/employee.do">
					<input type="text" name="empno"> 
					<input type="hidden" name="action" value="getone_show"> 
					<input type="submit" value="查詢">
				</form></th>
			<th><form method="post" action="<%=request.getContextPath() %>/employee/employee.do">
					<select size="1" name="empno">
						<c:forEach var="employeeVO" items="${empSvc.all	}">
							<option value="${employeeVO.empno}">${employeeVO.empno}
						</c:forEach>	
					</select> <input type="hidden" name="action" value="getone_show"> 
							  <input type="submit" value="查詢">
				</form>
			</th>
			<th><form method="post" action="<%=request.getContextPath() %>/employee/employee.do">
				<select size="1" name="empno">
						<c:forEach var="employeeVO" items="${empSvc.all}">
							<option value="${employeeVO.empno}">${employeeVO.empname}
						</c:forEach>
					</select> <input type="hidden" name="action" value="getone_show"> 
							  <input type="submit" value="查詢">
				</form>
			</th>
			<th><a href="<%=request.getContextPath() %>/back-end/employee/addEmployee.jsp"><button>新增</button></a></th>
		</tr>
	</table>
</body>
</html>