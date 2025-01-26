package com.example.game_web.chess;

import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.chess.play.PlayService;
import com.example.game_web.chess.play.dto.RequestDto;
import com.example.game_web.chess.play.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChessSocket {
    private final PlayService playService;
    @MessageMapping("/move")
    @SendTo("/topic/move")
    public ResponseDto makeMove(RequestDto requestDto){
        return playService.play(requestDto);
    }

    @MessageMapping("/pawn")
    @SendTo("/topic/pawn")
    public ResponseDto pawnTo(RequestDto requestDto){
        return playService.pawnTo(requestDto);
    }
}
