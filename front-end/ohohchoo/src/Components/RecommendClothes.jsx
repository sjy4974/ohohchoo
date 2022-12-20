import React from "react";
import { dummy } from "../ClothesDummy";
import styled from "styled-components";

function RecommendClothes({ temp }) {
  return (
    <div>
      <div>RecommendClothes Components</div>
      <div>props temp : {temp} </div>
      <ClothesContainer>
        {dummy.map((item) => (
          <div key={item.idx}>
            <ItemBox src={item.img} title='jacket icons' />
          </div>
        ))}
      </ClothesContainer>
      {/* <button onClick={}></button> */}
    </div>
  );
}

export default RecommendClothes;

const ClothesContainer = styled.div`
  display: flex;
`;
const ItemBox = styled.img`
  width: 30px;
`;
