package com.course_type.model;

import java.io.Serializable;

public class CourseTypeVO implements Serializable {
	private String cstypeno;
	private String cstypename;
	
	public String getCstypeno() {
		return cstypeno;
	}
	public void setCstypeno(String cstypeno) {
		this.cstypeno = cstypeno;
	}
	public String getCstypename() {
		return cstypename;
	}
	public void setCstypename(String cstypename) {
		this.cstypename = cstypename;
	}
}
