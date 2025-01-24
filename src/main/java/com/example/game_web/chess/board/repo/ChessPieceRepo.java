package com.example.game_web.chess.board.repo;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.entity.ChessPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChessPieceRepo extends JpaRepository<ChessPiece, Long> {
    boolean existsByRowIdxAndColIdxAndIsWhite (int rowIdx, int colIdx, boolean isWhite);
    boolean existsByPieceAndIsWhiteAndChessGame(String piece, boolean isWhite, ChessGame chessGame);
    Optional<ChessPiece> findByPieceAndIsWhiteAndChessGame(String piece, boolean isWhite, ChessGame chessGame);
    Optional<ChessPiece> findByRowIdxAndColIdxAndChessGame(int rowIdx, int colIdx, ChessGame chessGame);
}
