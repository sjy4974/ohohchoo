import React, { useState } from "react";
import styled from "styled-components";

export default function OptionButton({ title, OptionList, setOption }) {
  const [checkedElement, setCheckedElement] = useState(-1);

  const onChangeRadioButton = (e) => {
    setCheckedElement(Number(e.target.value));
    setOption(Number(e.target.value));
  };

  return (
    <OptionWrap>
      <div>{title}</div>
      <div className='RadioComp'>
        {OptionList.map((Option, index) => (
          <RadioWrap key={index}>
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
      </div>
    </OptionWrap>
  );
}

const OptionWrap = styled.div`
  .RadioComp {
    display: flex;
  }
`;

const RadioWrap = styled.label`
  display: flex;
  align-items: center;
  margin-bottom: 8px;

  input {
    display: none;
  }

  .OptionText {
    margin-left: 8px;
  }

  .checked {
    background-color: #ff7f00;
  }

  .unchecked {
    background-color: #808080;
  }
`;
