package Chess;

import Chess.Piece.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class describes the Player object which has
 * an arraylist of available pieces and the player's color.
 */
public class Player{
    ArrayList<Piece> availablePieces;
    ArrayList<Move> myMoves;
    public Piece.color myColor;

    /**
     * This is the constructor used for creating the Player object.
     * @param color The color of the Player.
     * @param board The Board object associated with the Player.
     */
    public Player(Piece.color color, Board board){
        availablePieces = new ArrayList<>();

        Piece[][] squares = board.squares;
        myColor = color;
        if(color == Piece.color.White) {
            squares[0][0] = new Rook(Piece.color.White, new int[]{0, 0});
            squares[0][1] = new Knight(Piece.color.White, new int[]{1, 0});
            squares[0][2] = new Bishop(Piece.color.White, new int[]{2, 0});
            squares[0][3] = new Queen(Piece.color.White, new int[]{3, 0});
            squares[0][4] = new King(Piece.color.White, new int[]{4, 0});
            squares[0][5] = new Bishop(Piece.color.White, new int[]{5, 0});
            squares[0][6] = new Knight(Piece.color.White, new int[]{6, 0});
            squares[0][7] = new Rook(Piece.color.White, new int[]{7, 0});

            squares[1][0] = new Pawn(Piece.color.White, new int[]{0, 1});
            squares[1][1] = new Pawn(Piece.color.White, new int[]{1, 1});
            squares[1][2] = new Pawn(Piece.color.White, new int[]{2, 1});
            squares[1][3] = new Pawn(Piece.color.White, new int[]{3, 1});
            squares[1][4] = new Pawn(Piece.color.White, new int[]{4, 1});
            squares[1][5] = new Pawn(Piece.color.White, new int[]{5, 1});
            squares[1][6] = new Pawn(Piece.color.White, new int[]{6, 1});
            squares[1][7] = new Pawn(Piece.color.White, new int[]{7, 1});

            for(int y = 0; y < 2; y++){
                for(int x = 0; x < 8; x++){
                   availablePieces.add(squares[y][x]);
                }
            }
        }
        else if(color == Piece.color.Black){
            squares[7][0] = new Rook(Piece.color.Black, new int[]{0, 7});
            squares[7][1] = new Knight(Piece.color.Black, new int[]{1, 7});
            squares[7][2] = new Bishop(Piece.color.Black, new int[]{2, 7});
            squares[7][3] = new Queen(Piece.color.Black, new int[]{3, 7});
            squares[7][4] = new King(Piece.color.Black, new int[]{4, 7});
            squares[7][5] = new Bishop(Piece.color.Black, new int[]{5, 7});
            squares[7][6] = new Knight(Piece.color.Black, new int[]{6, 7});
            squares[7][7] = new Rook(Piece.color.Black, new int[]{7, 7});

            squares[6][0] = new Pawn(Piece.color.Black, new int[]{0, 6});
            squares[6][1] = new Pawn(Piece.color.Black, new int[]{1, 6});
            squares[6][2] = new Pawn(Piece.color.Black, new int[]{2, 6});
            squares[6][3] = new Pawn(Piece.color.Black, new int[]{3, 6});
            squares[6][4] = new Pawn(Piece.color.Black, new int[]{4, 6});
            squares[6][5] = new Pawn(Piece.color.Black, new int[]{5, 6});
            squares[6][6] = new Pawn(Piece.color.Black, new int[]{6, 6});
            squares[6][7] = new Pawn(Piece.color.Black, new int[]{7, 6});

            for(int y = 7; y > 5; y--){
                for(int x = 0; x < 8; x++){
                    availablePieces.add(squares[y][x]);
                }
            }

        }

    }

    /**
     * This method is used to remove the piece from the Player's
     * available pieces list.
     * @param piece The piece we are attempting to remove.
     */
    public void removePiece(Piece piece){
        int[] position = piece.position;
        int index = 0;
        for(Piece curr: availablePieces){
            if(Arrays.equals(curr.position, position)){
                availablePieces.remove(index);
                break;
            }
            index++;
        }
    }

    /**
     * This method searches through all pieces a player has,
     * and checks where the piece can move too and adds the piece's current position.
     * I.G. pawn at e2 would have [E3, E4] -> [E2, E3, E2, E4].
     * The move set for every piece will be added to one big list,
     * containing [[move set for pawn 1], [move set for pawn 2], ...].
     * @param board This is the board containing all the pieces, including the opponents pieces.
     * @return A 2-D arrayList containing move sets with varying lengths: [[move set], [move set], ...].
     */
    public ArrayList<ArrayList<int[]>> findAvailableMoves(Board board){
        ArrayList<Move> moves = new ArrayList<>();
        for(Piece piece: availablePieces){
            piece.myMoves(board);
            moves.addAll(piece.myMoves);
        }
        myMoves = moves;

        ArrayList<ArrayList<int[]>> availableMoves = new ArrayList<>();
        for(Piece currPiece: availablePieces){
           ArrayList<int[]> currMoves = currPiece.myMoves(board);

           for(int x = 0; x < currMoves.size(); x+=2){
               currMoves.add(x, currPiece.position);
           }
           if(!currMoves.isEmpty()) {
               availableMoves.add(currMoves);
           }
        }
        return availableMoves;
    }

    /**
     * This method can be used to print any available moves the player has using
     * the filterMoves method. The player will see the number of moves as well as
     * the where the piece is being moved from and to.
     * @param board This is the board currently being used.
     */
    public void printAvailableMoves(Board board){
        ArrayList<ArrayList<int[]>> moves = filterMoves(board);
        int total = 0;
        for(ArrayList<int[]> moveList: moves){
            total += moveList.size()/2;
        }
        System.out.println("Total moves available: "+total);
        for(ArrayList<int[]> moveList: moves){
            for(int i = 0; i < moveList.size(); i+=2){
                String from = Board.encodeMove(moveList.get(i));
                String to = Board.encodeMove(moveList.get(i+1));
                System.out.println("From: "+from+" To: "+to);
            }
        }
    }

    /**
     * This method will iterate through every arrayList within the availableMoves list
     * and check if that move will either 1. lead into a self-inflicted check or 2.
     * doesn't address a current check. We filter moves that leaves the current player
     * in check.
     * @param board The current board being used.
     * @return The 2-D ArrayList of moves that are now filtered and allowed to be made. 
     */
    public ArrayList<ArrayList<int[]>> filterMoves(Board board){
        ArrayList<ArrayList<int[]>> currList = findAvailableMoves(board);
        ArrayList<ArrayList<int[]>> newList = new ArrayList<>();


        for(ArrayList<int[]> move : currList){
            ArrayList<int[]> possible = new ArrayList<>();
            for(int x = 0; x <move.size(); x+=2) {
                Board copy = board.copy();
                copy.makeMove(move.get(x), move.get(x + 1));
                if (!copy.inCheck(myColor, copy)) {
                    possible.add(move.get(x));
                    possible.add(move.get(x+1));
                }
            }
            if (!possible.isEmpty()) {
                newList.add(possible);
            }
        }

        ArrayList<Move> validMoves = new ArrayList<>();
        for(Move move: myMoves){
            Board copy = board.copy();
            copy.makeMove(move.getSrc(), move.getDest());
            if(!copy.inCheck(myColor, copy)){
                validMoves.add(move);
            }
        }
        myMoves.clear();
        myMoves.addAll(validMoves);

        return newList;
    }

    /**
     * This is used to create a deep-copy of the Player object.
     * @param board The board we are currently using.
     * @return The deep-copy of the Player object.
     */
    public Player copy(Board board){
        Player newPlayer = new Player(myColor, board);
        newPlayer.myColor = this.myColor;
        newPlayer.availablePieces = new ArrayList<>();
        newPlayer.availablePieces.addAll(this.availablePieces);

        return newPlayer;
    }

}
