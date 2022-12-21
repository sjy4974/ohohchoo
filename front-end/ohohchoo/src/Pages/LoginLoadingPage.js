import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const LoginLoading = () => {
    const href = window.location.href;
    let params = new URL(document.URL).searchParams;
    let code = params.get("code");

    const navigate = useNavigate();

    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        userJoinRequest(code);
    }, [])

    const userJoinRequest = async (code) => {
        try {
            //요청을 시작하면서 error를 초기화한다.
            setError(null);
            //loading 상태를 true로 바꾼다.
            setLoading(true);
            //axios 요청 보내기
            const response = await axios.get(
                'http://localhost:8080/userApi',{
                    params: {"code": code}
                }
            );
            
            sessionStorage.setItem("current-user", response.data["current-user"]);
            setUser(response.data["current-user"]);

        } catch(e) {
            setError(e);
            navigate("/");
        }
        setLoading(false);
    }

    if(loading) return <div>로그인 중입니다...</div>
    if(error) return <div>로그인에 실패하였습니다.</div>

    if(!user) return null;
    if(user) navigate("/");
}

export default LoginLoading;