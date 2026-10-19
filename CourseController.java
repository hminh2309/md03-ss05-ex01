package com.example.session05.controllers;

import com.example.session05.models.dtos.ApiResponse;
import com.example.session05.models.dtos.CourseResponse;
import com.example.session05.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CourseResponse>>> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction) {

        // Lấy danh sách khóa học đã phân trang và sắp xếp từ Service
        Page<CourseResponse> pagedCourses = courseService.getPagedCourses(page, size, sortBy, direction);

        // Đóng gói vào ApiResponse sử dụng Builder của Lombok
        ApiResponse<Page<CourseResponse>> response = ApiResponse.<Page<CourseResponse>>builder()
                .success(true)
                .message("OK")
                .data(pagedCourses)
                .build();

        return ResponseEntity.ok(response);
    }
}