import React, { useState, useEffect } from "react";
import axios from "../API/axios";
import requests from "../API/request";

function Review({ city, user }) {
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
    <section>
      <div>
        {review.map((data, index) => (
          <div key={index}>
            {index} : {data.original_title}
          </div>
        ))}
      </div>
      {user && (
        <div>
          <form onSubmit={handleSubmit}>
            <input type='text' name='comment' placeholder='댓글 달기...' />
            <button type='submit'>등록</button>
          </form>
        </div>
      )}
    </section>
  );
}

export default Review;
