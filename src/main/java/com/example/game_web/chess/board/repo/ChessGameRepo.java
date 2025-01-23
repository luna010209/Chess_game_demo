package com.example.game_web.chess.board.repo;

import com.example.game_web.chess.board.entity.ChessGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessGameRepo extends JpaRepository<ChessGame, Long> {
}
