package com.sanket.com.springDataJPA.repository;

import com.sanket.com.springDataJPA.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student , Long> {

    public List<Student> findByFirstName(String firstName);

    public List<Student> findByFirstNameContaining(String name);

    public List<Student> findByLastNameNotNull();

    public List<Student> findByGuardianName(String guardianName);

    // giving our own query using @Query
    //Query to find the student info by email Address
    @Query("select s from Student s where s.emailId = ?1")
    Student getStudentByEmailAddress(String emailAddress);

    //Query to find student's first name by email address

    @Query("select s.firstName from Student s where s.emailId = ?1")
    String findStudentFirstNameByEmailAddress(String emailId);  // string type

    // Native SQL Queries ---> complex SQL queries with direct interaction with our database

    @Query(
            value = "select * from tbl_student s where s.email_address = ?1",   // make sure the names of the fields matching with database in the Query
            nativeQuery = true
    )
    Student findStudentByEmailAddressNative(String emailId);

    // Native SQL Queries --> to find firstName and LastName by EmailAddress
    // make sure the names of the fields matching with database in the Query

    @Query(
            value = "select s.first_name , s.last_name from tbl_student s where s.email_address = ?1;",
            nativeQuery = true
    )
    String findStudentFirstNameAndLastNameByEmailAddress(String emailId); // string type


    // Query Named Params --> replacing the ?1 (positional parameter)
    // its useful when we have multiple params to pass

    @Query(
            value = "select * from tbl_student s where s.email_address = :emailId",
            nativeQuery = true
    )
    String findStudentByEmailAddressNamedParam(@Param("emailId") String emailId);

    //  @Modifying and @Transactional

    @Modifying      // helps to Insert , update and delete
    @Transactional  // helps to achieve ACID properties
    @Query(
            value = "update tbl_student set first_name = :firstName where email_address = :emailId",
            nativeQuery = true
    )
    int updateStudentFirstNameByEmailId(@Param("firstName") String firstName ,@Param("emailId") String emailId);
}
