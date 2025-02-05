import axios from "axios";

export const board = (gameId)=>{
  return axios.get("/chess-game/"+gameId);
}

export const movement = (request)=>{
  return axios.post("/chess-game/play", request);
}

export const play = (client, requestDto)=>{
  return client.current.publish({
      destination: "/app/move",
      body: JSON.stringify(requestDto),
  })
}

export const game = (gameId)=>{
  return axios.get("/chess-game/game"+gameId);
}

export const resetGame= (gameId)=>{
  return axios.post("/chess-game/reset/"+gameId);
}