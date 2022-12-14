package com.ohohchoo.user.controller;

/**
 * 회원 관리 Controller
 * 카카오 로그인 및 회원 관리를 담당
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ohohchoo.user.entity.User;
import com.ohohchoo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    /**
     * code를 요청받아
     * access token을 받고
     * 해당 access token을 기반으로
     * 사용자 정보를 받는 메소드
     */
    @GetMapping("/kakao")
    public ResponseEntity<Map<String, Object>> RequestAccessToken(@RequestParam String code) {
        //카카오 서버에 POST 방식으로 엑세스 토큰을 요청
        //RestTemplate를 이용
        RestTemplate rt = new RestTemplate();
        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "32b01baf85e405c00cffe1d59f22fd1b");
        params.add("redirect_uri", "http://localhost:9999/user/kakao");
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
        //성별의 경우, 선택 동의 사항이기 때문에 기본 남성으로 설정한다.
        String gender = "male";
        if(userAccountObject.get("has_gender").getAsBoolean()) {
            gender = userAccountObject.get("gender").getAsString();
        }
        User user = new User(nickname, email, gender, 1);

        //해당 nickname으로 가입된 사용자가 있다면, 로그인을 진행하고
        //없다면, DB에 저장하는 과정을 거쳐 로그인을 진행한다.
        if(userService.findByEmail(email).isEmpty()) {
            userService.save(user);
        }

        //로그인을 진행
        HashMap<String, Object> result = new HashMap<>();
        User loginUser = userService.findByEmail(email).get();
            //jwt 토큰 추가 필요
        result.put("current-user", loginUser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //유저 삭제 요청을 받아, ID로 삭제를 진행
    //로그아웃도 같이 진행
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user, HttpSession session) {
        int id = user.getId();
        userService.deleteById(id);
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> doLogout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
