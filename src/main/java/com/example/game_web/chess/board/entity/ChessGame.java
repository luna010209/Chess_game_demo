package com.example.game_web.chess.board.entity;


import com.example.game_web.base.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChessGame extends EntityBase {
    private boolean isWhiteTurn= true;
    @OneToMany(mappedBy = "chessGame")
    private final List<ChessPiece> pieces = new ArrayList<>();
}
