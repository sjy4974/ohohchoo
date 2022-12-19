import axios from "axios";

const instance = axios.create({
  baseURL: "https://api.themoviedb.org/3",
  params: {
    api_key: "c4c61062f0586b0c14f0df48b5eda1f1",
    language: "ko-KR",
  },
});

export default instance;
