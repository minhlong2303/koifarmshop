import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  // baseURL: "https://66dd9794f7bcc0bbdcde7d3c.mockapi.io/",
  //baseURL: "http://103.200.20.149:8081/api/",
});

// làm hành động gì đó trước khi call api
const handleBefore = (config) => {
  const token = localStorage.getItem("token");
  config.headers["Authorization"] = `Bearer ${token}`;
  return config;
};

const handleError = (error) => {
  console.log(error);
};

api.interceptors.request.use(handleBefore, handleError);

export default api;
