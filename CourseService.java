package com.example.session05.services;

import com.example.session05.models.dtos.CourseResponse;
import com.example.session05.models.entities.Course;
import com.example.session05.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Page<CourseResponse> getPagedCourses(int page, int size, String sortBy, Sort.Direction direction) {
        // Safety check cho tham số page: nếu page < 0 thì tự điều chỉnh về 0
        if (page < 0) {
            page = 0;
        }

        Sort sort;
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sort = Sort.by(direction, "id");
        } else {
            sort = Sort.by(direction, sortBy);
        }

        // Tạo đối tượng Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Lấy dữ liệu phân trang từ Database thông qua Repository
        Page<Course> coursePage = courseRepository.findAll(pageable);

        // Chuyển đổi từ Page<Course> sang Page<CourseResponse>
        return coursePage.map(this::convertToCourseResponse);
    }

    // Hàm chuyển đổi Entity sang DTO sử dụng Builder của Lombok
    private CourseResponse convertToCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .build();
    }
}