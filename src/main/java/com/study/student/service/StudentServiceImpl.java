package com.study.student.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.study.student.dao.IStudentDao;
import com.study.student.vo.StudentVO;

@Service
public class StudentServiceImpl  implements IStudentService{
	@Inject
	IStudentDao studentDao;

	@Override
	public List<StudentVO> getStudentList() {
		
		return studentDao.getStudentList();
	}

	@Override
	public StudentVO getStudent(int studentNumber) {
		return studentDao.getStudent(studentNumber);
	}

	@Override
	public void modifyStudent(StudentVO student) {
		studentDao.updateStudent(student);
		
	}

	@Override
	public void removeStudent(int studentNumber) {
		studentDao.deleteStudent(studentNumber);
		
	}
	
	@Override
	public void registStudent(StudentVO student) {
		studentDao.insertStudent(student);
	}

}
