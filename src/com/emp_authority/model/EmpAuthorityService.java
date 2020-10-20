package com.emp_authority.model;

import java.util.List;



public class EmpAuthorityService {

	EmpAuthorityDAO_interface dao;

//	public EmpAuthorityService() {
//		dao = new EmpAuthorityJDBCDAO();
//	}

	public EmpAuthorityService() {
		dao = new EmpAuthorityJNDIDAO();
	}

	// 新增
	public EmpAuthorityVO addEmpAuth(String empno, String funcno) {

		EmpAuthorityVO empAuthorityVO = new EmpAuthorityVO();

		empAuthorityVO.setEmpno(empno);
		empAuthorityVO.setFuncno(funcno);
		dao.insert(empAuthorityVO);

		return empAuthorityVO;
	}

	public void deleteEmpAuth(String empno) {
		dao.delete(empno);
	}
	
	public List<EmpAuthorityVO> getAll(){
		return dao.getAll();
	}
	
	 public List<EmpAuthorityVO> findByEmp(String empno) {
		 return dao.findByEmp(empno);
	 }
	 

}
