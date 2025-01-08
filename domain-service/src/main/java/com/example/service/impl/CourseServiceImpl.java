package com.example.service.impl;

import com.example.dto.Course;
import com.example.repository.CourseRepository;
import com.example.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Page<Course> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Course get(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found by id : " + id));
    }

    @Override
    public Course update(Long id, Course updateCourse) {
        Course course = get(id);
        Optional.ofNullable(updateCourse.getTitle()).ifPresent(course::setTitle);
        Optional.ofNullable(updateCourse.getHours()).ifPresent(course::setHours);
        Optional.ofNullable(updateCourse.getInstructor()).ifPresent(course::setInstructor);
        return courseRepository.save(course);
    }

    @Override
    public void delete(Long id) {
        get(id);
        courseRepository.deleteById(id);
    }
}
