package com.sanket.com.springDataJPA.repository;

import com.sanket.com.springDataJPA.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // pagination using JPARepository

    Page<Course> findByTitleContaining(String title , Pageable pageable);
}
