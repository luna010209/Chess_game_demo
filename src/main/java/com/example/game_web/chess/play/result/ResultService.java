package com.example.game_web.chess.play.result;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.entity.ChessPiece;
import com.example.game_web.chess.board.repo.ChessGameRepo;
import com.example.game_web.chess.board.repo.ChessPieceRepo;
import com.example.game_web.chess.movement.MoveService;
import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.chess.movement.piece.king.KingMove;
import com.example.game_web.chess.movement.piece.pawn.PawnMove;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ChessPieceRepo pieceRepo;
    private final ChessGameRepo gameRepo;
    private final PawnMove pawnMove;
    private final KingMove kingMove;
    private final MoveService moveService;
    public void draw(Long gameId, boolean isCurWhite){
        ChessGame game = gameRepo.findById(gameId).orElseThrow(
                ()-> new CustomException("No exist game", HttpStatus.BAD_REQUEST)
        );
        List<ChessPiece> pieces = pieceRepo.findByChessGame(game);
        if (pieces.size()==2)
            game.setStatus(ChessGame.Status.DRAW);
        else if (pieces.size()==3 &&
                (pieceRepo.existsByPieceAndChessGame("pawn", game) ||
                pieceRepo.existsByPieceAndChessGame("bishop", game) ||
                pieceRepo.existsByPieceAndChessGame("knight", game)))
            game.setStatus(ChessGame.Status.DRAW);

        // Stalemate check!!!
        List<ChessPiece> piecesOneSide = pieceRepo.findByWhiteAndChessGame(isCurWhite, game);
        if (piecesOneSide.size()<4){
            List<NewPosition> validPositions = new ArrayList<>();
            for (ChessPiece piece: piecesOneSide){
                if (piece.getPiece().equals("pawn")){
                    validPositions.addAll(pawnMove.listMove(new CurrentPosition(gameId, piece.getRowIdx(), piece.getColIdx(), piece.isWhite(), "pawn")));
                } else if (piece.getPiece().equals("king")){
                    for (NewPosition p: kingMove.listMove(new CurrentPosition(gameId, piece.getRowIdx(), piece.getColIdx(), piece.isWhite(), "king"))){
                        for (ChessPiece cp: pieceRepo.findByWhiteAndChessGame(!piece.isWhite(), game)){
                            if (!moveService.validMove(new CurrentPosition(gameId, cp.getRowIdx(), cp.getColIdx(), cp.isWhite(), cp.getPiece()))
                                    .contains(p)) validPositions.add(p);
                        }
                    }
                } else validPositions.add(new NewPosition());
            }
            if (validPositions.isEmpty() && !game.isKingDanger())
                game.setStatus(ChessGame.Status.DRAW);
        }
        gameRepo.save(game);
        return;
    }

    public void win(Long gameId){
        ChessGame game = gameRepo.findById(gameId).orElseThrow(
                ()-> new CustomException("No exist game", HttpStatus.BAD_REQUEST)
        );
        if (!pieceRepo.existsByPieceAndWhiteAndChessGame("king", !game.isWhiteTurn(), game))
            game.setStatus(ChessGame.Status.WIN);
        gameRepo.save(game);
        return;
    }
}
