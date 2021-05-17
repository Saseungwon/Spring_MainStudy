package com.study.student.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.student.service.IStudentService;
import com.study.student.vo.ResultMessageVO;
import com.study.student.vo.StudentVO;

@Controller
public class StudentController {
	@Inject
	IStudentService studentService;
	
	@RequestMapping("/student/studentList")
	public String studentList(Model model) {
		List<StudentVO> studentList=studentService.getStudentList();
		model.addAttribute("studentList",studentList);
		return "student/studentList";
		
	}
	
	@RequestMapping("/student/studentView")
	public String studentView(Model model,@RequestParam int studentNumber) {
		StudentVO student=studentService.getStudent(studentNumber);
		model.addAttribute("student",student);
		return "student/studentView";
	}
	
	@RequestMapping("/student/studentEdit")
	public String studentEdit(Model model,@RequestParam int studentNumber) {
		StudentVO student=studentService.getStudent(studentNumber);
		model.addAttribute("student",student);
		return "student/studentEdit";
	}
	
	@RequestMapping("/student/studentModify")
	public String studentModify(Model model,StudentVO student) {
		studentService.modifyStudent(student);
		ResultMessageVO resultMessageVO=new ResultMessageVO();
		resultMessageVO.setMessage("수정");
		model.addAttribute("resultMessageVO",resultMessageVO);
		return "message";
	}
	
	@RequestMapping("/student/studentDelete")
	public String studentDelete(Model model,@RequestParam int studentNumber) {
		studentService.removeStudent(studentNumber);
		ResultMessageVO resultMessageVO=new ResultMessageVO();
		resultMessageVO.setMessage("삭제");
		model.addAttribute("resultMessageVO",resultMessageVO);
		return "message";
	}
	
	
	
	@RequestMapping("/student/studentForm")
	public String studentForm(Model model) {
		
		return "student/studentForm"; 
	}
	
	@RequestMapping("/student/studentRegist")
	public String studentRegist(Model model,StudentVO student) {
		studentService.registStudent(student);
		ResultMessageVO resultMessageVO=new ResultMessageVO();
		resultMessageVO.setMessage("등록");
		model.addAttribute("resultMessageVO",resultMessageVO);
		return "message"; 
	}
	
	

}
