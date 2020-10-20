package com.course_type.model;

import java.util.List;

public interface CourseTypeDAO_interface {
	public void insert(CourseTypeVO courseTypeVO);
	public void update(CourseTypeVO courseTypeVO);
	public void delete(String cstypeno);
	public CourseTypeVO findByPrimaryKey(String cstypeno);
	public List<CourseTypeVO> getAll();
}
