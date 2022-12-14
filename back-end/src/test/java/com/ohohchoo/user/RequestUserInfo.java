package com.ohohchoo.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


/**
 * JSON을 응답받아
 * 엑세스 토큰을 파싱하여
 * 유저 정보를 받아온다.
 */
@SpringBootTest
public class RequestUserInfo {
    @Test
    void KakaoUserInfo() {
        RestTemplate rt = new RestTemplate();

        String response = "{\"access_token\":\"CXyWfQi-HDwkxjdtaBp-E0f1BuxG8nU3YN0ma0LmCisNIAAAAYUQnrzh\",\"token_type\":\"bearer\",\"refresh_token\":\"Hepzr_3uml--I0uU0cCbK4uY3ZqujEA2dFPHmyCgCisNIAAAAYUQnrzg\",\"id_token\":\"eyJraWQiOiI5ZjI1MmRhZGQ1ZjIzM2Y5M2QyZmE1MjhkMTJmZWEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzMmIwMWJhZjg1ZTQwNWMwMGNmZmUxZDU5ZjIyZmQxYiIsInN1YiI6IjI1NzkyMDU1NjkiLCJhdXRoX3RpbWUiOjE2NzEwMjExMTYsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwibmlja25hbWUiOiLqs7XsobDtlZwiLCJleHAiOjE2NzEwNDI3MTYsImlhdCI6MTY3MTAyMTExNiwiZW1haWwiOiJqb2hhbkBrYWthby5jb20ifQ.Le31u8z4lzkNB5itfek4-2j0xaGwwI1NtUuIvCb_KF4gW0Efq-XuE2AC0ibmyDvWi7p1Th_ycjfWCbI3Ps80vh88XFzrX1KLurJX6sTbmVhdRS0ck9HcWfhAP8VNRuMBUjxeP51xGRVaVq-YBULphxiPiOS6hkNtvZkIDtYoxAwLTvEl8rpIYZqaMZ7rgeZxs5QaVc6LRir_jEYSSMGA0k4ZNonN9NcEsWwSjG31TQLGoX-97VMTtjsUalnVAMAMQr7cE2p3H1BHmXrRWEJBHxxU1BAPACbRdAYEZyVCpv5Ull2aw5yb3Vimla7P_UHeUmxTgD_zS5fTOZGhpNhcug\",\"expires_in\":21599,\"scope\":\"account_email gender openid profile_nickname\",\"refresh_token_expires_in\":5183999}";
        //응답에서 엑세스 토큰 받기
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(response).getAsJsonObject();
        String accessToken = jo.get("access_token").getAsString();
        //엑세스 토큰을 통해 사용자 정보를 응답 받기
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.add("Authorization", "Bearer " + accessToken);
        tokenHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<HttpHeaders> userInfoRequest = new HttpEntity<>(tokenHeaders);

        ResponseEntity<String> userInfoResponse = rt.exchange(
          "https://kapi.kakao.com/v2/user/me",
          HttpMethod.POST,
          userInfoRequest,
          String.class
        );
        System.out.println(userInfoResponse);
    }
}
