import React, { useState } from "react";
import LocationComp from "../Components/LocationComp";
import styled from "styled-components";

export default function Nav({ city, setCity, user }) {
  const [locationModal, setLocationModal] = useState(false);
  const [loginModal, setLoginModal] = useState(false);

  const onChangeLocationBtn = () => {
    setLocationModal(true);
  };
  return (
    <NavWrap>
      <NavContent>
        <div>
          {locationModal ? (
            <LocationComp
              city={city}
              setCity={setCity}
              user={user}
              setLocationModal={setLocationModal}
            />
          ) : (
            <LocationWrap
              props={loginModal}
              onClick={onChangeLocationBtn}
              src='https://cdn-icons-png.flaticon.com/128/3916/3916996.png'
              alt=''
            ></LocationWrap>
          )}
        </div>
        <div>outFit Forecast</div>
        <div>
          {loginModal ? (
            <LocationComp
              city={city}
              setCity={setCity}
              user={user}
              setLocationModal={setLocationModal}
            />
          ) : (
            <LoginWrap props={locationModal} onClick={onChangeLocationBtn}>
              login page open
            </LoginWrap>
          )}
        </div>
      </NavContent>
    </NavWrap>
  );
}

const NavWrap = styled.nav`
  margin: 0px;
  padding: 0px;
  // background-color: black;
`;
const NavContent = styled.div`
  display: flex;
  justify-content: space-between;
`;

const LocationWrap = styled.img`
  color: white;
  width: 8vw;
  margin: 5px;
  padding: 5px;
  display: ${(props) => (props.props ? "none" : "flex")};
`;

const LoginWrap = styled.div`
  color: white;
  width: 10vw;
  margin: 5px;
  padding: 5px;
  display: ${(props) => (props.props ? "none" : "flex")};
`;
