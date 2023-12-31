package Chess;

import Chess.Piece.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Evaluation {
    private final static int pawn = 10;
    private final static int knight = 30;
    private final static int bishop = 30;
    private final static int rook = 50;
    private final static int queen = 90;
    private final static int king = 900;

    public static int[] findBestMove(Player player, Board board, int depth){
        int[] move = new int[2];
        int bestValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        ArrayList<ArrayList<int[]>> pieceMoveList = player.filterMoves(board);

        for(int pieceIndex = 0; pieceIndex < pieceMoveList.size(); pieceIndex++){
            for(int index = 0; index < pieceMoveList.get(pieceIndex).size(); index+=2){
                int currValue = minimax(board, player, depth-1);
                if(currValue > bestValue){
                    System.out.println(pieceIndex);
                    bestValue = currValue;
                    move[0] = index;
                    move[1] = index+1;
                }
            }
        }
        return move;
    }

    private static int minimax(Board board, Player player, int depth){
        int eval = 0;
        int maxEval = Integer.MIN_VALUE;
        if(depth == 0 || Chess.checkMate(Piece.color.White, board) || Chess.checkMate(Piece.color.Black, board)){
            return evaluatePosition(board, player, depth);
        }


        ArrayList<ArrayList<int[]>> pieceMoveList = player.filterMoves(board);
        for(ArrayList<int[]> moveList: pieceMoveList){
            for(int i = 0; i < moveList.size(); i +=2){
                Board duplicate = board.copy();
                duplicate.makeMove(moveList.get(i), moveList.get(i+1));

                eval = minimax(duplicate, duplicate.opponent(player), depth-1);

                maxEval = Math.max(eval, maxEval);
            }
        }

        return maxEval;

    }

    private static int evaluatePosition(Board board, Player player, int depth){
        int score = 0;
        Player opponent = board.opponent(player);

        for(Piece piece: player.availablePieces){
            score += piece.myValue;
        }

        for(Piece piece: opponent.availablePieces){
            score -= piece.myValue;
        }


        return score;
    }
    private static int scoreCapture(Piece piece){
        if(piece instanceof Pawn){
            return pawn;
        }
        else if(piece instanceof Knight){
            return knight;
        }
        else if(piece instanceof Bishop){
            return bishop;
        }
        else if(piece instanceof Rook){
            return rook;
        }
        else if(piece instanceof Queen){
            return queen;
        }
        else if(piece instanceof King){
            return king;
        }

        return 0;
    }
}
