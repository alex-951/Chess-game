package Chess;

import Chess.Piece.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the Board class which describes the different players,
 * pieces positions, pre-move, and current player/color.
 */
public class Board {

    Player playerWhite;
    Player playerBlack;
    public Piece[][] squares;
    public int[] prevMove;
    public Player currentPlayer;
    public Player nextPlayer;

    /**
     * Constructor used to create a board, initializes pieces, players, pre move, and current player.
     */
    public Board(){
        squares = new Piece[8][8];

        playerWhite = new Player(Piece.color.White, this);
        playerBlack = new Player(Piece.color.Black, this);

        prevMove = new int[2];

        currentPlayer = playerWhite;
        nextPlayer = playerBlack;

        for(int y = 2; y < 6; y++){
            for(int x = 0; x < 8; x++){
                squares[y][x] = new Empty(new int[]{x, y});
            }
        }
    }


    /**
     * This is used to decode a move from rank & file
     * to x,y coordinates. E2 -> [x, y] -> [4, 1].
     * @param move The string we are decoding.
     * @return The array containing the x & y coordinates.
     */
    public static int[] decodeMove(String move){
        int[] index = new int[2];
        char file = move.charAt(0);
        int x = switch (file) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            case 'E' -> 4;
            case 'F' -> 5;
            case 'G' -> 6;
            case 'H' -> 7;
            default -> -1;
        };
        index[1] = Character.getNumericValue(move.charAt(1)) - 1;
        index[0] = x;
        return index;
    }

    /**
     * Used to print the board for the player to see
     */
    public void print(){
        for(int y = 7; y > -1; y--){
            System.out.print("\t");
            printDivider();
            System.out.print((y+1)+"\t| ");
            for(int x = 0; x < 8; x++){
                char letter = squares[y][x].getChar();
                String color = squares[y][x].getColorString();
                System.out.print(color + letter + Piece.resetColor + " | " );
            }
            System.out.println();
        }
        System.out.print("\t");
        printDivider();
        System.out.print("\t");
        printFiles();
    }

    /**
     * Used to print the board in reverse for Black.
     */
    public void printReverse(){
        System.out. print("\033[H\033[2J");
        for(int y = 0; y < 8; y++){
            System.out.print("\t");
            printDivider();
            System.out.print((y+1)+"\t| ");
            for(int x = 7; x > -1; x--){
                char letter = squares[y][x].getChar();
                String color = squares[y][x].getColorString();
                System.out.print(color + letter + Piece.resetColor + " | " );
            }
            System.out.println();
        }
        System.out.print("\t");
        printDivider();
        System.out.print("\t");
        printFilesReverse();
    }

    /**
     * Used to print file letters.
     */
    private void printFiles(){
        for (int x = 0; x < 8; x++){
            System.out.print("  "+(char)(65+x)+" ");
        }
        System.out.println();
    }

    /**
     * Used to print file letters in reveres.
     */
    private void printFilesReverse(){
        for (int x = 0; x < 8; x++){
            System.out.print("  "+(char)(72-x)+" ");
        }
        System.out.println();
    }

    /**
     * Used to print the divider between ranks/rows
     */
    private void printDivider(){
        for(int x = 0; x < 8; x++){
            System.out.print("+---");
        }
        System.out.println("+");
    }

    /**
     * This method is used to check whether the move is within the available moves for the current color.
     * @param from The x,y coordinate of the source position.
     * @param to The x,y coordinate of the destination position.
     * @param color The color of the piece we are trying to move.
     */
    public void move(int[] from, int[] to, Piece.color color){
        ArrayList<ArrayList<int[]>> availableMoves;

        availableMoves = currentPlayer.filterMoves(this);

        if(inPlayerMoveList(availableMoves, to, from)){
            makeMove(from, to);
            this.prevMove = to;
            checkForPromotion();
        }
        else System.out.println("Not an available move!");

    }

    /**
     * After validation, this method is used to actually make the move from source
     * to destination.
     * @param from The x, y coordinate of the source position.
     * @param to The x, y coordinate of the destination position.
     */
    public void makeMove(int[]from, int[]to){
        //x and y values of src and dest
        int from_x = from[0];
        int from_y = from[1];
        int to_x = to[0];
        int to_y = to[1];

        //src and dest found at specified positions
        Piece src = squares[from_y][from_x];
        Piece dest = squares[to_y][to_x];

        //remove dest from player list
        if(dest.myColor != Piece.color.Empty) nextPlayer.removePiece(dest);

        //move src to new location
        squares[to_y][to_x] = src;
        src.counter++;
        src.position = to;

        //make old location empty
        squares[from_y][from_x] = new Empty(from);

        //en passant
        if(src instanceof Pawn && dest instanceof Empty){
            if(squares[to_y-1][to_x] instanceof Pawn && squares[to_y-1][to_x].isBlack()){
                Pawn chump = (Pawn) squares[to_y-1][to_x];
                if(src.isWhite() && chump.counter == 1 ){
                    squares[to_y-1][to_x] = new Empty(new int[] {to_x, to_y-1});
                    playerWhite.removePiece(chump);
                }
            }
            if(squares[to_y+1][to_x] instanceof Pawn && squares[to_y+1][to_x].isWhite()){
                Pawn chump = (Pawn) squares[to_y+1][to_x];
                if(src.isBlack() && chump.counter == 1 ){
                    squares[to_y+1][to_x] = new Empty(new int[] {to_x, to_y+1});
                    playerWhite.removePiece(chump);
                }
            }
        }

        //castling
        if(src instanceof King){
            if(from_x - to_x == 2){
                Piece rook = squares[from_y][0];
                squares[from_y][0] = new Empty(rook.position);
                squares[to_y][to_x+1] = rook;
                rook.position = new int[]{to_x+1, to_y};
                rook.counter++;
            }

            if(from_x - to_x == - 2){
                Piece rook = squares[from_y][7];
                squares[from_y][7] = new Empty(rook.position);
                squares[to_y][to_x-1] = rook;
                rook.position = new int[]{to_x-1, to_y};
                rook.counter++;
            }
        }

        Player temp = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = temp;

    }

    /**
     * Used to encode a move from x, y coordinates to rank & file.
     * [4,1] -> E2.
     * @param move The x, y coordinates for the position we want to encode.
     * @return The rank & file string.
     */
    public static String encodeMove(int[] move){
        String encodedMoved = "";
        encodedMoved = switch (move[0]) {
            case 0 -> encodedMoved.concat("A");
            case 1 -> encodedMoved.concat("B");
            case 2 -> encodedMoved.concat("C");
            case 3 -> encodedMoved.concat("D");
            case 4 -> encodedMoved.concat("E");
            case 5 -> encodedMoved.concat("F");
            case 6 -> encodedMoved.concat("G");
            case 7 -> encodedMoved.concat("H");
            default -> "";
        };
        return encodedMoved.concat(String.valueOf(move[1]+1));
    }

    /**
     * Used to print the move list for a given piece.
     * @param moveList The move list for a given piece.
     */
    void printMoveList(ArrayList<int[]> moveList){
        if(moveList != null) {
            for (int[] entry : moveList) {
                System.out.println(encodeMove(entry));
            }
        }
        else{
            System.out.println("Empty moveList");
        }
    }

    /**
     * Used to count moves for a given piece
     * @param moveList The move list we are trying to count.
     * @return The # of moves.
     */
    int countMoves(ArrayList<int[]> moveList){
        if(moveList != null){
            return moveList.size();
        }
        return 0;
    }

    /**
     * This method is used to check whether a move is a Piece's move list.
     * @param moveList The Piece's move list.
     * @param dest The x,y coordinates for the destination position.
     * @return True/False, whether the given move is available to the Piece.
     */
    private boolean inPieceMoveList(ArrayList<int[]> moveList, int[] dest){
        if(moveList == null){
            return false;
        }
        for (int[] ints : moveList) {
            if (Arrays.equals(ints, dest)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to check whether a move is a Player's move list.
     * @param moveList The Player's move list.
     * @param dest The x,y coordinates for the destination position.
     * @return True/False, whether the given move is available to the Player.
     */
    private boolean inPlayerMoveList(ArrayList<ArrayList<int[]>> moveList, int[] dest, int[] src){

        for(ArrayList<int[]> piece: moveList){
            for(int x = 0; x < piece.size(); x+=2){
                if(Arrays.equals(piece.get(x+1), dest) && Arrays.equals(piece.get(x), src)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method is used to highlight the available moves for a given Piece.
     * @param pos The x, y coordinate for the given Piece.
     */
    public void highlightMovesFor(int[] pos){
        ArrayList<int[]> list = squares[pos[1]][pos[0]].myMoves(this);
        ArrayList<Piece.color> list_Colors = new ArrayList<>();

        for (int[] ints : list) {
            int x_ = ints[0];
            int y_ = ints[1];
            Piece piece = squares[y_][x_];


            list_Colors.add(piece.myColor);
            piece.myColor = Piece.color.Highlight;

        }


        this.print();

        for(int x = 0; x < list.size(); x++){
            int x_ = list.get(x)[0];
            int y_ = list.get(x)[1];
            Piece piece = squares[y_][x_];

            piece.myColor = list_Colors.get(x);

        }

    }

    /**
     * This method checks if any pawns are in the opponent's back rank,
     * which results in promotion.
     */
    void checkForPromotion(){
        int x, y;
        x = prevMove[0];
        y = prevMove[1];
        Piece piece = squares[y][x];
        if(piece instanceof Pawn && (y == 7 || y == 0)){
            Scanner input = new Scanner(System.in);
            System.out.println("What would you like to promote to: ");
            System.out.println("-queen\n-knight\n-bishop\n-rook");
            String option = input.nextLine();
            switch (option){
                case "queen":
                    squares[y][x] = new Queen(piece.myColor, piece.position);
                    break;
                case "knight":
                    squares[y][x] = new Knight(piece.myColor, piece.position);
                    break;
                case "bishop":
                    squares[y][x] = new Bishop(piece.myColor, piece.position);
                    break;
                case "rook":
                    squares[y][x] = new Rook(piece.myColor, piece.position);
                    break;

            }
        }
    }

    /**
     * This method is used to see if the given color is in Check.
     * @param color The player/color we are checking is in Check.
     * @param board The board we are using.
     * @return True/False, whether the player/color is in Check.
     */
    public boolean inCheck(Piece.color color, Board board){

        Board cloneBoard = board.copy();

        if(color == Piece.color.White) {
            ArrayList<ArrayList<int[]>> list = cloneBoard.playerBlack.findAvailableMoves(cloneBoard);
            for (ArrayList<int[]> moves : list) {
                for (int x = 1; x < moves.size(); x += 2) {
                    Piece capture = cloneBoard.squares[moves.get(x)[1]][moves.get(x)[0]];
                    if (capture instanceof King) {
                        return true;
                    }
                }
            }
        }
        else if(color == Piece.color.Black) {
            ArrayList<ArrayList<int[]>> list = cloneBoard.playerWhite.findAvailableMoves(cloneBoard);
            for (ArrayList<int[]> moves : list) {
                for (int x = 1; x < moves.size(); x += 2) {
                    Piece capture = cloneBoard.squares[moves.get(x)[1]][moves.get(x)[0]];
                    if (capture instanceof King) {
                        return true;
                    }
                }
            }
        }


        return false;
    }

    /**
     * Used to create a deep-copy of the Board object.
     * @return A copy of the Board object.
     */
    public Board copy(){

        Board copy = new Board();
        copy.playerWhite = this.playerWhite.copy(copy);
        copy.playerBlack = this.playerBlack.copy(copy);
        for(int y = 0; y < 8; y++){
            for(int x = 0; x< 8; x++){
                Piece og = this.squares[y][x];
                copy.squares[y][x] = og.copy();
            }
        }

        copy.prevMove = Arrays.copyOf(this.prevMove, this.prevMove.length);

        return copy;
    }

    /**
     * This is used to look at what Piece is at the given position.
     * @param position The x, y coordinate of the given position.
     * @return The piece at the given position.
     */
    public Piece lookUp(int[] position){
        return squares[position[1]][position[0]];
    }

    public Player opponent(Player player){

        if(player.myColor == Piece.color.White){
            return playerBlack;
        }
        else{
            return playerWhite;
        }
    }

}
