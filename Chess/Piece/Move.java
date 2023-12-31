package Chess.Piece;

import Chess.Board;

import java.util.ArrayList;

public class Move {
    private final int[] src;
    private final int[] dest;

    public Move(int[] src, int[] dest){
        this.src = src;
        this.dest = dest;
    }

    public int[] getSrc(){
        return src;
    }

    public int[] getDest(){
        return dest;
    }

    @Override
    public String toString() {
        return "From: " + Board.encodeMove(src) + " To: " + Board.encodeMove(dest);
    }
}
