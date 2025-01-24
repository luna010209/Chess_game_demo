package com.example.game_web.chess.board.repo;

import com.example.game_web.chess.board.entity.ChessPiece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessPieceRepo extends JpaRepository<ChessPiece, Long> {
}
