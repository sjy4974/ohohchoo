import React, { useEffect, useState } from "react";
import MainPage from "./Pages/MainPage";
import styled from "styled-components";

function App() {
  const [location, setLocation] = useState({
    loaded: false,
    coordinates: { lat: 0, lng: 0 },
  });

  useEffect(() => {
    // navigator 객체 안에 geolocation이 없다면
    // 위치 정보가 없는 것.
    if (!("geolocation" in navigator)) {
      onError({
        code: 0,
        message: "Geolocation not supported",
      });
    }

    console.log("geoLocation 실행");
    navigator.geolocation.getCurrentPosition(onSuccess, onError);
  }, []);

  // 성공에 대한 로직
  const onSuccess = (location) => {
    setLocation({
      loaded: true,
      coordinates: {
        lat: location.coords.latitude,
        lng: location.coords.longitude,
      },
    });
  };

  // 에러에 대한 로직
  const onError = (error) => {
    setLocation({
      loaded: true,
      error,
    });
  };

  console.log("useGeolocation: ", location);

  return (
    <AppContainer>
      {location.loaded && <MainPage location={location} />}
    </AppContainer>
  );
}

export default App;

const AppContainer = styled.div`
  @media only screen and (min-width: 375px) and (max-width: 480px) {
    width: 100vw;
    height: 100vh;
    text-align: center;
    align-items: center;
  }
`;
