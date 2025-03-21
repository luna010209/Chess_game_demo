import React, { useEffect, useRef, useState } from 'react'
import { useParams } from 'react-router-dom'
import { board, movement, play } from '../../service/chess/ChessService';
import { Client } from '@stomp/stompjs';
import ChessIcons from './ChessIcons';
import ChessNotification from './ChessNotification';

const Chess = () => {
  const { gameId } = useParams();
  const [curPiece, setCurPiece] = useState(null);
  let colorBase;
  // let whiteTurn = true;

  const [boardInit, setBoardInit] = useState(Array.from({ length: 8 }, () => Array(8).fill(null)));
  // const boardRef = useRef(boardInit);

  useEffect(() => {
    board(gameId).then(res => {
      const newBoard = Array.from({ length: 8 }, () => Array(8).fill(null));
      res.data.forEach(piece => {
        // console.log(piece.pieceId);
        newBoard[piece.rowIdx][piece.colIdx] = piece;
      })
      setBoardInit(newBoard);
    }).catch(e=>{
      alert(e.response.data);
    });
  }, [gameId])
  // Update boardInit inside client stomp
  // useEffect(() => {
  //   boardRef.current = boardInit;
  // }, [boardInit]);
  
  // console.log(boardInit[0][0]);

  const client = useRef(new Client({
    brokerURL: "ws://localhost:3000/game",
  }))
  useEffect(()=>{
    client.current.activate();
    client.current.onConnect= ()=>{
      client.current.subscribe('/topic/move', res=>{
        const chessPiece = JSON.parse(res.body); 
        // console.log(chessPiece);
        board(gameId).then(res => {
          const newBoard = Array.from({ length: 8 }, () => Array(8).fill(null));
          res.data.forEach(piece => {
            // console.log(piece.pieceId);
            newBoard[piece.rowIdx][piece.colIdx] = piece;
          })
          setBoardInit(newBoard);
        }).catch(e=>{
          alert(e.response.data);
        });
      })
    }
  })

  const handleMove = (piece, rowIdx, colIdx)=>{
    if (piece && piece.white===piece.whiteTurn) setCurPiece(piece);
    else if (curPiece){
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

      movement(requestData).then((res)=>{
        play(client, res.data);
        setCurPiece(null);
        // whiteTurn=!whiteTurn;
      }).catch(e=>{
        alert(e.response.data);
        setCurPiece(null);
      })  
    }
  }

  return (
    <div className='d-flex flex-wrap p-3 justify-content-center'>
      <div className='col-12 col-lg-8 border border-3 d-flex flex-wrap justify-content-center pt-3 pb-3'>
        <h1 className='w-100 text-center text-bg-success rounded'>Luna's chess game</h1>
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
                onClick={()=>handleMove(piece, row, col)} 
              >
                {piece && (<ChessIcons piece={piece}/>)}
              </div>
            );
          })}
        </div>
      </div>
      <div className='d-none d-lg-flex flex-wrap col-3 border justify-content-center align-items-center p-2'>
        {(<ChessNotification boardInit={boardInit} gameId={gameId}/>)}
          {/* <div className='w-100 d-flex flex-wrap justify-content-center'>
            <h1 className='bg-success-subtle text-white text-center'>White's turn</h1>
            <h3 className='text-center text-danger'>Your king is threatened right now!!!</h3>
            <button className='text-center btn btn-outline-success'>Reset game</button>
          </div>
           */}
      </div>
    </div>
  )
}

export default Chess