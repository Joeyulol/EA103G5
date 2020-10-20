package com.functionx.model;

import java.util.List;

import com.emp_authority.model.EmpAuthorityVO;
import com.employee.model.EmployeeVO;

public interface FunctionxDAO_interface {
	public void insert(FunctionxVO functionxVO);
	public void update(FunctionxVO functionxVO);
	public void delete(String funcno);
	public FunctionxVO findByPrimaryKey(String funcno);
	public List<FunctionxVO> getAll();
	public void insertWithFunc(FunctionxVO functionxVO,  List<EmpAuthorityVO> list);
}
