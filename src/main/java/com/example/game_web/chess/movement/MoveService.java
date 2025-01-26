package com.example.game_web.chess.movement;

import com.example.game_web.chess.movement.piece.bishop.BishopMove;
import com.example.game_web.chess.movement.piece.king.KingMove;
import com.example.game_web.chess.movement.piece.knight.KnightMove;
import com.example.game_web.chess.movement.piece.pawn.PawnMove;
import com.example.game_web.chess.movement.piece.queen.QueenMove;
import com.example.game_web.chess.movement.piece.rook.RookMove;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveService {
    private final BishopMove bishop;
    private final KingMove king;
    private final KnightMove knight;
    private final PawnMove pawn;
    private final QueenMove queen;
    private final RookMove rook;
    

}
