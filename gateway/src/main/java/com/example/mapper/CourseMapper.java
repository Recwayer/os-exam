package com.example.mapper;

import com.example.dto.CourseDto;
import com.example.service.grpc.CourseOuterClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDto toDto(CourseOuterClass.Course course);

    CourseOuterClass.Course fromDto (CourseDto courseDto);
}
