import React, { useState } from "react";
import styled, { css } from "styled-components";

export default function OptionButton({ title, OptionList, setOption }) {
  const [checkedElement, setCheckedElement] = useState(-1);

  const onChangeRadioButton = (e) => {
    setCheckedElement(Number(e.target.value));
    setOption(Number(e.target.value));
  };

  return (
    <OptionWrap>
      <OptionTitle>
        <div>{title}</div>
      </OptionTitle>

      <RadioGroup>
        {OptionList.map((Option, index) => (
          <RadioWrap
            key={index}
            length={OptionList.length % 2 === 0 ? true : false}
          >
            <input
              type='radio'
              value={index}
              checked={checkedElement === index}
              onChange={onChangeRadioButton}
            />
            {checkedElement === index ? (
              <div className={"OptionText checked"}>{Option}</div>
            ) : (
              <div className={"OptionText unchecked"}>{Option}</div>
            )}
          </RadioWrap>
        ))}
      </RadioGroup>
    </OptionWrap>
  );
}

const OptionWrap = styled.div`
  text-align: center;
`;

const OptionTitle = styled.h5`
  text-align: start;
  margin-left: 20vw;
  margin-top: 2px;
  margin-bottom: 0;
  color: gray;
`;

const RadioGroup = styled.div`
  display: flex;
  margin: 0 10%;
  justify-content: center;
  text-align: center;
`;

const RadioWrap = styled.label`
  // width: 40vw;
  display: block;
  font-size: 16px;
  margin: 0px 1px;
  padding: 0px;
  text-align: center;

  input {
    display: none;
  }

  .OptionText {
    // margin-left: 8px;
    ${(props) =>
      props.length
        ? css`
            width: 30vw;
            font-size: 10px;
          `
        : css`
            width: 20vw;
            font-size: 10px;
          `}
    padding: 3px 0;
    margin: 0px;
    height: 3vw;
    line-height: 3vw;
    font-size: 1em;
    @media (max-width: 480px) {
      font-size: 0.5em;
    }
    // border-radius: 30%;
  }

  .checked {
    color: white;
    background-color: #ff7f00;
  }

  .unchecked {
    color: white;
    background-color: #d3d3d3;
  }
`;
