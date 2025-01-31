package com.example.game_web.chess;

import com.example.game_web.chess.board.service.ChessInitialService;
import com.example.game_web.chess.play.PlayService;
import com.example.game_web.chess.play.dto.RequestDto;
import com.example.game_web.chess.play.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("chess-game")
public class ChessController {
    private final ChessInitialService chessInitialService;
    private final PlayService playService;
    @PostMapping
    public String startGame(){
        chessInitialService.initializeGame();
        return "Chess is ready for you!!!";
    }

    @GetMapping("{gameId}")
    public List<ResponseDto> allPieces(@PathVariable("gameId") Long gameId){
        return chessInitialService.listPieces(gameId);
    }

    @PostMapping("play")
    public ResponseDto play(@RequestBody RequestDto request){
        return playService.play(request);
    }
}
