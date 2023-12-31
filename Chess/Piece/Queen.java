package Chess.Piece;

import Chess.Board;

import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(color myColor, int[] pos){
        super(myColor, type.Queen, pos);
        myValue = 90;
    }

    public ArrayList<int[]> myMoves(Board board){
        ArrayList<int[]> list = new ArrayList<>();
        ArrayList<Move> myMoves = new ArrayList<>();

        Piece.color color = this.myColor;

        Bishop temp1 = new Bishop(color, this.position);
        Rook temp2 = new Rook(color, this.position);



        list.addAll(temp1.myMoves(board));
        list.addAll(temp2.myMoves(board));

        myMoves.addAll(temp1.myMoves);
        myMoves.addAll(temp2.myMoves);
        this.myMoves = myMoves;

        return list;
    }

    @Override
    public Piece copy() {
        return new Queen(myColor, position);
    }

    @Override
    public void print(){
        System.out.println("Queen");
    }

    @Override
    public String toString() {
        return "Queen";
    }
}
