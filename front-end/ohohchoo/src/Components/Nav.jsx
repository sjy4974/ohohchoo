import { BsHouseDoor, BsPersonCircle } from "react-icons/bs";
import styled from "styled-components";

export default function Nav({ user, mode, setMode }) {
  const onChangeMainPageBtn = () => {
    setMode(0);
  };

  const onChangeLocationBtn = () => {
    setMode(1);
  };

  const onChangeLoginBtn = () => {
    setMode(2);
  };

  return (
    <NavWrap>
      <NavContent>
        <LocationWrap onClick={onChangeLocationBtn}>
          <BsHouseDoor size='24' />
        </LocationWrap>

        <TitleWrap onClick={onChangeMainPageBtn}>
          <span>Outfit</span> Forecast
        </TitleWrap>

        <LoginWrap onClick={onChangeLoginBtn}>
          <BsPersonCircle size='24' />
        </LoginWrap>
      </NavContent>
    </NavWrap>
  );
}

const NavWrap = styled.nav`
  height: 5vh;
  line-height: 5vh;
  margin: 0px;
  padding: 0px;
  background-color: black;
`;
const NavContent = styled.div`
  display: flex;
  // text-align: center;
  justify-content: center;
`;

const LoginWrap = styled.div`
  color: white;
  margin: 0;
  padding: 0;

  position: absolute;
  top: 1vh;
  right: 5vw;
`;

const LocationWrap = styled.div`
  color: white;
  margin: 0;
  padding: 0;

  position: absolute;
  top: 1vh;
  left: 5vw;
`;

const TitleWrap = styled.div`
  color: white;
  font-size: 3vh;
  margin: 0;
  padding: 0;
  font-weight: bold;
  text-align: center;

  span {
    color: red;
  }
`;
