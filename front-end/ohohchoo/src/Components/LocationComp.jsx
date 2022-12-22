import React, { useState, useEffect } from "react";
import axios from "../API/axios";
import requests from "../API/request";
import styled from "styled-components";

function Location({ city, setCity, setMode }) {
  const [locationList, setLocationList] = useState([]);

  useEffect(() => {
    console.log("location 데이터 불러오기");
    fetchLocationData();
  }, []);

  const fetchLocationData = async () => {
    const request = await axios.get(requests.fetchActionMovies);
    console.log(request.data.results);
    setLocationList(request.data.results);
  };

  const ModeHandler = () => {
    setMode(0);
  };

  const SelectLocation = (title) => {
    setCity(title);
  };

  const handleSubmit = (e) => {
    e.previentDefault();
    /////////////////////////////////////

    // location 정보 등록 관련 코드 작성 //

    /////////////////////////////////////
    console.log(e);
  };

  return (
    <LocationWrap>
      <Form onSubmit={handleSubmit}>
        <input type='text' name='locationSearch' placeholder='...'></input>
      </Form>
      <LocationContent>
        <h5>선택 위치</h5>
        <LocationContainer onClick={ModeHandler}>{city}</LocationContainer>
      </LocationContent>
      <LocationContent>
        <h5>관심 위치</h5>
        {locationList.map((location, index) => (
          <LocationContainer
            key={index}
            onClick={() => {
              ModeHandler();
              SelectLocation(location.title);
            }}
          >
            <div>{location.original_title}</div>
          </LocationContainer>
        ))}
      </LocationContent>
    </LocationWrap>
  );
}

export default Location;

const LocationWrap = styled.div`
  width: 100vw;
  height: 100vh;
`;
const Form = styled.form``;

const LocationContent = styled.div`
  margin-top: 10px;
  text-align: center;

  h5 {
    margin: 0 10%;
    text-align: left;
  }
`;

const LocationContainer = styled.button`
  width: 80vw;
  height: 5vh;
  background-color: #ded7d7;
  opacity: 90%;
  margin-bottom: 2vh;
  display: inline-block;

  @media (hover: none) {
    &:active {
      background-color: black;
      width: 90%;
    }
  }

  @media (hover: hover) {
    &:hover {
      background-color: white;
      width: 85vw;
      height: 6vh;
    }
  }

  // &:hover {
  //   background-color: black;
  //   width: 85%;
  // }
`;
