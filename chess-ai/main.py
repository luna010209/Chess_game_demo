from fastapi import FastAPI;
import chess;
import chess.engine;
from stockfish import Stockfish;

app= FastAPI();

stockfish = Stockfish(path="stockfish")

@app.get("/ai/")
def read_root():
  return {"message": "Chess AI Server is running"}

# API to get the best move
@app.post("/ai/move/")
def get_best_move(fen: str):
  stockfish.set_fen_position(fen)
  best_move = stockfish.get_best_move()
  return {"best_move": best_move}