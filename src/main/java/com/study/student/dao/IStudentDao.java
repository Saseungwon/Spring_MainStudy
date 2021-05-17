package com.study.student.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.student.vo.StudentVO;

@Mapper
public interface IStudentDao {
	
	public List<StudentVO> getStudentList();
	public StudentVO getStudent(int studentNumber);
	public int updateStudent(StudentVO student);
	public int deleteStudent(int studentNumber);
	public int insertStudent(StudentVO student);
	

}
