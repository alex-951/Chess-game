package Chess.Piece;

import Chess.Board;

import java.util.ArrayList;

public class King extends Piece{
    public King (color myColor, int [] pos){
        super(myColor, type.King, pos);
        myValue = 900;
    }

    public ArrayList<int[]> myMoves (Board board){
        ArrayList<Move> myMoves = new ArrayList<>();
        ArrayList<int[]> list = new ArrayList<>();

        color color = this.myColor;
        Piece[][] squares = board.squares;

        int from_x, from_y, x, y;
        from_x = position[0];
        from_y = position[1];

        //top left
        x = from_x - 1;
        y = from_y + 1;
        if(x > -1 && y < 8){
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
            }
        }

        //top
        x = from_x;
        y = from_y + 1;
        if(y < 8){
            Piece top = squares[y][x];
            if(top.isEmpty()){
                list.add(top.position);
                Move move = new Move(position, top.position);
                myMoves.add(move);
            }
            else if(top.myColor != color){
                list.add(top.position);
                Move move = new Move(position, top.position);
                myMoves.add(move);
            }
        }

        //top right
        x = from_x + 1;
        y = from_y + 1;
        if(x < 8 && y < 8){
            Piece topRight = squares[y][x];
            if(topRight.isEmpty()){
                list.add(topRight.position);
                Move move = new Move(position, topRight.position);
                myMoves.add(move);
            }
            else if(topRight.myColor != color){
                list.add(topRight.position);
                Move move = new Move(position, topRight.position);
                myMoves.add(move);
            }
        }

        //left
        x = from_x - 1;
        y = from_y;
        if(x > -1){
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
            }
        }

        //right
        x = from_x + 1;
        y = from_y;
        if(x < 8){
            Piece right = squares[y][x];
            if(right.isEmpty()){
                list.add(right.position);
                Move move = new Move(position, right.position);
                myMoves.add(move);
            }
            else if(right.myColor != color){
                list.add(right.position);
                Move move = new Move(position, right.position);
                myMoves.add(move);
            }
        }

        //bottom left
        x = from_x - 1;
        y = from_y - 1;
        if(x > -1 && y > -1){
            Piece bottomLeft = squares[y][x];
            if(bottomLeft.isEmpty()){
                list.add(bottomLeft.position);
                Move move = new Move(position, bottomLeft.position);
                myMoves.add(move);
            }
            else if(bottomLeft.myColor != color){
                list.add(bottomLeft.position);
                Move move = new Move(position, bottomLeft.position);
                myMoves.add(move);
            }
        }

        //bottom
        x = from_x;
        y = from_y - 1;
        if(y > -1){
            Piece bottom = squares[y][x];
            if(bottom.isEmpty()){
                list.add(bottom.position);
                Move move = new Move(position, bottom.position);
                myMoves.add(move);
            }
            else if(bottom.myColor != color){
                list.add(bottom.position);
                Move move = new Move(position, bottom.position);
                myMoves.add(move);
            }
        }

        //bottom right
        x = from_x + 1;
        y = from_y - 1;
        if(x < 8 && y > -1){
            Piece bottomRight = squares[y][x];
            if(bottomRight.isEmpty()){
                list.add(bottomRight.position);
                Move move = new Move(position, bottomRight.position);
                myMoves.add(move);
            }
            else if(bottomRight.myColor != color){
                list.add(bottomRight.position);
                Move move = new Move(position, bottomRight.position);
                myMoves.add(move);
            }
        }

        //long castle
        x = from_x - 2;
        y = from_y;
        if(counter == 0){
            Piece longRook = squares[y][0];
            if(longRook instanceof Rook && longRook.counter == 0){
                Piece inBetween1 = squares[y][x-1];
                Piece inBetween2 = squares[y][x];
                Piece inBetween3 = squares[y][x+1];
                if(inBetween1.isEmpty() && inBetween2.isEmpty() && inBetween3.isEmpty()){
                    list.add(inBetween2.position);
                    Move move = new Move(position, inBetween2.position);
                    myMoves.add(move);
                }

            }
        }

        //short castle
        x = from_x + 2;
        y = from_y;
        if(counter == 0){
            Piece shortRook = squares[y][7];
            if(shortRook instanceof Rook && shortRook.counter == 0){
                Piece inBetween1 = squares[y][x-1];
                Piece inBetween2 = squares[y][x];
                if(inBetween1.isEmpty() && inBetween2.isEmpty()){
                    list.add(inBetween2.position);
                    Move move = new Move(position, inBetween2.position);
                    myMoves.add(move);
                }
            }
        }

        this.myMoves = myMoves;
        return list;
    }

    @Override
    public Piece copy(){
        return new King(myColor, position);
    }

    @Override
    public void print(){
        System.out.println("King");
    }

    @Override
    public String toString() {
        return "King";
    }
}
