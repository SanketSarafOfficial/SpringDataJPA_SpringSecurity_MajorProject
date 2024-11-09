package com.sanket.com.springDataJPA.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CourseMaterial {
    @Id
    @SequenceGenerator(name = "courseMaterial_sequence",
            sequenceName = "courseMaterial_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "courseMaterial_sequence")
    private Long courseMaterialId;

    private String url;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    // so you will the course_id field in the course Material db due to one-to-one relationship
    private Course course;
}
