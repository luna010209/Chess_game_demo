package com.example.game_web.chess.play;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.entity.ChessPiece;
import com.example.game_web.chess.board.repo.ChessGameRepo;
import com.example.game_web.chess.board.repo.ChessPieceRepo;
import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.chess.movement.piece.king.KingMove;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayService {
    private final ChessPieceRepo pieceRepo;
    private final ChessGameRepo gameRepo;

    public ChessGame play(CurrentPosition current, NewPosition newPosition, Long gameId){
        ChessGame game = gameRepo.findById(gameId).orElseThrow(
                ()-> new CustomException("No exist game", HttpStatus.BAD_REQUEST)
        );
        ChessPiece pieceCurrent = pieceRepo.findByRowIdxAndColIdxAndChessGame(current.getRowIdx(), current.getColIdx(), game)
                .orElseThrow(()-> new CustomException("No exist any pieces here", HttpStatus.BAD_REQUEST));
//        ChessGame game = pieceCurrent.getChessGame();
        // Check turn of player
        if (pieceCurrent.isWhite()!= game.isWhiteTurn())
            throw new CustomException("It's not your turn", HttpStatus.BAD_REQUEST);
        // Check if current piece can move or not
        if (pieceCurrent.getPiece().equals("king")){
            List<NewPosition> newPositions = new KingMove(gameRepo,  pieceRepo).listMove(current);
            if (newPositions.isEmpty() || !newPositions.contains(newPosition))
                throw new CustomException("You cannot move here", HttpStatus.BAD_REQUEST);
        }

        // ....

        // Update new position
        Optional<ChessPiece> pieceNewPos = pieceRepo.findByRowIdxAndColIdxAndChessGame(newPosition.getRowIdx(), newPosition.getColIdx(), game);
        ChessPiece pieceNew;
        if (pieceNewPos.isEmpty()){
            pieceNew = ChessPiece.builder()
                    .piece(pieceCurrent.getPiece())
                    .rowIdx(newPosition.getRowIdx())
                    .colIdx(newPosition.getColIdx())
                    .isWhite(pieceCurrent.isWhite())
                    .chessGame(game)
                    .build();
        } else {
            pieceNew = pieceNewPos.get();
            pieceNew.setPiece(pieceCurrent.getPiece());
            pieceNew.setWhite(pieceCurrent.isWhite());
        }
        pieceRepo.save(pieceNew);

        // Delete current(old) position
        pieceRepo.delete(pieceCurrent);
        // Change turn
        game.setWhiteTurn(!game.isWhiteTurn());

        // Check if king is threatened or not
            // 1. List of position that new piece can move
        List<NewPosition> listMoveOfNewPos = new KingMove(gameRepo, pieceRepo).listMove(CurrentPosition.builder()
                        .rowIdx(pieceNew.getRowIdx()).colIdx(pieceNew.getColIdx())
                        .piece(pieceNew.getPiece()).isWhite(pieceNew.isWhite())
                .build());

            // 2. Get position of opponent's king
        ChessPiece king = pieceRepo.findByPieceAndIsWhiteAndChessGame("king", !game.isWhiteTurn(), game).orElseThrow(
                ()-> new CustomException("You lose", HttpStatus.BAD_REQUEST)
        );
            // 3. Check if king exists into list movement or not.
        if (listMoveOfNewPos.contains(new NewPosition(king.getRowIdx(), king.getColIdx())))
            game.setKingDanger(true);

        return gameRepo.save(game);
    }

    public String finish(Long chessGameId){
        ChessGame game = gameRepo.findById(chessGameId).orElseThrow(
                ()-> new CustomException("No exist this game", HttpStatus.BAD_REQUEST)
        );
        if (!pieceRepo.existsByPieceAndIsWhiteAndChessGame("king", true, game))
            return "Black win!";
        else if (!pieceRepo.existsByPieceAndIsWhiteAndChessGame("king", false, game))
            return "White win!";
        return "No finish";
    }
}
