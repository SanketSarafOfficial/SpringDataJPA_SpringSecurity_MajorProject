package com.sanket.com.springDataJPA.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = "course")
public class CourseMaterial {
    @Id
    @SequenceGenerator(name = "courseMaterial_sequence",
            sequenceName = "courseMaterial_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "courseMaterial_sequence")
    private Long courseMaterialId;

    private String url;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY ,optional = false)  // Cascading will pass down data of parents (Course) to the child (CourseMaterial)
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    // so you will generate the course_id field in the course Material db due to one-to-one relationship
    private Course course;
}


/*
* The relationship between two classes are established using two important annotations i.e. @OneToOne , @JoinColumn
* In the above example name = "course_id" represents the foreign key in CourseMaterialDB
* The referencedColumnName = "courseId" represents the field present in the course class.
*
*  */
