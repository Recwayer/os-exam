package com.example.service;

import com.example.dto.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    Course create(Course course);

    Page<Course> getAll(Pageable pageable);

    Course get(Long id);

    Course update(Long id, Course course);

    void delete(Long id);
}
