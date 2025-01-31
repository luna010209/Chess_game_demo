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