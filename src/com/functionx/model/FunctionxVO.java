package com.functionx.model;

import java.io.Serializable;
import java.sql.Date;

public class FunctionxVO implements Serializable{
	private String funcno;
	private String funcname;
	private String funcifo;
	private Date funclmod;
	
	
	public String getFuncno() {
		return funcno;
	}
	public void setFuncno(String funcno) {
		this.funcno = funcno;
	}
	public String getFuncname() {
		return funcname;
	}
	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}
	public String getFuncifo() {
		return funcifo;
	}
	public void setFuncifo(String funcifo) {
		this.funcifo = funcifo;
	}
	public Date getFunclmod() {
		return funclmod;
	}
	public void setFunclmod(Date funclmod) {
		this.funclmod = funclmod;
	}
	
}
