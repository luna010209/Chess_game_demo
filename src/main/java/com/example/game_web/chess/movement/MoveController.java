package com.example.game_web.chess.movement;

import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.chess.movement.piece.king.KingMove;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("move")
public class MoveController {
    private final MoveService moveService;
    @PostMapping
    public List<NewPosition> listMove(@RequestBody CurrentPosition current){

        if (current.getPiece().equals("king")){

        }
        return moveService.validMove(current);
    }
}
