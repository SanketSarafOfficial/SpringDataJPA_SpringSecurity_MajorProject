package com.sanket.com.springDataJPA.repository;

import com.sanket.com.springDataJPA.entity.Course;
import com.sanket.com.springDataJPA.entity.CourseMaterial;
import org.hibernate.usertype.CompositeUserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseMaterialRepositoryTest {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Test
    public void saveCourseMaterial() {
        Course course = Course
                .builder()
                .title("RD Sharma Class XII Mathematics")
                .credit(5)
                .build();

        CourseMaterial courseMaterial = CourseMaterial
                .builder()
                .url("www.google.com")
                .course(course)
                .build();

        courseMaterialRepository.save(courseMaterial);
    }

}


/*
*
* Hibernate: select next_val as id_val from course_material_sequence for update
Hibernate: update course_material_sequence set next_val= ? where next_val=?
org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : com.sanket.com.springDataJPA.entity.CourseMaterial.course -> com.sanket.com.springDataJPA.entity.Course
at org.springframework.orm.jpa.EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(EntityManagerFactoryUtils.java:368)
*
* // --> occuring when we created a courseMaterial builder and tried to save the data in database
* // anyhow we made the Course class builder & passed in the CourseMaterial builder , still this issue occurred
* // its happened because , the database is expected some data for course table.
*
* */
