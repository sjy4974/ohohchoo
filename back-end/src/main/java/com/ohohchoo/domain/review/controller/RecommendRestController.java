package com.ohohchoo.domain.review.controller;

import com.ohohchoo.domain.review.dto.RecommendRequestDto;
import com.ohohchoo.domain.review.service.RecommendService;
import com.ohohchoo.global.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendApi")
public class RecommendRestController {

    private final RecommendService recommendService;

    private final JwtUtil jwtUtil;

    private static final String HEADER_AUTH = "access-token";
    private static final String SUCCESS = "success";

    @PostMapping("/recommend")
    public ResponseEntity<?> likeOrDisLike(@Validated @RequestBody RecommendRequestDto requestDto,
                                           HttpServletRequest req){

        String message = SUCCESS;
        HttpStatus status = HttpStatus.CREATED;
        try {
            recommendService.likeOrDislike(requestDto);
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(message, status);
    }

    @DeleteMapping("/recommend/{recommendId}")
    public ResponseEntity<?> cancelRecommend(@PathVariable Long recommendId,
                                             HttpServletRequest req){
        String token = req.getHeader(HEADER_AUTH);
        Long userId = jwtUtil.getTokenInfo(token);

        String message = SUCCESS;
        HttpStatus status = HttpStatus.OK;
        try {
            recommendService.delete(userId, recommendId);
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(message, status);
    }
}
