import { Outlet, Routes, Route } from "react-router-dom";
// import React, { useEffect, useState } from "react";
import "./App.css";
import Nav from "./Components/Nav";
import MainPage from "./Pages/MainPage";
import useGeolocation from "./hooks/useGeolocation";

const Layout = () => {
  return (
    <div>
      <Nav />

      <Outlet />
    </div>
  );
};

function App() {
  const location = useGeolocation();

  return (
    <div className='app'>
      {location.loaded && (
        <Routes>
          <Route path='/' element={<Layout />}>
            <Route index element={<MainPage location={location} />} />
          </Route>
        </Routes>
      )}
    </div>
  );
}

export default App;
