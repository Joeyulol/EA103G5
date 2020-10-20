package com.employee.model;

import java.util.List;

public class EmployeeService {
	
	private EmployeeDAO_interface dao;

//	public EmployeeService() {
//		dao = new EmployeeJDBCDAO();
//	}
//	
	public EmployeeService() {
		dao = new EmployeeJNDIDAO();
	}
//	
	//增加員工
	public EmployeeVO addEmp(String empacc, String emppwd, String empname, Integer empsalary
			, java.sql.Date hiredate, String empemail) {
		
		EmployeeVO employeeVO = new EmployeeVO();
		
		employeeVO.setEmpacc(empacc);
		employeeVO.setEmppwd(emppwd);//先暫時寫這樣
		employeeVO.setEmpname(empname);
		employeeVO.setEmpsalary(empsalary);
		employeeVO.setHiredate(hiredate);
		employeeVO.setEmpemail(empemail);
		employeeVO.setEmpno(dao.insertWithEmp(employeeVO));
		return employeeVO;	
	}
	//更新員工資料
	 public EmployeeVO updateEmp(String empno,String empacc, String emppwd, String empname, Integer empsalary
			 , java.sql.Date hiredate, String empemail, Integer empdelete) {
		 
		 EmployeeVO employeeVO = new EmployeeVO();
		 employeeVO.setEmpno(empno);
		 employeeVO.setEmpacc(empacc); 
		 employeeVO.setEmppwd(emppwd);
		 employeeVO.setEmpname(empname);
		 employeeVO.setEmpsalary(empsalary);
		 employeeVO.setHiredate(hiredate);
		 employeeVO.setEmpemail(empemail);
		 employeeVO.setEmpdelete(empdelete);
		 dao.update(employeeVO);
		 return employeeVO;		 
	 }

	 
	 //查詢
	 public EmployeeVO getEmp(String empno) {
		 return dao.findByPrimaryKey(empno);
	 }
	 
	 public List<EmployeeVO> getAll(){
		 return dao.getAll();
	 }
	 
	 public EmployeeVO logIn(String empacc, String emppwd) {
		 return dao.logIn(empacc, emppwd);
	 }
	 
	 public EmployeeVO getEmpAcc(String empacc) {
		 return dao.getOneAcc(empacc);
	 }
}
