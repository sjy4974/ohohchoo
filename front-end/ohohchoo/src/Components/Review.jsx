import React, { useState, useEffect } from "react";
import axios from "../API/axios";
import requests from "../API/request";
import styled, { css } from "styled-components";

function Review({ city, user, setReviewModal, ModalHandler }) {
  const [review, setReview] = useState([]);

  useEffect(() => {
    console.log("review 데이터 불러오기");
    fetchReviewData();
  }, []);

  const fetchReviewData = async () => {
    const request = await axios.get(requests.fetchActionMovies);
    setReview(request.data.results);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // axios로 user정보와 댓글정보를 넘겨준다.
    //

    //  이 부분에 작성하기 !!! //

    //
    console.log("e : ", e);
    console.log("가져온 데이터: ", e.target[0].value);
    const newReview = [...review, { original_title: e.target[0].value }];
    setReview(newReview);
  };

  console.log("review", review);

  return (
    <ReviewListWrap>
      <ReviewTitleWrap>
        <div>
          <span>R</span>eview
        </div>
        <div onClick={ModalHandler}>x</div>
      </ReviewTitleWrap>
      <RadioGroup></RadioGroup>
      <ReviewListContent>
        <div>
          <form onSubmit={handleSubmit}>
            <input type='text' name='comment' placeholder='댓글 달기...' />
            <button type='submit'>등록</button>
          </form>
        </div>
        <div>
          {review.map((data, index) => (
            <ReviewWrap key={index}>
              {index} : {data.original_title}
            </ReviewWrap>
          ))}
        </div>
      </ReviewListContent>
    </ReviewListWrap>
  );
}

export default Review;

const ReviewListWrap = styled.div`
  position: relative;
  margin: 0 5vw;
  background-color: black;
  opacity: 90%;
  border-radius: 1vw;
  position: absolute;
  top: 28vh;
  left: 0;
  right: 0;
  bottom: 0;
`;

const ReviewTitleWrap = styled.div`
  height: 8vh;
  display: flex;
  justify-content: space-between;
  font-weight: bold;
  div {
    font-size: 5vw;
    color: white;
    margin: 5vw;

    span {
      color: red;
    }
  }
`;

const RadioGroup = styled.div`
  height: 3vh;
  background-color: red;
  margin: 0;
  padding: 0;
`;

const ReviewListContent = styled.div`
  position: absolute;
  top: 11vh;
  left: 5vw;
  right: 5vw;
  bottom: 5vh;
  background-color: gray;
`;
const RegisterWrap = styled.div``;

const ReviewWrap = styled.div`
  color: white;
`;
