package com.example.game_web.chess.movement;

import com.example.game_web.chess.board.entity.ChessPiece;
import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.chess.movement.piece.bishop.BishopMove;
import com.example.game_web.chess.movement.piece.king.KingMove;
import com.example.game_web.chess.movement.piece.knight.KnightMove;
import com.example.game_web.chess.movement.piece.pawn.PawnMove;
import com.example.game_web.chess.movement.piece.queen.QueenMove;
import com.example.game_web.chess.movement.piece.rook.RookMove;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoveService {
    private final BishopMove bishop;
    private final KingMove king;
    private final KnightMove knight;
    private final PawnMove pawn;
    private final QueenMove queen;
    private final RookMove rook;
    public boolean checkMove(CurrentPosition current, NewPosition newPosition){
        List<NewPosition> newPositions;
        if (current.getPiece().equals("king")){
            newPositions = king.listMove(current);
            if (newPositions.contains(newPosition))
                return true;
        } else if (current.getPiece().equals("bishop")){
            newPositions = bishop.listMove(current);
            if (newPositions.contains(newPosition))
                return true;
        } else if (current.getPiece().equals("knight")){
            newPositions = knight.listMove(current);
            if (newPositions.contains(newPosition))
                return true;
        } else if (current.getPiece().equals("pawn")){
            newPositions = pawn.listMove(current);
            if (newPositions.contains(newPosition))
                return true;
        } else if (current.getPiece().equals("queen")){
            newPositions = queen.listMove(current);
            if (newPositions.contains(newPosition))
                return true;
        } else if (current.getPiece().equals("rook")){
            newPositions = rook.listMove(current);
            if (newPositions.contains(newPosition))
                return true;
        }
        return false;
    }

}
