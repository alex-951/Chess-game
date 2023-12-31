package Chess.Piece;

import Chess.Board;

import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(color myColor, int[] pos){
        super(myColor, type.Knight, pos);
        myValue = 30;
    }

    public ArrayList<int[]> myMoves (Board board){
        ArrayList<Move> myMoves = new ArrayList<>();
        ArrayList<int[]> list = new ArrayList<>();
        color color = this.myColor;
        Piece[][] squares = board.squares;

        int from_x, from_y, x, y;
        from_x = position[0];
        from_y = position[1];

        //check 2 left up
        x = from_x - 2;
        y = from_y + 1;
        if(x > -1 && y < 8){
            Piece twoLeftUp = squares[y][x];
            if(twoLeftUp.isEmpty()){
                list.add(twoLeftUp.position);
                Move move = new Move(position, twoLeftUp.position);
                myMoves.add(move);
            }
            else if(twoLeftUp.myColor != color){
                list.add(twoLeftUp.position);
                Move move = new Move(position, twoLeftUp.position);
                myMoves.add(move);
            }
        }

        //check 2 left down
        x = from_x - 2;
        y = from_y - 1;
        if(x > -1 && y > -1){
            Piece twoLeftDown = squares[y][x];
            if(twoLeftDown.isEmpty()){
                list.add(twoLeftDown.position);
                Move move = new Move(position, twoLeftDown.position);
                myMoves.add(move);
            }
            else if(twoLeftDown.myColor != color){
                list.add(twoLeftDown.position);
                Move move = new Move(position, twoLeftDown.position);
                myMoves.add(move);
            }
        }
        //check 2 right up
        x = from_x + 2;
        y = from_y + 1;
        if(x < 8 && y < 8){
            Piece twoRightUp = squares[y][x];
            if(twoRightUp.isEmpty()){
                list.add(twoRightUp.position);
                Move move = new Move(position, twoRightUp.position);
                myMoves.add(move);
            }
            else if(twoRightUp.myColor != color){
                list.add(twoRightUp.position);
                Move move = new Move(position, twoRightUp.position);
                myMoves.add(move);
            }
        }

        //check 2 right down
        x = from_x + 2;
        y = from_y - 1;
        if(x < 8 && y > -1){
            Piece twoRightDown = squares[y][x];
            if(twoRightDown.isEmpty()){
                list.add(twoRightDown.position);
                Move move = new Move(position, twoRightDown.position);
                myMoves.add(move);
            }
            else if(twoRightDown.myColor != color){
                list.add(twoRightDown.position);
                Move move = new Move(position, twoRightDown.position);
                myMoves.add(move);
            }
        }

        //check 2 up left
        x = from_x - 1;
        y = from_y + 2;
        if(x > -1 && y < 8){
            Piece twoUpLeft = squares[y][x];
            if(twoUpLeft.isEmpty()){
                list.add(twoUpLeft.position);
                Move move = new Move(position, twoUpLeft.position);
                myMoves.add(move);
            }
            else if(twoUpLeft.myColor != color){
                list.add(twoUpLeft.position);
                Move move = new Move(position, twoUpLeft.position);
                myMoves.add(move);
            }
        }

        //check 2 up right
        x = from_x + 1;
        y = from_y + 2;
        if(x < 8 && y < 8){
            Piece twoUpRight = squares[y][x];
            if(twoUpRight.isEmpty()){
                list.add(twoUpRight.position);
                Move move = new Move(position, twoUpRight.position);
                myMoves.add(move);
            }
            else if(twoUpRight.myColor != color){
                list.add(twoUpRight.position);
                Move move = new Move(position, twoUpRight.position);
                myMoves.add(move);
            }
        }
        //check 2 down right
        x = from_x + 1;
        y = from_y - 2;
        if(x < 8 && y > -1){
            Piece twoDownRight = squares[y][x];
            if(twoDownRight.isEmpty()){
                list.add(twoDownRight.position);
                Move move = new Move(position, twoDownRight.position);
                myMoves.add(move);
            }
            else if(twoDownRight.myColor != color){
                list.add(twoDownRight.position);
                Move move = new Move(position, twoDownRight.position);
                myMoves.add(move);
            }
        }

        //check 2 down left
        x = from_x - 1;
        y = from_y - 2;
        if(x > -1 && y > -1){
            Piece twoDownLeft = squares[y][x];
            if(twoDownLeft.isEmpty()){
                list.add(twoDownLeft.position);
                Move move = new Move(position, twoDownLeft.position);
                myMoves.add(move);
            }
            else if(twoDownLeft.myColor != color){
                list.add(twoDownLeft.position);
                Move move = new Move(position, twoDownLeft.position);
                myMoves.add(move);
            }
        }

        this.myMoves = myMoves;
        return list;
    }

    @Override
    public Piece copy() {
        return new Knight(myColor, position);
    }

    @Override
    public void print(){
        System.out.println("Knight");
    }

    @Override
    public String toString() {
        return "Knight";
    }
}