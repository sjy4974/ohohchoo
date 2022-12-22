import React from "react";
import { dummy } from "../ClothesDummy";
import styled from "styled-components";

function RecommendClothes({ temp }) {
  return (
    <RecommendClothesWrap>
      {/* <div>RecommendClothes Components</div>
      <div>props temp : {temp} </div> */}
      <ClothesContainer>
        {dummy.map((item) => (
          <div>
            <div className='category'>
              <div className='categoryTitle'>{item.temp}</div>
            </div>
            <ClothesWrap key={item.idx}>
              <ItemBox src={item.img} title='jacket icons' />
            </ClothesWrap>
          </div>
        ))}
      </ClothesContainer>
      {/* <button onClick={}></button> */}
    </RecommendClothesWrap>
  );
}

export default RecommendClothes;

const RecommendClothesWrap = styled.div`
  width: 90vw;
  margin: 0 5vw;
  padding-top: 3vw;
  z-index: -1;
  background-color: #d3d3d3;
  text-align: center;
  justify-content: center;
  border-radius: 1vw 1vw 0 0;
`;

const ClothesContainer = styled.div`
  width: 90vw;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;

  .category {
    height: 5vw;
    margin-bottom: 0;
    margin-top: 1vw;
    text-align: left;

    .categoryTitle {
    display: block;
    height: 5vw;
    margin: 0;
    width: 20vw;
    line-height : 5vw;
    text-align: center;
    background-color: #ff7f00;
    // border-radius:  5% / 20% 20% 0 0;
    border-top-left-radius: 1vw;
    border-top-right-radius: 1vw;
  }
`;
const ItemBox = styled.img`
  // display: flex;
  width: 28vw;
  height: 28vw;
  // object-fit: fill;
  margin: 0;
  padding: 0;
`;

const ClothesWrap = styled.div`
  // display: flex;
  width: 35vw;
  heigth: 35vw;
  background-color: white;
  border-radius: 0 1vw 1vw 1vw;
  margin-bottom: 3vw;
  text-align: center;
  justify-contet: center;
`;
