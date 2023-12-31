package Chess.Piece;

import Chess.Board;

import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(color myColor, int[] pos){
        super(myColor, type.Bishop, pos);
        myValue = 30;
    }

    public ArrayList<int[]> myMoves (Board board){
        ArrayList<Move> myMoves = new ArrayList<>();
        ArrayList<int[]> list = new ArrayList<>();
        color color = myColor;
        Piece[][] squares = board.squares;

        int from_x = position[0];
        int from_y = position[1];

        //check top left
        int x = from_x - 1;
        int y = from_y + 1;
        while(y < 8 && x > -1 ){
            Piece topLeft = squares[y][x];
            if(topLeft.isEmpty()){
                list.add(topLeft.position);
                Move move = new Move(position, topLeft.position);
                myMoves.add(move);
            }
            else if(topLeft.myColor != color){
                list.add(topLeft.position);
                Move move = new Move(position, topLeft.position);
                myMoves.add(move);
                break;
            }
            else{
                break;
            }
            x--;
            y++;
        }

        //check top right
        x = from_x + 1;
        y = from_y + 1;
        while(y < 8 && x < 8 ){
            Piece topRight = squares[y][x];
            if(topRight.isEmpty()){
                list.add(topRight.position);
                Move move = new Move(position, topRight.position);
                myMoves.add(move);
            }
            else if(topRight.myColor != color) {
                list.add(topRight.position);
                Move move = new Move(position, topRight.position);
                myMoves.add(move);
                break;
            }
            else{
                break;
            }
            x++;
            y++;
        }

        //check bottom left
        x = from_x - 1;
        y = from_y - 1;
        while(y > -1 && x > -1 ){
            Piece bottomLeft = squares[y][x];
            if(bottomLeft.isEmpty()){
                list.add(bottomLeft.position);
                Move move = new Move(position, bottomLeft.position);
                myMoves.add(move);
            }
            else if(squares[y][x].myColor != color) {
                list.add(bottomLeft.position);
                Move move = new Move(position, bottomLeft.position);
                myMoves.add(move);
                break;
            }
            else{
                break;
            }
            x--;
            y--;
        }

        //check bottom right
        x = from_x + 1;
        y = from_y - 1;
        while(y > -1 && x < 8 ){
            Piece bottomRight = squares[y][x];
            if(bottomRight.isEmpty()){
                list.add(bottomRight.position);
                Move move = new Move(position, bottomRight.position);
                myMoves.add(move);
            }
            else if(bottomRight.myColor != color) {
                list.add(bottomRight.position);
                Move move = new Move(position, bottomRight.position);
                myMoves.add(move);
                break;
            }
            else {
                break;
            }
            x++;
            y--;
        }

        this.myMoves = myMoves;
        return list;
    }

    @Override
    public Piece copy() {
        return new Bishop(myColor, position);
    }

    @Override
    public void print(){
        System.out.println("Bishop");
    }

    @Override
    public String toString() {
        return "Bishop";
    }
}