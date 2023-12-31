package Chess.Piece;


import Chess.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {

    public Pawn(color myColor, int[] pos){
        super(myColor,type.Pawn, pos);
        myValue = 10;
    }


    @Override
    public  ArrayList<int[]> myMoves(Board board) {
        ArrayList<Move> myMoves = new ArrayList<>();
        Piece[][] squares = board.squares;
        Piece.color color = this.myColor;

        ArrayList<int[]> moves = new ArrayList<>();
        int from_x = this.position[0];
        int from_y = this.position[1];

        if (color == Piece.color.White) {
            Piece oneInFront = squares[from_y+1][from_x];

            //check 2 squares in front of pawn
            if (from_y == 1){
                Piece twoInFront = squares[from_y+2][from_x];
                if(oneInFront.isEmpty() && twoInFront.isEmpty()){
                    moves.add(twoInFront.position);
                    Move currMove = new Move(position, twoInFront.position);
                    myMoves.add(currMove);
                }

            }

            if(oneInFront.isEmpty()){
                moves.add(oneInFront.position);
                Move currMove = new Move(position, oneInFront.position);
                myMoves.add(currMove);
            }

            //check left diagonal
            if (from_x > 0) {
                Piece leftDiagonal = squares[from_y+1][from_x-1];
                Piece left = squares[from_y][from_x-1];
                //regular
                if (leftDiagonal.isBlack()) {
                    moves.add(leftDiagonal.position);
                    Move currMove = new Move(position, leftDiagonal.position);
                    myMoves.add(currMove);
                }
                //en-passant
                if (left.isBlack() && left instanceof Pawn && Arrays.equals(board.prevMove, left.position)){
                    if(left.counter == 1) {
                        moves.add(leftDiagonal.position);
                        Move currMove = new Move(position, leftDiagonal.position);
                        myMoves.add(currMove);
                    }
                }
            }

            //check right diagonal
            if (from_x  < 7) {
                Piece rightDiagonal = squares[from_y+1][from_x+1];
                Piece right = squares[from_y][from_x+1];

                //regular
                if (rightDiagonal.isBlack()) {
                    moves.add(rightDiagonal.position);
                    Move currMove = new Move(position, rightDiagonal.position);
                    myMoves.add(currMove);
                }
                //en-passant
                if (right.isBlack() && right instanceof Pawn && Arrays.equals(board.prevMove, right.position)){
                    if(right.counter == 1){
                        moves.add(rightDiagonal.position);
                        Move currMove = new Move(position, rightDiagonal.position);
                        myMoves.add(currMove);
                    }
                }
            }
        }

        if (color == Piece.color.Black) {

            //check 2 squares in front of pawn
            Piece oneInFront = squares[from_y-1][from_x];
            if(from_y == 6){
                Piece twoInFront = squares[from_y-2][from_x];
                if(oneInFront.isEmpty() && twoInFront.isEmpty()){
                    moves.add(twoInFront.position);
                    Move currMove = new Move(position, twoInFront.position);
                    myMoves.add(currMove);
                }

            }

            if(oneInFront.isEmpty()){
                moves.add(oneInFront.position);
                Move currMove = new Move(position, oneInFront.position);
                myMoves.add(currMove);

            }


            //check left diagonal
            if (from_x < 7 ) {
                Piece leftDiagonal = squares[from_y-1][from_x+1];
                Piece left = squares[from_y][from_x+1];
                if (leftDiagonal.isWhite()) {
                    moves.add(leftDiagonal.position);
                    Move currMove = new Move(position, leftDiagonal.position);
                    myMoves.add(currMove);

                }
                //en-passant
                if(left instanceof Pawn && left.isWhite() && Arrays.equals(board.prevMove, left.position)){
                    if(left.counter == 1){
                        moves.add(leftDiagonal.position);
                        Move currMove = new Move(position, leftDiagonal.position);
                        myMoves.add(currMove);
                    }
                }
            }

            //check right diagonal
            if (from_x > 0) {
                Piece rightDiagonal = squares[from_y-1][from_x-1];
                Piece right = squares[from_y][from_x-1];
                if (rightDiagonal.isWhite()) {
                    moves.add(rightDiagonal.position);
                    Move currMove = new Move(position, rightDiagonal.position);
                    myMoves.add(currMove);
                }
                //en-passant
                if(right instanceof Pawn && right.isWhite() && Arrays.equals(board.prevMove, right.position)){
                    if(right.counter == 1){
                        moves.add(rightDiagonal.position);
                        Move currMove = new Move(position, rightDiagonal.position);
                        myMoves.add(currMove);
                    }
                }
            }

        }

        this.myMoves = myMoves;
        return moves;
    }

    @Override
    public Piece copy() {
        return new Pawn(myColor, position);

    }

    @Override
    public void print(){
        System.out.println("Pawn");
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
