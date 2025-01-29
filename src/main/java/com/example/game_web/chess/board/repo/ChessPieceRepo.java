package com.example.game_web.chess.board.repo;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.entity.ChessPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChessPieceRepo extends JpaRepository<ChessPiece, Long> {
    boolean existsByPieceAndWhiteAndChessGame(String piece, boolean isWhite, ChessGame chessGame);
    boolean existsByRowIdxAndColIdxAndWhiteAndChessGame(int rowIdx, int colIdx, boolean isWhite, ChessGame chessGame);
    boolean existsByRowIdxAndColIdxAndChessGame(int rowIdx, int colIdx, ChessGame chessGame);
    boolean existsByPieceAndChessGame(String piece, ChessGame chessGame);

    Optional<ChessPiece> findByPieceAndWhiteAndChessGame(String piece, boolean isWhite, ChessGame chessGame);
    Optional<ChessPiece> findByRowIdxAndColIdxAndChessGame(int rowIdx, int colIdx, ChessGame chessGame);
    List<ChessPiece> findByChessGame(ChessGame chessGame);
    List<ChessPiece> findByWhiteAndChessGame(boolean isWhite, ChessGame chessGame);

    @Query("SELECT p FROM ChessPiece p JOIN FETCH p.chessGame WHERE p.id=:id")
    ChessPiece pieceWithGame(@Param("id") Long id);
}
