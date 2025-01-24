package com.example.game_web.chess.movement.piece.king;

import com.example.game_web.chess.board.entity.ChessPiece;
import com.example.game_web.chess.board.repo.ChessPieceRepo;
import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KingMove {
    private final ChessPieceRepo pieceRepo;

    // All possible movement of king.
    public List<NewPosition> listMove(CurrentPosition king){
        List<NewPosition> listMove = new ArrayList<>();
        ChessPiece kingPiece = pieceRepo.findByPieceAndIsWhite("king", king.isWhite()).orElseThrow(
                ()-> new CustomException("King is not here", HttpStatus.BAD_REQUEST)
        );
        int rowPos = kingPiece.getRowIdx();
        int colPos = kingPiece.getColIdx();
        if (colPos < 7 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowPos, colPos + 1, king.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowPos).colIdx(colPos + 1).build());
        if (colPos > 0 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowPos, colPos - 1, king.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowPos).colIdx(colPos - 1).build());
        if (rowPos < 7 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowPos + 1, colPos, king.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowPos + 1).colIdx(colPos).build());
        if (rowPos > 0 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowPos - 1, colPos, king.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowPos - 1).colIdx(colPos).build());
        return listMove;
    }


}
