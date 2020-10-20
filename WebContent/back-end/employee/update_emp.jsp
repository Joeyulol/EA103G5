<%@ page contentType="text/html ; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.employee.model.*"%>
<%@ page import="com.functionx.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	  	EmployeeVO employeeVO = (EmployeeVO) request.getAttribute("employeeVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


<title>更新員工資料</title>
<style>
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
</head>
<body>
<c:if test="${not empty errMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
	<form method="post" ACTION="<%=request.getContextPath() %>/employee/employee.do" >
	<table>
			<tr>
				<th>員工編號</th>
				<th><label><%=employeeVO.getEmpno() %></label></th>							
			</tr>
			<tr>
				<th>員工帳號</th>
				<th><input type="text" name="empacc" placeholder="請輸入員工帳號" autocomplete="off" value="<%= employeeVO.getEmpacc()%>" /></th>
			</tr>			
			<tr>
				<th>員工密碼</th>
				<th><input type="password" 	name="emppwd" placeholder="請輸入員工密碼" autocomplete="off"value="<%= employeeVO.getEmppwd()%>"></th>
			</tr>
			<tr>
				<th>員工姓名</th>
				<th><input type="text"  name="empname" placeholder="請輸入員工姓名" autocomplete="off" value="<%=employeeVO.getEmpname() %>" /></th>
			</tr>
			<tr>
				<th>員工薪水</th>
				<th><input type="text"  name="empsalary" placeholder="請輸入員工薪水" autocomplete="off" value="<%=employeeVO.getEmpsalary() %>" /></th>
			</tr>
			<tr>
				<th>員工到職日期 </th>
				<th><input type="text"  id="f_date1" name="hiredate" value="<%=employeeVO.getHiredate() %>"></th>
			</tr>
			<tr>
				<th>員工Email</th>
				<th> <input type="email" name="empemail" placeholder="請輸入email" value="<%=employeeVO.getEmpemail() %>" /></th>
			</tr>
			<jsp:useBean id="empSvc" scope="page" class="com.employee.model.EmployeeService" />
			<tr>
				<th>員工狀態</th>
				<th>
					<select name="empdelete">
						<option value = 0 <%=(employeeVO.getEmpdelete()==0) ? "selected": "" %>>啟用
						<option value = 1 <%=(employeeVO.getEmpdelete()==1) ? "selected": "" %>>停用
					</select>
				</th>
				<th>
					<input type="hidden" name="action" value="empupdate">
					<input type="hidden" name="empno" value="<%=employeeVO.getEmpno() %>">
					<input type="submit" value="送出修改">
				</th>
			</tr>
	</table>
	</form>
</body>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>
<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=employeeVO.getHiredate()%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
</script>
</html>