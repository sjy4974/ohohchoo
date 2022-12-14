package com.ohohchoo.user;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 카카오에서 받은 인가 코드를 통해
 * 엑세스 토큰을 요청
 */
public class RequestAccessToken {
    public static void main(String[] args) {
        //카카오에서 받은 인가 코드
        String code = "GeTjbIYx6uoFeUllRptrP4pAsWp5dWn4r0pEmw12PLxRDZqWQXjlsYoPnG-d5VGj52y3jwo9dJgAAAGFEHYLEQ";
        //카카오 서버에 POST 방식으로 엑세스 토큰을 요청
        //RestTemplate를 이용
        RestTemplate rt = new RestTemplate();
        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "32b01baf85e405c00cffe1d59f22fd1b");
        params.add("redirect_uri", "http://localhost:9999/oauth/kakao");
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
        System.out.println(response.toString());
    }
}
