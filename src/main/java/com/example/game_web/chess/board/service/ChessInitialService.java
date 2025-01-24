package com.example.game_web.chess.board.service;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.entity.ChessPiece;
import com.example.game_web.chess.board.repo.ChessGameRepo;
import com.example.game_web.chess.board.repo.ChessPieceRepo;
import org.springframework.stereotype.Service;

@Service
public class ChessInitialService {
    private final ChessGameRepo gameRepo;
    private final ChessPieceRepo pieceRepo;
    public ChessInitialService(ChessGameRepo gameRepo, ChessPieceRepo pieceRepo){
        this.gameRepo = gameRepo;
        this.pieceRepo = pieceRepo;
        initializeGame();
    }

    public void initializeGame(){
        ChessGame startGame = new ChessGame();
        gameRepo.save(startGame);
        for (int col=0; col<8; col++){
            pieceRepo.save(ChessPiece.builder()
                    .piece("pawn").isWhite(true).rowIdx(1).colIdx(col).chessGame(startGame)
                    .build());
            pieceRepo.save(ChessPiece.builder()
                    .piece("pawn").isWhite(false).rowIdx(6).colIdx(col).chessGame(startGame)
                    .build());
        }

        //Set up rooks
        pieceRepo.save(ChessPiece.builder()
                .piece("rook").isWhite(true).rowIdx(0).colIdx(0).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("rook").isWhite(true).rowIdx(0).colIdx(7).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("rook").isWhite(false).rowIdx(7).colIdx(0).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("rook").isWhite(false).rowIdx(7).colIdx(7).chessGame(startGame)
                .build());

        // Set up knights
        pieceRepo.save(ChessPiece.builder()
                .piece("knight").isWhite(true).rowIdx(0).colIdx(1).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("knight").isWhite(true).rowIdx(0).colIdx(6).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("knight").isWhite(false).rowIdx(7).colIdx(1).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("knight").isWhite(false).rowIdx(7).colIdx(6).chessGame(startGame)
                .build());
        // Set up bishop
        pieceRepo.save(ChessPiece.builder()
                .piece("bishop").isWhite(true).rowIdx(0).colIdx(2).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("bishop").isWhite(true).rowIdx(0).colIdx(5).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("bishop").isWhite(false).rowIdx(7).colIdx(2).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("bishop").isWhite(false).rowIdx(7).colIdx(5).chessGame(startGame)
                .build());

        // Set up queens
        pieceRepo.save(ChessPiece.builder()
                .piece("queen").isWhite(true).rowIdx(0).colIdx(3).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("queen").isWhite(false).rowIdx(7).colIdx(3).chessGame(startGame)
                .build());

        // Set up kings
        pieceRepo.save(ChessPiece.builder()
                .piece("king").isWhite(true).rowIdx(0).colIdx(4).chessGame(startGame)
                .build());
        pieceRepo.save(ChessPiece.builder()
                .piece("king").isWhite(false).rowIdx(7).colIdx(4).chessGame(startGame)
                .build());
    }
}
