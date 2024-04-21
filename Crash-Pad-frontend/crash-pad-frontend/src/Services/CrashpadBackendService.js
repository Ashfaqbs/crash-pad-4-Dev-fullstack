// npm i axios

import axios from "axios";
const BACKEND_Rest_API_BASE_URL = "http://localhost:8080/mainError";


export const listErrors = () => {
    return axios.get(BACKEND_Rest_API_BASE_URL);
  };
   
