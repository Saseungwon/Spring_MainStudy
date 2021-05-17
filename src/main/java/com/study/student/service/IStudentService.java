package com.study.student.service;

import java.util.List;

import com.study.student.vo.StudentVO;

public interface IStudentService {
	
	public List<StudentVO> getStudentList();
	public StudentVO getStudent(int studentNumber);
	public void modifyStudent(StudentVO student);
	public void removeStudent(int studentNumber);
	public void registStudent(StudentVO student);
	
	
}
