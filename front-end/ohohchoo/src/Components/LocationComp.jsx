import React, { useState, useEffect } from "react";
import axios from "../API/axios";
import requests from "../API/request";
import styled from "styled-components";

function Location({ city, setCity, setLocationModal }) {
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

  const ModalHandler = () => {
    setLocationModal(false);
  };

  const SelectLocation = (title) => {
    setCity(title);
  };
  return (
    <LocationWrap>
      <LocationContent>
        <LocationContainer onClick={ModalHandler}>{city}</LocationContainer>
      </LocationContent>
      {locationList.map((location, index) => (
        <LocationContainer
          key={index}
          onClick={() => {
            ModalHandler();
            SelectLocation(location.title);
          }}
        >
          <div>{index}</div>
        </LocationContainer>
      ))}
      <LocationContent></LocationContent>
      uselocation page
    </LocationWrap>
  );
}

export default Location;

const LocationWrap = styled.div`
  width: 100vw;
  height: 100vh;
`;

const LocationContent = styled.div``;

const LocationContainer = styled.button``;
