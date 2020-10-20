package com.emp_authority.model;

import java.io.Serializable;


public class EmpAuthorityVO implements Serializable{
		private String empno;
		private String funcno;
		
		public String getEmpno() {
			return empno;
		}
		public void setEmpno(String empno) {
			this.empno = empno;
		}
		public String getFuncno() {
			return funcno;
		}
		public void setFuncno(String funcno) {
			this.funcno = funcno;
		}

}
