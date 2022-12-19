import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import LocationComp from "../Components/LocationComp";
export default function SelectLocationPage({
  location,
  setLocation,
  user,
  city,
  setCity,
  result,
  setResult,
}) {
  // user 데이터가 필요하네? 흠....
  const [locationList, setLocationList] = useState([]);
  useEffect(() => {
    console.log("locationList Call");
  }, []);
  // 여기서 axios 요청
  // useEffect(() => {

  // }, [])

  const changeLocationBtn = (e) => {
    console.log(e);
  };

  return (
    <div>
      <h2>SelectLocationPage</h2>
      <button onClick={changeLocationBtn}></button>
      {/* {locationList.map((loc, index) => (
        <LocationComp key='index' title={loc} />
      ))} */}
    </div>
  );
}

// LocationBtn에 hover 속성 추가하기
const LocationBtn = styled.div``;
