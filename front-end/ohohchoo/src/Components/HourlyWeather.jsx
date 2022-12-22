import React from "react";

export default function HourlyWeather({ hourlyWth }) {
  const renderWeathers = hourlyWth.map((weather) => {
    let ptySkyTxt = "";
    switch (weather.ptySky) {
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
      <div className="hourly-weather">
        {weather.fcstDate} {weather.fcstTime} {ptySkyTxt} {weather.tmp}
      </div>
    );
  });

  return (
    <section className="hourly">
      <h2>시간별 날씨</h2>
      {renderWeathers}
    </section>
  );
}
