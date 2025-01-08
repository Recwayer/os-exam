package com.example.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "instructor", nullable = false)
    private String instructor;

    @Column(name = "hours", nullable = false)
    private Integer hours;

    public Course(Long id, String title, String instructor, Integer hours) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.hours = hours;
    }

    public Course() {
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getInstructor() {
        return this.instructor;
    }

    public Integer getHours() {
        return this.hours;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String toString() {
        return "Course(id=" + this.getId() + ", title=" + this.getTitle() + ", instructor=" + this.getInstructor() + ", hours=" + this.getHours() + ")";
    }
}

