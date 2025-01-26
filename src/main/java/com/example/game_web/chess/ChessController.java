package com.example.game_web.chess;

import com.example.game_web.chess.board.service.ChessInitialService;
import com.example.game_web.chess.play.PlayService;
import com.example.game_web.chess.play.dto.RequestDto;
import com.example.game_web.chess.play.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("chess")
public class ChessController {
    private final ChessInitialService chessInitialService;
    private final PlayService playService;
    @PostMapping
    public String startGame(){
        chessInitialService.initializeGame();
        return "Chess is ready for you!!!";
    }

    @PostMapping("play")
    public ResponseDto play(@RequestBody RequestDto request){
        return playService.play(request);
    }
}
