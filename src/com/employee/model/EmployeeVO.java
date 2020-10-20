package com.employee.model;

import java.io.Serializable;
import java.sql.Date;

public class EmployeeVO implements Serializable {
	private String empno;
	private String empacc;
	private String emppwd;
	private String empname;
	private Integer empsalary;
	private Date hiredate;
	private String empemail;
	private Integer empdelete;
	
	
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getEmpacc() {
		return empacc;
	}
	public void setEmpacc(String empacc) {
		this.empacc = empacc;
	}
	public String getEmppwd() {
		return emppwd;
	}
	public void setEmppwd(String emppwd) {
		this.emppwd = emppwd;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public Integer getEmpsalary() {
		return empsalary;
	}
	public void setEmpsalary(Integer empsalary) {
		this.empsalary = empsalary;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public String getEmpemail() {
		return empemail;
	}
	public void setEmpemail(String empemail) {
		this.empemail = empemail;
	}
	public Integer getEmpdelete() {
		return empdelete;
	}
	public void setEmpdelete(Integer empdelete) {
		this.empdelete = empdelete;
	}
}
