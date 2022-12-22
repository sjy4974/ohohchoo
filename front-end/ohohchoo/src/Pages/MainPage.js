import React, { useEffect, useState } from "react";
import styled from "styled-components";
// import axios from "../API/axios";
import Geocode from "react-geocode";
import axios from "axios";
import CurrWeather from "../Components/CurrWeather";
import RecommendClothes from "../Components/RecommendClothes";
import OptionButton from "../Components/OptionButton";
import Review from "../Components/Review";
import { dummy } from "../OptionDummy";
import Nav from "../Components/Nav";
import axios2 from "../API/axios";
import requests from "../API/request";
import LocationComp from "../Components/LocationComp";

Geocode.setApiKey("AIzaSyAoKq3Uq6CfDSQ91bccZ17H4-DGo-SnTQw");
Geocode.setLanguage("ko");
Geocode.setRegion("ko");

export default function MainPage({ location }) {
  const [mode, setMode] = useState(0);
  const [town, setTown] = useState("");
  const [city, setCity] = useState("서울");
  const [result, setResult] = useState({});
  const [user, setUser] = useState("김현수");

  const [reviewModal, setReviewModal] = useState(false);
  const [gender, setGender] = useState(-1);
  const [sensitivity, setSensitivity] = useState(-1);

  const [reviewData, setReviewData] = useState([]);

  const API_KEY = "011be7fcc3f5c002bed4737f3e97b02a";
  const url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${API_KEY}`;

  // console.log("location Info : ", location);
  useEffect(() => {
    console.log("getWeather 함수 실행");
    getWeather();
  }, [city]);

  useEffect(() => {
    console.log("user : ", user);
    // sessionStorage.getItem("user");
  }, [user]);

  useEffect(() => {
    console.log("gender : ", gender);
  }, [gender]);

  useEffect(() => {
    console.log("sensitivity", sensitivity);
  }, [sensitivity]);

  const getWeather = () => {
    Geocode.fromLatLng(location.coordinates.lat, location.coordinates.lng).then(
      async (response) => {
        const address = response.results[0].formatted_address.split(" ");
        setCity(address[1]);
        setTown(address[2]);
        if (city !== "" && town !== "") {
          const reqLoc = {
            city: city,
            town: town,
          };
          const wthToday = await axios({
            url: `${url}/weather/today`,
            method: "POST",
            headers: { "Content-type": "application/json" },
            data: reqLoc,
          });
          console.log(wthToday.data);
          setResult(wthToday.data);
        }
      },
      (error) => {
        console.error("error", error);
      }
    );
  };

  // ReviewModal Handler
  const ModalHandler = () => {
    setReviewModal((prev) => !prev);
  };

  const pullReviewData = async () => {
    // 여기서 리뷰 데이터를 가지고 있자.
    const data = await axios2.get(requests.fetchActionMovies);

    setReviewData(data);
    console.log(reviewData);
  };

  return (
    <div>
      <Nav user={"김현수"} city={city} mode={mode} setMode={setMode}></Nav>
      {/* mode === 0 => 날씨 정보 제공창 */}
      {mode === 0 && (
        // <div>
        //   <RecommendClothes temp={10} />
        //   <div>{city}</div>
        <div>
          {Object.keys(result).length !== 0 && (
            <div>
              {/* <Nav city={city} user={user}>
                {console.log("Nav bar 생성")}
              </Nav> */}
              {/* 현재 날씨 정보 props: current-weather-info */}
              <CurrWeather
                city={city}
                town={town}
                tmp={result.tmp}
                ptySky={result.ptySky}
              ></CurrWeather>

              {/* 남자 여자 선택하는 버튼 만들기, props={ gender, setGender } */}
              {/* 온도 민감도 선택하는 버튼 만들기 props={ sensitivity, setsensitivity }*/}
              <RootWrap>
                {dummy.map((item) => (
                  <OptionButton
                    key={item.idx}
                    title={item.title}
                    OptionList={item.OptionList}
                    setOption={
                      item.title === "성별" ? setGender : setSensitivity
                    }
                  />
                ))}
              </RootWrap>

              {/* 옷 추천 props: temperature */}
              <RootWrap>
                <RecommendClothes
                  temp={result.data.main.temp}
                ></RecommendClothes>
              </RootWrap>
              {/* <Clothes temp={}></Clothes> */}
              {/* 리뷰 : pros: location */}
              <ModalWrapper>
                {reviewModal ? (
                  <ModalBackground>
                    <ModalBox>
                      {/* <ModalBtn onClick={ModalHandler}>X</ModalBtn> */}
                      <Review
                        city={city}
                        user={user}
                        setReviewModal={setReviewModal}
                        ModalHandler={ModalHandler}
                      />
                    </ModalBox>
                  </ModalBackground>
                ) : (
                  <OpenModal onClick={ModalHandler}>
                    Click! 오늘의 날씨와 옷차림 후기
                  </OpenModal>
                )}
              </ModalWrapper>
              {/* <Review location={city}></Review> */}

              {/* <Review location={location}></Review> */}
              {/* 시간별 날씨 정보 hourly-weather-info  */}
              {/* <Row weatherInfo={}></Row> */}
            </div>
          )}
        </div>
      )}
      {/* mode === 1 => 위치 선택창 */}
      {mode === 1 && (
        <LocationComp city={city} setCity={setCity} setMode={setMode} />
      )}
      {/* mode === 2 => 로그인 창 */}
    </div>
  );
}

const RootWrap = styled.div`
  border: solid;
  text-align: center;
  // margin-top: 2vh;
  // position: absolute;
  // top: 0;
  // bottom: 0;
  // width: 100%;
  // max-width: 500px;
  // left: 50%;
  // transform: translate(-50%, 0);

  // background-color: white;

  // padding: 20px;
`;

const ModalWrapper = styled.div`
  border: solid;
`;

const ModalBackground = styled.div``;

const ModalBox = styled.div``;

const ModalBtn = styled.button``;

const OpenModal = styled.div`
  background-color: black;
  color: white;
  margin 0 5vw;
  // border: solid;
  border-radius: 0 0 1vw 1vw;
  padding: 0;
  height: 3vh;
  line-height: 3vh;
  // padding-top: 1vw;
  // padding-bottom: 1vw;
  font-size: 10px;
  text-align: center;
`;
