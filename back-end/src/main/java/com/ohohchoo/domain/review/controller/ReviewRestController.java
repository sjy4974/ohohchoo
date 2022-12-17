package com.ohohchoo.domain.review.controller;

import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.exception.ReviewNotFoundException;
import com.ohohchoo.domain.review.service.ReviewService;
import com.ohohchoo.global.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // 리뷰 작성
    @PostMapping("/review")
    public ResponseEntity<?> write(@Validated @RequestBody ReviewWriteRequestDto reviewDto,
                                   HttpServletRequest req) {

        // 요청 헤더의 토큰을 가져온 후 복호화해서
        // userId만 저장
        String token = req.getHeader(HEADER_AUTH);
        Long userId = jwtUtil.getTokenInfo(token);

        try {
            reviewService.write(userId,reviewDto);
        } catch (Exception e){
            // validation 통과하지 못하거나, userId가 잘못된경우 해당 에러메세지를 리턴
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);

    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable Long reviewId){
        String message = SUCCESS;
        HttpStatus status = HttpStatus.OK;

        // delete 로직 실행중 예외가 발생하면
        // 해당 예외 메세지, HttpStatus 상태를 변경해서 return
        try {
            reviewService.delete(reviewId);
        } catch (ReviewNotFoundException e) {
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(message, status);
    }

}
