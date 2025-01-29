package com.example.game_web.chess.play;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.entity.ChessPiece;
import com.example.game_web.chess.board.repo.ChessGameRepo;
import com.example.game_web.chess.board.repo.ChessPieceRepo;
import com.example.game_web.chess.movement.MoveService;
import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.chess.play.dto.ResponseDto;
import com.example.game_web.chess.play.dto.RequestDto;
import com.example.game_web.chess.play.result.ResultService;
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
    private final MoveService moveService;
    private final ResultService resultService;

    public ResponseDto play(RequestDto request){
        ChessGame game = gameRepo.findById(request.getGameId()).orElseThrow(
                ()-> new CustomException("No exist game", HttpStatus.BAD_REQUEST)
        );
//        if (game.isFinish())
//            throw new CustomException("Game already finished!!", HttpStatus.BAD_REQUEST);

        if (game.getStatus().equals(ChessGame.Status.WIN) || game.getStatus().equals(ChessGame.Status.DRAW))
            throw new CustomException("Game already finished!!", HttpStatus.BAD_REQUEST);

        ChessPiece pieceCurrent = pieceRepo.findByRowIdxAndColIdxAndChessGame(request.getRowCur(), request.getColCur(), game)
                .orElseThrow(()-> new CustomException("No exist any pieces here", HttpStatus.BAD_REQUEST));

        // Check turn of player
        if (pieceCurrent.isWhite()!= game.isWhiteTurn())
            throw new CustomException("It's not your turn", HttpStatus.BAD_REQUEST);

        // Check if current piece can move or not
        if (!moveService.validMove(new CurrentPosition(pieceCurrent.getChessGame().getId(), pieceCurrent.getRowIdx(), pieceCurrent.getColIdx(), pieceCurrent.isWhite(), pieceCurrent.getPiece()))
                .contains(new NewPosition(request.getRowNew(), request.getColNew())))
            throw new CustomException("You cannot move here", HttpStatus.BAD_REQUEST);

        // In case of 'king' piece, if the new position king move is under check, king cannot move over there.
        if (pieceCurrent.getPiece().equals("king")){
            List<ChessPiece> pieces = pieceRepo.findByWhiteAndChessGame(!pieceCurrent.isWhite(), game);
            for (ChessPiece piece: pieces){
                List<NewPosition> positions = moveService.validMove(
                        new CurrentPosition(game.getId(), piece.getRowIdx(), piece.getColIdx(),piece.isWhite(), piece.getPiece())
                );
                if (positions.contains(new NewPosition(request.getRowNew(), request.getColNew())))
                    throw new CustomException("King will be checked by opponent's "+ piece.getPiece(), HttpStatus.BAD_REQUEST);
            }
        }

        // Update new position
        Optional<ChessPiece> pieceNewPos = pieceRepo.findByRowIdxAndColIdxAndChessGame(request.getRowNew(), request.getColNew(), game);
        ChessPiece pieceNew;
        if (pieceNewPos.isEmpty()){
            pieceNew = ChessPiece.builder()
                    .piece(pieceCurrent.getPiece())
                    .rowIdx(request.getRowNew())
                    .colIdx(request.getColNew())
                    .white(pieceCurrent.isWhite())
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

        // Change status of king threaten if necessary
        if (game.isKingDanger()) game.setKingDanger(false);

        // Check if the game finish(king was taken out or not)
//        if (!pieceRepo.existsByPieceAndWhiteAndChessGame("king", !game.isWhiteTurn(), game))
//            game.setFinish(true);
        resultService.draw(game.getId(), pieceCurrent.isWhite());

        // Change turn
        game.setWhiteTurn(!game.isWhiteTurn());

        // Check if king is threatened or not
            // 1. List of position that new piece can move
        List<NewPosition> listMoveOfNewPos = moveService.validMove(CurrentPosition.builder()
                        .rowIdx(pieceNew.getRowIdx()).colIdx(pieceNew.getColIdx())
                        .piece(pieceNew.getPiece()).white(pieceNew.isWhite())
                        .gameId(game.getId())
                .build());

            // 2. Get position of opponent's king
        ChessPiece king = pieceRepo.findByPieceAndWhiteAndChessGame("king", !game.isWhiteTurn(), game).orElseThrow(
                ()-> new CustomException("You lose", HttpStatus.BAD_REQUEST)
        );
            // 3. Check if king exists into list movement or not.
        if (listMoveOfNewPos.contains(new NewPosition(king.getRowIdx(), king.getColIdx())))
            game.setKingDanger(true);
        gameRepo.save(game);
        return ResponseDto.fromEntity(pieceRepo.pieceWithGame(pieceNew.getId()));
    }


    // In case that pawn end up the row of opponent. -> pawn can be queen, rook, ...
    public ResponseDto pawnTo(RequestDto requestDto){
        ChessPiece pawn = pieceRepo.findById(requestDto.getPieceId()).orElseThrow(
                ()-> new CustomException("No exist any pawns", HttpStatus.BAD_REQUEST)
        );
        if (pawn.isWhite()){
            if (!(pawn.getPiece().equals("pawn") && pawn.getRowIdx()==7))
                throw new CustomException("No exist white pawn in this position", HttpStatus.BAD_REQUEST);
        }
        else {
            if (!(pawn.getPiece().equals("pawn") && pawn.getRowIdx()==0))
                throw new CustomException("No exist black pawn in this position", HttpStatus.BAD_REQUEST);
        }
        // Change pawn to another piece
        pawn.setPiece(requestDto.getPiece());
        pieceRepo.save(pawn);
        return ResponseDto.fromEntity(pieceRepo.pieceWithGame(pawn.getId()));
    }



}
