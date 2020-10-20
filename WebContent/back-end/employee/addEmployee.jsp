<%@ page contentType="text/html ; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.employee.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	EmployeeVO employeeVO = (EmployeeVO) request.getAttribute("EmployeeVO");
%>
<%
	java.sql.Date hiredate = null;
	try {
		hiredate = employeeVO.getHiredate();
	} catch (Exception e) {
		hiredate = new java.sql.Date(System.currentTimeMillis());
	}
%>
<!DOCTYPE html>


<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
	integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"
	integrity="sha384-u/bQvRA/1bobcXlcEYpsEdFVK/vJs3+T+nXLsBYJthmdBuavHvAW6UsmqO2Gd/F9"
	crossorigin="anonymous"></script>

<style type="text/css">
body {
	background-color: #16dbdb;
}
</style>
<title>新增員工</title>
</head>

<body>
<%-- 	<c:if test="${not empty errMsgs}"> --%>
<!-- 		<font style="color: red">請修正以下錯誤:</font> -->
<!-- 		<ul> -->
<%-- 			<c:forEach var="message" items="${errMsgs}"> --%>
<%-- 				<li style="color: red">${message}</li> --%>
<%-- 			</c:forEach> --%>
<!-- 		</ul> -->
<%-- 	</c:if> --%>

	<form method="post" ACTION="<%=request.getContextPath() %>/employee/employee.do" name="form1">
		<div class="form-row">

			<div class="form-group col-md-6">
				<div style="color: red">${accerr}</div>
				<div style="color: red">${accerr2}</div>
				<label>員工帳號</label> <input type="text" class="form-control"
					name="empacc" placeholder="請輸入員工帳號" autocomplete="off"
					value="${param.empacc}" />
			</div>
			<div></div>
			<div class="form-group col-md-6">
				<label>員工密碼</label> <input type="password" class="form-control"
					name="emppwd" placeholder="請輸入員工密碼" autocomplete="off">
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-6">
				<div style="color: red">${nameerr}</div>
				<div style="color: red">${nameerr2}</div>
				<label>員工姓名</label> <input type="text" class="form-control"
					name="empname" placeholder="請輸入員工姓名" autocomplete="off"
					value="${param.empname}" />
			</div>
			<div class="form-group col-md-6">
				<div style="color: red">${salaryerr}</div>
				<div style="color: red">${salaryerr2}</div>
				<label>員工薪水</label> <input type="text" class="form-control"
					name="empsalary" placeholder="請輸入員工薪水" autocomplete="off"
					value="${empsalary}" />
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-6">
			<div style="color: red">${errhiredate}</div>
			<div style="color: red">${latehiredate}</div>
				<label>員工到職日期</label> <input type="TEXT" class="form-control"
					id="f_date1" name="hiredate" value="${param.hiredate}">
			</div>
			<div class="form-group col-md-6">
			<div style="color: red">${errhiredate}</div>
				<label>員工Email</label> <input type="email" class="form-control"
					name="empemail" placeholder="請輸入email" value="${param.empemail}" />
			</div>
		</div>
		<input type="hidden" name="action" value="insert"> 
		<input type="submit" class="btn btn-primary" value="新增員工">
	</form>

</body>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '${param.hiredate}', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
//            minDate:               '-1970-01-01', // 去除今日(不含)之前
           maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
 </script>
</html>