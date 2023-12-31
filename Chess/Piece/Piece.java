package Chess.Piece;

import Chess.Board;

import java.util.ArrayList;

abstract public class Piece {

    public enum color {White, Black, Highlight, Empty};
    public enum type {Pawn, Rook, King, Queen, Bishop, Knight, Empty};
    public color myColor;
    public type myType;
    public int myValue;
    public static final String resetColor = "\u001B[0m";
    public int[] position = new int[2]; // {x, y}
    public int counter;
    public ArrayList<Move> myMoves;


    public Piece(color myColor, type myType, int[] pos){
        this.position[0] = pos[0];
        this.position[1] = pos[1];
        this.myColor = myColor;
        this.myType = myType;
        this.counter = 0;
        this.myMoves = new ArrayList<>();
    }

    public char getChar(){
        switch(this.myType){
            case King:
                return 'K';
            case Queen:
                return 'Q';
            case Pawn:
                return 'P';
            case Bishop:
                return  'B';
            case Knight:
                return 'H';
            case Rook:
                return 'R';
            case Empty:
                if (this.myColor == color.Highlight){
                    return '*';
                }
                return ' ';
            default:
                return 0;
        }
    }

    public String getColorString(){
        switch(this.myColor){
            case White:
                return "\u001B[32m";
            case Black:
                return "\u001B[31m";
            case Highlight:
                return "\u001B[33m";
            default:
                return "\u001B[0m";
        }
    }




    public boolean isEmpty(){
        if(this.myType == type.Empty){
            return true;
        }
        return false;
    }

    public boolean isBlack(){
        if(this.myColor == color.Black){
            return true;
        }
        return false;
    }

    public boolean isWhite(){
        if(this.myColor == color.White){
            return true;
        }
        return false;
    }

    public abstract  ArrayList<int[]> myMoves(Board board);

    public abstract Piece copy();

    public abstract void print();

    public void printMyMoves(Board board) {
        myMoves(board);
        System.out.println(this);
        for(Move move: myMoves){
            System.out.println(move);
        }
        System.out.println();
    }
}
