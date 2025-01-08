package com.example.service.grpc;

import com.example.dto.Course;
import com.example.service.CourseService;
import com.example.service.grpc.CourseOuterClass.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseGrpcService extends CourseServiceGrpc.CourseServiceImplBase {
    private final CourseService courseService;

    @Override
    public void createCourse(CreateCourseRequest request,
                             StreamObserver<CourseResponse> responseObserver) {
        Course course = courseService.create(new Course(null, request.getTitle(), request.getInstructor(), request.getHours()));

        responseObserver.onNext(CourseResponse.newBuilder().setCourse(map(course)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllCourses(GetAllCoursesRequest request,
                              StreamObserver<CourseListResponse> responseObserver) {

        Page<Course> page = courseService.getAll(PageRequest.of(request.getPage(), request.getSize() == 0 ? 10 : request.getSize()));

        responseObserver.onNext(CourseListResponse.newBuilder().addAllCourses(page.getContent()
                .stream()
                .map(this::map)
                .collect(Collectors.toList())).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getCourse(GetCourseRequest request,
                          StreamObserver<CourseResponse> responseObserver) {
        Course course = courseService.get(request.getId());

        responseObserver.onNext(CourseResponse.newBuilder().setCourse(map(course)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateCourse(CourseOuterClass.UpdateCourseRequest request,
                             StreamObserver<CourseResponse> responseObserver) {
        Course course = courseService.update(request.getId(), new Course(request.getId(), request.getTitle(), request.getInstructor(), request.getHours()));

        responseObserver.onNext(CourseResponse.newBuilder().setCourse(map(course)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCourse(GetCourseRequest request,
                             StreamObserver<Empty> responseObserver) {
        courseService.delete(request.getId());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    private CourseOuterClass.Course map(Course course) {
        return CourseOuterClass.Course.newBuilder().setId(course.getId()).setTitle(course.getTitle()).setHours(course.getHours()).setInstructor(course.getInstructor()).build();
    }
}
