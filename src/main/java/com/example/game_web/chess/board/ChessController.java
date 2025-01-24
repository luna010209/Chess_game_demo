package com.example.game_web.chess.board;

import com.example.game_web.chess.board.service.ChessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("chess")
public class ChessController {
    private final ChessService chessService;
    @PostMapping
    public String startGame(){
        chessService.initializeGame();
        return "Chess is ready for you!!!";
    }
}
