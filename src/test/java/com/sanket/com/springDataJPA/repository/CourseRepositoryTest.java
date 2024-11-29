package com.sanket.com.springDataJPA.repository;

import com.sanket.com.springDataJPA.entity.Course;
import com.sanket.com.springDataJPA.entity.Guardian;
import com.sanket.com.springDataJPA.entity.Student;
import com.sanket.com.springDataJPA.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void findCourseAndCourseMaterialInfo() {
        List<Course> courses = courseRepository.findAll();
        System.out.println(courses);
    }

    @Test
    public void saveCourseWithTeacher() {
        Teacher teacher = Teacher.builder()
                .firstName("Naveen")
                .lastName("Khunteta")
                .build();

        Course course = Course
                .builder()
                .title("Python Automation using Selenium")
                .teacher(teacher)
                .credit(6)
                .build();

        courseRepository.save(course);
    }

    @Test
    public void findAllPagination() {

        Pageable firstPageWithThreeRecords = PageRequest.of(0, 3);

        Pageable secondPageWithTwoRecords = PageRequest.of(1, 5);

        // findAll method contains pageable and Sort

        List<Course> courses = courseRepository.findAll(secondPageWithTwoRecords).getContent();

        long totalElements = courseRepository.findAll(secondPageWithTwoRecords).getTotalElements();

        long totalPages = courseRepository.findAll(secondPageWithTwoRecords).getTotalPages();

        System.out.println("Course pages details = " + courses);
        System.out.println("Total Elements = " + totalElements);
        System.out.println("Total Pages = " + totalPages);


    }

    @Test
    public void findAllDataSorting() {

        Pageable sortByTitle = PageRequest.of(0, 3, Sort.by("title"));

        Pageable sortByCreditDesc = PageRequest.of(1, 2, Sort.by("credit").descending());

        Pageable sortByTitleAndCreditDesc = PageRequest.of(1, 2,
                Sort.by("title")
                        .descending()
                        .and(Sort.by("credit"))
        );

        List<Course> courses = courseRepository.findAll(sortByCreditDesc).getContent();

        System.out.println(courses);
    }

    @Test
    public void findAllTitleContainingUsingJPARepo(){

        Pageable findTenRecordsInFirstPage = PageRequest.of(0 , 10);

        List<Course> courses = courseRepository.findByTitleContaining("P" ,findTenRecordsInFirstPage).getContent();

        System.out.println(courses);
    }

    @Test
    public void saveCourseWithStudentAndTeacher(){

        Guardian guardian = Guardian.builder()
                .name("Mahesh Kumar Bhatt")
                .email("MaheshK.bhatt@gmail.com")
                .mobile("9909909909")
                .build();

        Student student = Student.builder()
                .firstName("Rakesh")
                .lastName("Bhatt")
                .guardian(guardian)
                .emailId("Rakesh.Bhatt@gmail.com")
                .build();

        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        Teacher teacher = Teacher.builder()
                .firstName("Abhaya Kumar")
                .lastName("Mishra")
                .build();

        Course course = Course.builder()
                .students(studentList)
                .teacher(teacher)
                .title("Hindi Bhasa Madhuri Class IV")
                .credit(9)
                .build();

        courseRepository.save(course);

    }
}
