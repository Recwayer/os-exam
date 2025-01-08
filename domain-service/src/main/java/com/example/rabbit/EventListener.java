package com.example.rabbit;

import com.example.dto.Course;
import com.example.rabbit.model.Message;
import com.example.rabbit.model.Operation;
import com.example.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventListener {
    private final ObjectMapper objectMapper;
    private final CourseService courseService;

    @RabbitListener(queues = "${rabbit.queue}")
    public void handleEvent(String stringMessage) {
        Message message = convertToObject(stringMessage);
        Operation operation = message.getOperation();
        try {
            if (Operation.CREATE.equals(operation)) {
                handleCreate(convertToCourse(message.getData()));
            } else if (Operation.UPDATE.equals(operation)) {
                handleUpdate(convertToCourse(message.getData()));
            } else if (Operation.DELETE.equals(operation)) {
                handleDelete(Long.valueOf((Integer) message.getData()));
            }
        } catch (Exception ignored) {
        }
    }

    private Message convertToObject(String message) {
        try {
            return objectMapper.readValue(message, Message.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Course convertToCourse(Object object) {
        Map<String, Object> data = (Map) object;
        return objectMapper.convertValue(data, Course.class);
    }

    private void handleCreate(Course course) {
        if (course.getId() != null) {
            return;
        }
        courseService.create(course);
    }

    private void handleUpdate(Course course) {
        courseService.update(course.getId(), course);
    }

    private void handleDelete(Long id) {
        courseService.delete(id);
    }

}
