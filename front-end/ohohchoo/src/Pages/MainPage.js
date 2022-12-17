import React, { useEffect, useState } from "react";
// import axios from "../API/axios";
import Geocode from "react-geocode";
import axios from "axios";
import CurrWeather from "../Components/CurrWeather";
import RecommendClothes from "../Components/RecommendClothes";
// MainPage에서
// 시간정보, 주소 정보를 back에 요청할 수 있도록 데이터를 가공....

Geocode.setApiKey("API_KEY");
Geocode.setLanguage("en");
Geocode.setRegion("en");

export default function MainPage({ location }) {
  // const [weather, setWeather] = useState({});
  const [city, setCity] = useState("");
  const [result, setResult] = useState({});

  const API_KEY = "API_KEY";
  const url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${API_KEY}`;

  // const loc = { lat: location.coordinates.lat, lng: location.coordinates.lng };
  // console.log("시작!! loc", loc);

  useEffect(() => {
    console.log("나는 ", city);
    getWeather();
  }, [city]);

  const getWeather = () => {
    Geocode.fromLatLng(location.coordinates.lat, location.coordinates.lng).then(
      async (response) => {
        const address = response.results[0].formatted_address.split(",");
        setCity(address[2]);
        // console.log("address: ", address[2]);
        // console.log(typeof address[2]);
        // console.log("city: ", city);

        if (city !== "") {
          const data = await axios({
            method: "get",
            url: url,
          });
          // console.log("data: ", data);
          setResult(data);
          console.log(data);
        }

        // console.log("address: ", address);
        // console.log("adrress data type: ", typeof address);
        // const loc = address.split(",");
        // console.log(loc);
        // console.log(loc[2]);
      },
      (error) => {
        console.log("흠");
        console.error("error", error);
      }
    );
  };

  // 현재 시간 정보 받기

  return (
    <div>
      {Object.keys(result).length !== 0 && (
        <div>
          {/* 현재 날씨 정보 props: current-weather-info */}
          <CurrWeather
            address={city}
            weather={result.data.weather[0].main}
            temp={result.data.main.temp}
          ></CurrWeather>

          {/* 옷 추천 props: temperature */}
          <RecommendClothes temp={result.data.main.temp}></RecommendClothes>
          {/* <Clothes temp={}></Clothes> */}
          {/* 리뷰 : pros: location */}
          {/* <Review location={city}></Review> */}
          {/* <Review location={location}></Review> */}
          {/* 시간별 날씨 정보 hourly-weather-info  */}
          {/* <Row weatherInfo={}></Row> */}
          {/* 주간 날씨 정보 weekly-weather-info */}
          {/* <Row weatherInfo={}></Row> */}
        </div>
      )}
    </div>
  );
}
