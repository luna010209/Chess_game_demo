import React, { useEffect, useState } from 'react'
import { game, resetGame } from '../../service/chess/ChessService';
import { useNavigate } from 'react-router-dom';

const ChessNotification = ({boardInit, gameId}) => {
  const navigator = useNavigate();

  const [text, setText]= useState("Now is white pieces' turn!");
  const [font, setFont]= useState("bg-success text-white");
  const [display, setDisplay] = useState('d-none');
  const [reset, setReset] = useState("d-none");
  useEffect(()=>{
    game(gameId).then(res=>{
      const piece = res.data;
      // console.log(piece);
      if (piece.status==='DRAW') {
        setText("DRAW");
        setFont("bg-primary-subtle text-primary-emphasis");
        setReset("");
      } else if (piece.status==='WIN'){
        if (piece.whiteTurn) {
          setText("White win!");
          setFont("bg-warning text-white");
        } else {
          setText("Black win!");
          setFont("bg-warning-subtle text-dark");
        }
        setReset("");
      } else{
        if (piece.whiteTurn) {
          setText("Now is white pieces' turn!");
          setFont("bg-success text-white");
        } else {
          setText("Now is black pieces' turn!");
          setFont("bg-success-subtle text-dark");
        }
        setReset("d-none");
      }
      if (piece.kingDanger) setDisplay('');
      else setDisplay('d-none');
    })
  }, [boardInit])

  const resetButton = (e)=>{
    e.preventDefault();
    resetGame(gameId).then(res=>{
      alert("Start new game!!!")
      navigator("/chess/"+ res.data.id);
      window.location.reload();
    }).catch(e=>{
      alert(e.response.data);
    });
  }
  
  return (
    <div className='d-flex flex-wrap justify-content-center'>
      <h1 className={`${font} text-center w-100`}>{text}</h1>
      <h3 className={`w-100 text-center text-danger ${display}`}>King is threatened right now!!!</h3>
      <button className={`btn btn-outline-success ${reset}`} onClick={resetButton}>Reset game</button>
    </div>
  )
}

export default ChessNotification