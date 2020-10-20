package com.emp_authority.model;

import java.util.List;




public interface EmpAuthorityDAO_interface {
	
	public void insert(EmpAuthorityVO empAuthorityVO);	
	public void delete(String empno);
	public List<EmpAuthorityVO> getAll();
	public List<EmpAuthorityVO> findByEmp(String empno);
	public void insert2 (EmpAuthorityVO empAuthorityVO , java.sql.Connection con);

}
