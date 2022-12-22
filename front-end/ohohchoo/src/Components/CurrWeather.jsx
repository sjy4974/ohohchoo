import React from "react";
import styled from "styled-components";
function CurrWeather({ city, town, tmp, ptySky }) {
  let ptySkyTxt = "";
  console.log(ptySky);

  switch (ptySky) {
    case 1:
      ptySkyTxt = "비";
      break;
    case 2:
      ptySkyTxt = "비 또는 눈";
      break;
    case 3:
      ptySkyTxt = "눈";
      break;
    case 4:
      ptySkyTxt = "소나기";
      break;
    case 5:
      ptySkyTxt = "맑음";
      break;
    case 7:
      ptySkyTxt = "약간 흐림";
      break;
    case 8:
      ptySkyTxt = "흐림";
      break;
  }

  return (
    <WeatherInfo>
      <div>WeatherInfo</div>
      <h2>
        {city} {town}
      </h2>
      <div>
        {/* 여기에는 weader데이터에 따른 그림을 출력해보자 크흠... */}
        {tmp}℃ <br />
        {ptySkyTxt}
      </div>

    </WeatherInfo>
  );
}

export default CurrWeather;

const WeatherInfo = styled.div`
  width: 100vw;
  heigth: 100vh;
  h2 {
    left: 50%;
    top: 50%;
    font-size: 24px;
  }
`;
