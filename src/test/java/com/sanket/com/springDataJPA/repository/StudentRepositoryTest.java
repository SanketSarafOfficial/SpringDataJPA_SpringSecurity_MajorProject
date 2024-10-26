package com.sanket.com.springDataJPA.repository;

import com.sanket.com.springDataJPA.entity.Guardian;
import com.sanket.com.springDataJPA.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Generally to test JPA Repository or database layer , we use @DataJpaTest , since it flushes the data after table and data creation
 * @SpringBootTest will not flush any data, and we can test using this annotation to check the database data creation
 *
 * */

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent() {
        Student student = Student.builder()
                .emailId("sanket@gmail.com")
                .firstName("Sanket")
                .lastName("Saraf")
//                .guardianName("Akshya Kumar Saraf")
//                .guardianEmail("aks@gmail.com")
//                .guardianMobile("9437123333")
                .build();

        studentRepository.save(student); // will store in database
    }

    @Test
    public void findStudentList(){
        List<Student> studentList = studentRepository.findAll(); // to search and fetch
        for(Student stud : studentList){
            System.out.println(stud);
        }
    }

    @Test
    public void saveStudentWithGuardianDetails(){
        Guardian guardian = Guardian.builder()
                .name("Mahadev Umapati")
                .email("Mahadev@gamil.com")
                .mobile("9437125151")
                .build();

        Student student = Student.builder()
                .firstName("Ganesh")
                .lastName("Lambodara")
                .emailId("Ganesh@gamil.com")
                .guardian(guardian)
                .build();

        studentRepository.save(student);
    }

    @Test
    public void findStudentByFirstName(){
        List<Student> studentFirstNameInfo = studentRepository.findByFirstName("Ganesh");
        System.out.println(studentFirstNameInfo);
    }

    @Test
    public void findStudentContainingFirstName(){
        List<Student> studentFirstNameInfo = studentRepository.findByFirstNameContaining("Ga");
        System.out.println(studentFirstNameInfo);
    }

    @Test
    public void findStudentBasedOnGuardianName(){
        List<Student> studentInfo = studentRepository.findByGuardianName("Mahadev Umapati");
        System.out.println(studentInfo);
    }

    @Test
    public void findByLastNameNotNull(){
        List<Student> studentInfo = studentRepository.findByLastNameNotNull();
        System.out.println(studentInfo);
    }

}
