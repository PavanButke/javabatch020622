package com.zensar.springbootdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.zensar.springbootdemo.dto.StudentDto;
import com.zensar.springbootdemo.entity.Student;
import com.zensar.springbootdemo.exceptions.StudentNotFoundException;
import com.zensar.springbootdemo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public StudentDto getStudent(int studentId) {
		Student student = studentRepository.findById(studentId).orElse(null);

		// StudentDto dto = mapToDto(student);
		if (student != null) {

			return modelMapper.map(student, StudentDto.class);
		}

		return null;

		// return dto;
	}

	@Override
	public List<StudentDto> getAllStudents(int pageNumber, int pageSize) {

		// List<Student> listOfStudents = studentRepository.findAll();
		List<StudentDto> listOfStudentDto = new ArrayList<StudentDto>();

		// Page<Student> findAll = studentRepository.findAll(PageRequest.of(pageNumber,
		// pageSize,Sort.by(Direction.DESC,"studentName")));

		Page<Student> findAll = studentRepository
				.findAll(PageRequest.of(pageNumber, pageSize, Direction.ASC, "studentName"));

		List<Student> content = findAll.getContent();

		for (Student student : content) {
			// listOfStudentDto.add(mapToDto(student));
			listOfStudentDto.add(modelMapper.map(student, StudentDto.class));
		}

		return listOfStudentDto;
	}

	@Override
	public StudentDto insertStudent(StudentDto studentDto) {

		// Student student = mapToEntity(studentDto);

		Student student = modelMapper.map(studentDto, Student.class);

		Student insertedStudent = studentRepository.save(student);

		// StudentDto mapToDto = mapToDto(insertedStudent);

		return modelMapper.map(insertedStudent, StudentDto.class);

		// return mapToDto;

	}

	@Override
	public void updateStudent(int studentId, StudentDto studentDto) {

		// Student student = mapToEntity(studentDto);

		Student student = modelMapper.map(studentDto, Student.class);

		studentRepository.save(student);

	}

	@Override
	public void deleteStudent(int studentId) {
		studentRepository.deleteById(studentId);

	}

	@Override
	public List<StudentDto> getByStudentName(String studentName) {

		List<StudentDto> studentDtos = new ArrayList<>();

		List<Student> students = studentRepository.test(studentName);

		for (Student student : students)
			studentDtos.add(modelMapper.map(student, StudentDto.class));

		return studentDtos;
	}

	@Override
	public List<StudentDto> findByStudentNameAndStudentAge(String studentName, int age) {

		List<StudentDto> studentDtos = new ArrayList<>();

		List<Student> students = studentRepository.test1(studentName, age);

		for (Student student : students)
			studentDtos.add(modelMapper.map(student, StudentDto.class));

		return studentDtos;
	}

	/*
	 * public StudentDto mapToDto(Student student) {
	 * 
	 * StudentDto dto = new StudentDto(); dto.setStudentId(student.getStudentId());
	 * dto.setStudentName(student.getStudentName());
	 * dto.setStudentAge(student.getStudentAge());
	 * 
	 * return dto; }
	 * 
	 * public Student mapToEntity(StudentDto studentDto) {
	 * 
	 * Student student = new Student();
	 * student.setStudentId(studentDto.getStudentId());
	 * student.setStudentName(studentDto.getStudentName());
	 * student.setStudentAge(studentDto.getStudentAge());
	 * 
	 * return student;
	 * 
	 * }
	 */

}
