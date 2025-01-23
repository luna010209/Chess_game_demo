package com.example.game_web.chess.board.service;

import com.example.game_web.chess.board.repo.ChessGameRepo;
import org.springframework.stereotype.Service;

@Service
public class ChessService {
    private final ChessGameRepo gameRepo;
    public ChessService(ChessGameRepo gameRepo){
        this.gameRepo = gameRepo;

    }

    public void initializeGame(){
        
    }
}
