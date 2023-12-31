package Chess.Piece;

import Chess.Board;

import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(color myColor, int[] pos){
        super(myColor, type.Rook ,pos);
        myValue = 50;
    }

    public ArrayList<int[]> myMoves (Board board){
        ArrayList<Move> myMoves= new ArrayList<>();
        ArrayList<int[]> list = new ArrayList<>();

        Piece.color color = this.myColor;
        Piece[][] squares = board.squares;

        int from_x = position[0];
        int from_y = position[1];

        //check left
        int x = from_x - 1;
        int y = from_y;
        while(x > -1 ){
            Piece left = squares[y][x];
            if(left.isEmpty()){
                list.add(left.position);
                Move move = new Move(position, left.position);
                myMoves.add(move);
            }
            else if(left.myColor != color){
                list.add(left.position);
                Move move = new Move(position, left.position);
                myMoves.add(move);
                break;
            }
            else{
                break;
            }
            x--;
        }

        //check right
        x = from_x + 1;
        y = from_y;
        while(x < 8 ){
            Piece right = squares[y][x];
            if(right.isEmpty()){
                list.add(right.position);
                Move move = new Move(position, right.position);
                myMoves.add(move);
            }
            else if(right.myColor != color) {
                list.add(right.position);
                Move move = new Move(position, right.position);
                myMoves.add(move);
                break;
            }
            else{
                break;
            }
            x++;
        }

        //check down
        x = from_x;
        y = from_y - 1;
        while(y > -1 ){
            Piece down = squares[y][x];
            if(down.isEmpty()){
                list.add(down.position);
                Move move = new Move(position, down.position);
                myMoves.add(move);
            }
            else if(down.myColor != color) {
                list.add(down.position);
                Move move = new Move(position, down.position);
                myMoves.add(move);
                break;
            }
            else{
                break;
            }
            y--;
        }

        //check top
        x = from_x;
        y = from_y + 1;
        while(y < 8){
            Piece top = squares[y][x];
            if(top.isEmpty()){
                list.add(top.position);
                Move move = new Move(position, top.position);
                myMoves.add(move);
            }
            else if(top.myColor != color) {
                list.add(top.position);
                Move move = new Move(position, top.position);
                myMoves.add(move);
                break;
            }
            else {
                break;
            }
            y++;
        }

        return list;
    }

    @Override
    public Piece copy() {
        return new Rook(myColor, position);
    }

    @Override
    public void print(){
        System.out.println("Rook");
    }

    @Override
    public String toString() {
        return "Rook";
    }
}
