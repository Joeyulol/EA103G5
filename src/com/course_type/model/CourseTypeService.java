package com.course_type.model;

import java.util.List;

import com.employee.model.EmployeeVO;

public class CourseTypeService {

	private CourseTypeDAO_interface dao;

//	public CourseTypeService() {
//		dao = new CourseTypeJDBCDAO();
//	}

	public CourseTypeService() {
		dao = new CourseTypeJNDIDAO();
	}

	public CourseTypeVO addCourseType(String cstypename) {
		CourseTypeVO courseTypeVO = new CourseTypeVO();

		courseTypeVO.setCstypename(cstypename);
		dao.insert(courseTypeVO);
		return courseTypeVO;
	}

	public CourseTypeVO updateCoursType(String cstypeno, String cstypename) {
		CourseTypeVO courseTypeVO = new CourseTypeVO();

		courseTypeVO.setCstypeno(cstypeno);
		courseTypeVO.setCstypename(cstypename);
		dao.update(courseTypeVO);
		return courseTypeVO;
	}

	public CourseTypeVO getCoursType(String cstypeno) {
		return dao.findByPrimaryKey(cstypeno);
	}

	public List<CourseTypeVO> getAll() {
		return dao.getAll();
	}
}
