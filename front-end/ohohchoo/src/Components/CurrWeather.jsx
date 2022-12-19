import React from "react";
import styled from "styled-components";
function CurrWeather({ address, weather, temp }) {
  return (
    <WeatherInfo>
      <div>WeatherInfo</div>
      <h2>{address}</h2>
      <div>
        {/* 여기에는 weader데이터에 따른 그림을 출력해보자 크흠... */}
        {Math.round((temp - 273.15) * 10) / 10}℃
      </div>
      <h3>{weather}</h3>
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
