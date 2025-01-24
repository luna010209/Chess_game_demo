package com.example.game_web.chess;

import com.example.game_web.chess.board.service.ChessInitialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("chess")
public class ChessController {
    private final ChessInitialService chessInitialService;
    @PostMapping
    public String startGame(){
        chessInitialService.initializeGame();
        return "Chess is ready for you!!!";
    }
}
