package com.employee.model;

import java.util.List;

import com.emp_authority.model.EmpAuthorityVO;
import com.functionx.model.FunctionxVO;

public interface EmployeeDAO_interface {
	public void insert(EmployeeVO employeeVO);
	public void update(EmployeeVO employeeVO);
	public void updateStatus(EmployeeVO employeeVO);
	public void delete(String empno);
	public EmployeeVO findByPrimaryKey(String empno);
	public EmployeeVO logIn(String empacc, String emppwd);
	public List<EmployeeVO> getAll();
	public String insertWithEmp(EmployeeVO employeeVO);
	public EmployeeVO getOneAcc(String empacc);
}
