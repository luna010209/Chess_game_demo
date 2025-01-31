import React, { useEffect, useRef, useState } from 'react'
import { useParams } from 'react-router-dom'
import { board, movement, play } from '../../service/chess/ChessService';
import { Client } from '@stomp/stompjs';
import { produce } from 'immer';

const Chess = () => {
  const { gameId } = useParams();
  const [curPiece, setCurPiece] = useState(null);
  let colorBase;

  const pieceIcons = {
    pawn: "♙",
    knight: "♘",
    bishop: "♗",
    rook: "♖",
    queen: "♕",
    king: "♔",
  };
  const [boardInit, setBoardInit] = useState(Array.from({ length: 8 }, () => Array(8).fill(null)));
  useEffect(() => {
    board(gameId).then(res => {
      const newBoard = Array.from({ length: 8 }, () => Array(8).fill(null));
      res.data.forEach(piece => {
        // console.log(piece.pieceId);
        newBoard[piece.rowIdx][piece.colIdx] = piece;
      })
      setBoardInit(newBoard);
    })
  }, [gameId])

  const client = useRef(new Client({
    brokerURL: "ws://localhost:3000/game",
  }))
  useEffect(()=>{
    client.current.activate();
    client.current.onConnect= ()=>{
      client.current.subscribe('/topic/move', res=>{
        const chessPiece = JSON.parse(res.body); 
        setBoardInit(produce(draft => {
          draft[chessPiece.rowNew][chessPiece.colNew] = chessPiece;
          draft[chessPiece.rowCur][chessPiece.colCur] = null;
        }));
      })
    }
  })

  const getPiece = (piece)=>{
    setCurPiece(piece);
    console.log(piece);
  }

  const handleMove = (rowIdx, colIdx)=>{
    console.log(rowIdx, colIdx);
    if (curPiece){
      const requestData = {
        pieceId: curPiece.pieceId,
        gameId: gameId,
        piece: curPiece.piece,
        rowCur: curPiece.rowIdx,
        colCur: curPiece.colIdx,
        rowNew: rowIdx,
        colNew: colIdx,
        white: curPiece.white,
      }

      movement(requestData).then(()=>{
        play(client, requestData);
        setCurPiece(null);
      }).catch(e=>{
        alert(e.response.data);
      })  
    }
  }

  return (
    <div className='d-flex flex-wrap'>
      <div className='col-12 col-lg-8 border border-1 d-flex flex-wrap justify-content-center ps-5 pe-5'>
        <h1 className='w-100'>Class</h1>
        <div className='border border-2'
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(8, 60px)",
            gridTemplateRows: "repeat(8, 60px)",
            width: "480px",
            height: "480px",
          }}
        >
          {boardInit.flat().map((piece, index) => {
            const row = Math.floor(index / 8);
            const col = index % 8;
            if ((row + col) % 2 === 0) colorBase="darkturquoise";
            else colorBase="darkcyan";

            return (
              <div
                key={index}
                style={{
                  backgroundColor: colorBase,
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  width: "60px",
                  height: "60px",
                  cursor: "pointer",
                }}
                // onDoubleClick={e=>{e.preventDefault(); setCurPiece(null)}}
                onClick={()=>piece? getPiece(piece):handleMove(row, col)} 
              >
                {piece && 
                (<div
                  style={{
                    fontSize: "50px",
                    fontWeight: 900,
                    color: piece.white? "white": "black",
                    textAlign: "center",
                  }} 
                >
                  {pieceIcons[piece.piece]}
                </div>)}
              </div>
            );
          })}
        </div>
      </div>
      <div className='d-none d-lg-flex col-3'>

      </div>
    </div>
  )
}

export default Chess