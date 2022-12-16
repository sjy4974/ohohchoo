package com.ohohchoo.domain.review.controller;

import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.service.ReviewService;
import com.ohohchoo.global.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("reviewApi")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    private static final String HEADER_AUTH = "access-token";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @PostMapping("/review")
    public ResponseEntity<?> write(@Validated @RequestBody ReviewWriteRequestDto reviewDto,
                                   HttpServletRequest req) {

        String token = req.getHeader(HEADER_AUTH);
        Long userId = jwtUtil.getTokenInfo(token);

        try {
            reviewService.write(userId,reviewDto);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);

    }

}
