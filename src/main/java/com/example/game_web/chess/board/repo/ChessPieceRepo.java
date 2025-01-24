package com.example.game_web.chess.board.repo;

import com.example.game_web.chess.board.entity.ChessPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChessPieceRepo extends JpaRepository<ChessPiece, Long> {
    boolean existsByRowIdxAndColIdxAndIsWhite (int rowIdx, int colIdx, boolean isWhite);
    Optional<ChessPiece> findByPieceAndIsWhite(String piece, boolean isWhite);
}
