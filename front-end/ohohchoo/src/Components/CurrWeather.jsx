import React from "react";
import styled from "styled-components";
function CurrWeather({ city, town, tmp, pty, sky }) {
  return (
    <WeatherInfo>
      <div>WeatherInfo</div>
      <h2>
        {city} {town}
      </h2>
      <div>
        {/* 여기에는 weader데이터에 따른 그림을 출력해보자 크흠... */}
        {tmp}℃
      </div>
      <h3></h3>
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
