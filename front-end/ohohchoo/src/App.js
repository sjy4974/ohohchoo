import { Routes, Route } from "react-router-dom";
import React, { useEffect, useState } from "react";
import "./App.css";
import Nav from "./Components/Nav";
import MainPage from "./Pages/MainPage";
// import useGeolocation from "./hooks/useGeolocation";
import SelectLocationPage from "./Pages/SelectLocationPage";
import LoginPage from "./Pages/LoginPage";
import LoginLoading from "./Pages/LoginLoadingPage";

// const Layout = () => {
//   return (
//     <div>
//       <Nav />

//       <Outlet />
//     </div>
//   );
// };

function App() {
  const [location, setLocation] = useState({
    loaded: false,
    coordinates: { lat: 0, lng: 0 },
  });
  // const [user, setUser] = useState(false);

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
          <Route path='/' element={<MainPage location={location} />} />
          <Route path='/login' element={<LoginPage />} />
          <Route path='/login-request' element={<LoginLoading />} />
          <Route
            path='/location'
            element={
              <SelectLocationPage
                location={location}
                setLocation={setLocation}
              />
            }
          />
        </Routes>
      )}
    </div>
  );
}

export default App;
