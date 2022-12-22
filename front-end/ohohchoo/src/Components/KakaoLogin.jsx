import React from "react";
import { useNavigate } from 'react-router-dom';

const { Kakao } = window;
// kakao SDK를 초기화 합니다. 사용할 앱의 JavaScript 키를 설정해야 합니다.
Kakao.init(`${process.env.REACT_APP_KAKAO_LOGIN_JS_KEY}`);
// kakao SDK 초기화 여부를 판단합니다.
console.log(Kakao.isInitialized());

function KakaoLogin() {
    const navigate = useNavigate();
    const kakaoLoginHandler = () => {
        Kakao.Auth.authorize({
            redirectUri: `${process.env.REACT_APP_KAKAO_LOGIN_REDIRECT_URL}`
        })
    }

    return (
        <div>
            로그인 버튼을 넣겠습니다. <br />
            <button onClick={kakaoLoginHandler}>버튼</button>
        </div>
    )
}

export default KakaoLogin;