package com.example.controller;

import com.example.dto.CourseCreateRequest;
import com.example.dto.CourseDto;
import com.example.mapper.CourseMapper;
import com.example.rabbit.MessageProducer;
import com.example.rabbit.model.Message;
import com.example.rabbit.model.Operation;
import com.example.service.grpc.CourseOuterClass;
import com.example.service.grpc.CourseServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.configuration.RedisConfig.REDIS_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
@Slf4j
public class GatewayController {

    private final CourseServiceGrpc.CourseServiceBlockingStub courseServiceStub;
    private final CourseMapper courseMapper;
    private final MessageProducer messageProducer;

    @GetMapping("/{id}")
    @Cacheable(value = REDIS_KEY, key = "#id")
    public CourseDto getById(@PathVariable Long id) {
        log.info("GET request for getting course with (id: {})", id);
        CourseOuterClass.GetCourseRequest request = CourseOuterClass.GetCourseRequest.newBuilder().setId(id).build();
        CourseOuterClass.CourseResponse response = courseServiceStub.getCourse(request);
        return courseMapper.toDto(response.getCourse());
    }

    @GetMapping
    @Cacheable(value = REDIS_KEY, key = "#page + '-' + #size")
    public List<CourseDto> getAll(@RequestParam int page, @RequestParam int size) {
        log.info("GET request for getting courses with (page: {}, size: {})", page, size);
        CourseOuterClass.GetAllCoursesRequest request = CourseOuterClass.GetAllCoursesRequest.newBuilder().setPage(page).setSize(size).build();
        CourseOuterClass.CourseListResponse courseListResponse = courseServiceStub.getAllCourses(request);
        return courseListResponse.getCoursesList().stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    @CacheEvict(value = REDIS_KEY, allEntries = true)
    public ResponseEntity<?> create(@RequestBody CourseCreateRequest data) {
        log.info("POST request for creating (course: {})", data);
        messageProducer.sendMessage(Message.builder().operation(Operation.CREATE).data(data).build());
        return ResponseEntity.accepted().body("Create request sent.");
    }

    @PatchMapping("/{id}")
    @CacheEvict(value = REDIS_KEY, allEntries = true)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CourseDto data) {
        log.info("PATCH request for updating (id: {}, course: {})", id, data);
        data.setId(id);
        messageProducer.sendMessage(Message.builder().operation(Operation.UPDATE).data(data).build());
        return ResponseEntity.accepted().body("Update request sent.");
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = REDIS_KEY, allEntries = true)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("DELETE request for deleting course with (id: {})", id);
        messageProducer.sendMessage(Message.builder().operation(Operation.DELETE).data(id).build());
        return ResponseEntity.accepted().body("Delete request sent.");
    }
}

