import React, { useEffect, useState } from "react";
import { Routes, Route } from "react-router-dom";

import Nav from "./Components/Nav";
import MainPage from "./Pages/MainPage";
import SelectLocationPage from "./Pages/SelectLocationPage";

import "./App.css";

function App() {
  const [location, setLocation] = useState({
    loaded: false,
    coordinates: { lat: 0, lng: 0 },
  });
  const [user, setUser] = useState("김현수");
  const [city, setCity] = useState("");
  const [result, setResult] = useState({});

  useEffect(() => {
    // navigator 객체 안에 geolocation이 없다면
    // 위치 정보가 없는 것.
    if (!("geolocation" in navigator)) {
      onError({
        code: 0,
        message: "Geolocation not supported",
      });
    }

    navigator.geolocation.getCurrentPosition(onSuccess, onError);
    console.log("geoLocation 실행");
  }, []);

  console.log("useGeolocation: ", location);

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

  return (
    <div className='app'>
      <Nav></Nav>
      {location.loaded && (
        <Routes>
          <Route
            path='/'
            element={
              <MainPage
                location={location}
                user={user}
                city={city}
                setCity={setCity}
                result={result}
                setResult={setResult}
              />
            }
          />
          <Route
            path='/location'
            element={
              <SelectLocationPage
                location={location}
                setLocation={setLocation}
                user={user}
                city={city}
                setCity={setCity}
                result={result}
                setResult={setResult}
              />
            }
          />
        </Routes>
      )}
    </div>
  );
}

export default App;
