import axios from 'axios';

export const sendCode = (email) =>{
  return axios.post("/code/send", email);
}
export const verifyEmail = (verify)=>{
  return axios.post("/code/verify", verify)
}

export const newUser = (request)=>{
  return axios.post("/users", request);
}

