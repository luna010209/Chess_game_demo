import React from 'react'

const ChessIcons = ({piece}) => {
  const pieceIcons = {
    pawn: "♙",
    knight: "♘",
    bishop: "♗",
    rook: "♖",
    queen: "♕",
    king: "♔",
  };

  return (
    <div
      style={{
        fontSize: "50px",
        fontWeight: 900,
        color: piece.white ? "white" : "black",
        textAlign: "center",
      }}
    >
      {pieceIcons[piece.piece]}
    </div>
  )
}

export default ChessIcons