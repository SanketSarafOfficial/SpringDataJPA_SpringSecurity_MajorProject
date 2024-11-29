package com.sanket.com.springDataJPA.repository;

import com.sanket.com.springDataJPA.entity.Course;
import com.sanket.com.springDataJPA.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void saveTeacherData(){

        Course course = Course
                .builder()
                .title("HC Verma Physics XII")
                .credit(4)
                .build();

        Course courseJava = Course
                .builder()
                .title("Java Advance Fundamentals By Karumanchi")
                .credit(6)
                .build();

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        courseList.add(courseJava);

        Teacher teacher = Teacher.builder()
                .firstName("Brijesh")
                .lastName("Sharma")
//                .courses(courseList)
                .build();

        teacherRepository.save(teacher);
    }
}
