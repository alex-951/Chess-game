package Chess.Piece;

import Chess.Board;

import java.util.ArrayList;

public class Empty extends Piece{
    public Empty(int[] pos){
        super(color.Empty, type.Empty, pos);
        myValue = 0;
    }

    @Override
    public ArrayList<int[]> myMoves(Board board){
        return new ArrayList<>() ;
    }

    @Override
    public Piece copy() {
        return new Empty(position);
    }

    @Override
    public void print(){
        System.out.println("Empty");
    }



    @Override
    public String toString() {
        return "Empty";
    }
}

