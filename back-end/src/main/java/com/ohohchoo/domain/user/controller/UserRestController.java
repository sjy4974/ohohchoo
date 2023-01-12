package com.ohohchoo.domain.user.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ohohchoo.domain.user.dto.UserJoinRequestDto;
import com.ohohchoo.domain.user.dto.UserUpdateRequestDto;
import com.ohohchoo.domain.user.entity.User;
import com.ohohchoo.domain.user.exception.UserNotFoundException;
import com.ohohchoo.domain.user.service.UserService;
import com.ohohchoo.global.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userApi")
public class UserRestController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private final UserService userService;
    private final Environment env;

    // 유저 정보 수정 ( 성별 or 온도 민감도)
    @PutMapping("/user/{userId}")
    public ResponseEntity<String> update(@RequestBody UserUpdateRequestDto userUpdateRequestDto,
                                         @PathVariable Long userId) {

        try {
            userService.update(userId, userUpdateRequestDto);
        } catch (UserNotFoundException e) { // 해당 유저를 찾을 수 없는경우
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) { // 온도민감도, 성별 모두 null로 넘어온경우
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    // 유저 정보 등록 or 조회
    @GetMapping("/kakaoLogin")
    public ResponseEntity<Map<String, Object>> RequestAccessToken(@RequestParam String code) {
        //카카오 서버에 POST 방식으로 엑세스 토큰을 요청
        //RestTemplate를 이용
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();

        System.out.println("인가 코드 확인 :"+code);
        System.out.println(env.getProperty("kakao.login.api_key"));

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", env.getProperty("kakao.login.api_key"));
        params.add("redirect_uri", env.getProperty("kakao.login.redirect_uri"));
        params.add("code", code);
        //HttpHeader와 HttpBody를 HttpEntity에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(params, headers);
        //카카오 서버에 HTTP 요청 - POST
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoRequest,
                String.class
        );
        //응답에서 엑세스 토큰 받기
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(response.getBody()).getAsJsonObject();
        String accessToken = jo.get("access_token").getAsString();
        //엑세스 토큰을 통해 사용자 정보를 응답 받기
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.add("Authorization", "Bearer " + accessToken);
        tokenHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(tokenHeaders);

        rt = new RestTemplate();

        ResponseEntity<String> userInfoResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                userInfoRequest,
                String.class
        );
        JsonObject userJsonObject = jp.parse(userInfoResponse.getBody()).getAsJsonObject();
        JsonObject userAccountObject = jp.parse(userJsonObject.get("kakao_account").toString()).getAsJsonObject();
        String nickname = jp.parse(userAccountObject.get("profile").toString()).getAsJsonObject().get("nickname").getAsString();
        String email = userAccountObject.get("email").getAsString();

        System.out.println("=========================================");
        System.out.println("토큰에서 꺼내온 값 확인하기");
        System.out.println("닉네임 : "+nickname);
        System.out.println("email : "+email);

        HashMap<String, Object> result = new HashMap<>();
        JwtUtil jwtUtil = new JwtUtil();
        try {
            String token = jwtUtil.createToken("userId", 1L);
            result.put("access-token", token);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
