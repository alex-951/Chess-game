package Chess;

import Chess.Piece.*;

import java.util.Scanner;

/**
 * This class is used to run a chess game by using the static function
 * runChess().
 */
public class Chess {

    /**
     * This is the method that creates a new Board and dictates the order of player moves.
     */
    public static void runChess(Board myBoard){
        Player currPlayer = myBoard.currentPlayer;
        while(true) {
            myBoard.print();
            playerMove(myBoard);

            if(checkMate(myBoard)){
                myBoard.print();
                System.out.println("**CheckMate**");
                System.out.println(currPlayer.myColor+" Wins");
                break;
            }


        }
    }

    /**
     * This is used to take user input for each player.
     * This method also checks whether a player is in check.
     * @param board The board currently being used.
     */
    public static void playerMove(Board board){
        Piece.color currentColor = board.currentPlayer.myColor;

        System.out.println(currentColor+"'s Turn");
        if(board.inCheck(currentColor, board)){
            System.out.println("You are in check");
        }
        System.out.println();
        playerOptions(board);


    }

    /**
     * This validates whether the move input is valid or invalid.
     * @param move The move that a player would like to make (from/to).
     * @return True/False, whether the syntax is correct.
     */
    private static boolean validateMoveSyntax(String move){
        if(move.length() != 2){
            return false;
        }

        char c = move.charAt(0);
        if(c < 'A' || c > 'H'){
            return false;
        }

        char i = move.charAt(1);
        if(i < '1' || i > '8'){
            return false;
        }

        return true;
    }

    /**
     * This method checks whether the given color/player has successfully checkmated
     * the opponent.
     * @param board The board being checked.
     * @return True/False, whether the given player has won.
     */
    public static boolean checkMate(Board board){

        Player loser = board.currentPlayer;
        return loser.filterMoves(board).isEmpty();
    }

    /**
     * This method is used to create a board position based on the given
     * FEN string.
     * @param string Fen string.
     * @return A Board object that can be used by the rest of our Chess package.
     */
    static public Board inputFen(String string) {
        Board board = new Board();
        Player white = board.playerWhite;
        Player black = board.playerBlack;
        Piece[][] squares = board.squares;
        String[] stringArray = string.split("\\s+");

        //clear out default white/black pieces
        white.availablePieces.clear();
        black.availablePieces.clear();


        //generate new pieces
        int length = stringArray[0].length();
        int[] position = {0, 7};
        for(int i = 0; i < length; i++){
            Piece piece = null;
            char letter = stringArray[0].charAt(i);
            int offset = 0;
            switch (letter){
                case 'r':
                    piece = new Rook(Piece.color.Black, position);
                    break;
                case 'n':
                    piece = new Knight(Piece.color.Black, position);
                    break;
                case 'b':
                    piece = new Bishop(Piece.color.Black, position);
                    break;
                case 'q':
                    piece = new Queen(Piece.color.Black, position);
                    break;
                case 'k':
                    piece = new King(Piece.color.Black, position);
                    break;
                case 'p':
                    piece = new Pawn(Piece.color.Black, position);
                    break;
                case 'R':
                    piece = new Rook(Piece.color.White, position);
                    break;
                case 'N':
                    piece = new Knight(Piece.color.White, position);
                    break;
                case 'B':
                    piece = new Bishop(Piece.color.White, position);
                    break;
                case 'Q':
                    piece = new Queen(Piece.color.White, position);
                    break;
                case 'K':
                    piece = new King(Piece.color.White, position);
                    break;
                case 'P':
                    piece = new Pawn(Piece.color.White, position);
                    break;
                case '/':
                    offset--;
                    position[1]--;
                    position[0] = 0;
                    break;
                case '1':
                    piece = new Empty(position);
                    break;
                case '2':
                    offset += 1;
                    piece = new Empty(position);
                    break;
                case '3':
                    offset += 2;
                    piece = new Empty(position);
                    break;
                case '4':
                    offset += 3;
                    piece = new Empty(position);
                    break;
                case '5':
                    offset += 4;
                    piece = new Empty(position);
                    break;
                case '6':
                    offset += 5;
                    piece = new Empty(position);
                    break;
                case '7':
                    offset += 6;
                    piece = new Empty(position);
                    break;
                case '8':
                    offset += 7;
                    piece = new Empty(position);
                    break;
            }

            if(piece != null){
                squares[position[1]][position[0]] = piece;
                if(piece.isBlack()){
                    black.availablePieces.add(piece);
                }
                else if(piece.isWhite()){
                    white.availablePieces.add(piece);
                }
            }
            for(int j = 1; j <= offset; j++) {
                squares[position[1]][position[0] + j] = new Empty(new int[] {position[0] + j, position[1]});
            }
            position[0] += offset + 1;
        }

        //display board
        board.print();

        //validate castling options
        for(int i = 0; i < stringArray[2].length(); i++){
            switch (stringArray[2].charAt(i)){
                case '-':
                    System.out.println("No player can castle");
                    break;
                case 'K':
                    System.out.println("White can castle short-side");
                    squares[0][4].counter = 0;
                    squares[0][7].counter = 0;
                    break;
                case 'Q':
                    System.out.println("White can castle queen-side");
                    squares[0][4].counter = 0;
                    squares[0][0].counter = 0;
                    break;
                case 'k':
                    System.out.println("Black can castle short-side");
                    squares[7][4].counter = 0;
                    squares[7][7].counter = 0;
                    break;
                case 'q':
                    System.out.println("Black can castle queen-side");
                    squares[7][4].counter = 0;
                    squares[7][0].counter = 0;
                    break;
            }
        }
        System.out.println();

        //check for en-passant target square
        if(stringArray[3].equals("-")){
            System.out.println("En-passant unavailable");
        }
        else{
            System.out.println("En-passant available at target square: "+stringArray[3]);

        }
        System.out.println();

        //number of halve moves since last capture
        System.out.println("Half moves since last capture: "+ stringArray[4]);
        System.out.println();

        //number of full moves
        System.out.println("Full moves: "+stringArray[5]);
        System.out.println();

        //check the current player's turn
        if(stringArray[1].equals("w")){
            System.out.println("White to move: ");
            board.currentPlayer = board.playerWhite;
            board.nextPlayer = board.playerBlack;
        }
        else{
            System.out.println("Black to move: ");
            board.currentPlayer = board.playerBlack;
            board.nextPlayer = board.playerWhite;
        }
        return board;
    }

    public static void menu(){
        Scanner input = new Scanner(System.in);
        String selection;
        System.out.println("Welcome to Terminal Chess");
        System.out.println("1. Play a match vs. a friend");
        System.out.println("2. Resume a match using FEN string");
        do {
            System.out.print("Please make a selection: ");
            selection = input.nextLine();
        }while (!(selection.matches("1") || selection.matches("2")));


        switch(Integer.parseInt(selection)){
            case 1:
                runChess(new Board());
                break;
            case 2:
                System.out.print("Please input FEN string: ");
                selection = input.nextLine();
                runChess(Chess.inputFen(selection));
                break;
        }
    }

    private static void playerOptions(Board board){
        //create variables
        Scanner input = new Scanner(System.in);
        String selection = "0";
        String move;
        int[] from, to;

        //loop through player selection until move is made
        while (Integer.parseInt(selection) != 1) {
        System.out.println("1: Make move");
        System.out.println("2: Highlight moves for a single piece");
        System.out.println("3: List all moves");
        do{
            System.out.print("Please make a selection: ");
            selection = input.nextLine();
        }while(!selection.matches("[1-3]"));
        System.out.println();

            switch (Integer.parseInt(selection)) {

                //player wants to make a move
                case 1:
                    System.out.print("Please select a piece: ");
                    move = input.nextLine();
                    while (!validateMoveSyntax(move)) {
                        System.out.println("Invalid, try again");
                        move = input.nextLine();
                    }
                    from = Board.decodeMove(move);

                    System.out.print("Please select a destination: ");
                    move = input.nextLine();
                    while (!validateMoveSyntax(move)) {
                        System.out.println("Invalid, try again");
                        move = input.nextLine();
                    }
                    to = Board.decodeMove(move);

                    board.move(from, to, board.currentPlayer.myColor);
                    break;

                //player wants to highlight moves for a piece
                case 2:
                    System.out.print("Please select a piece: ");
                    move = input.nextLine();
                    while (!validateMoveSyntax(move)) {
                        System.out.println("Invalid, try again");
                        move = input.nextLine();
                    }
                    from = Board.decodeMove(move);
                    board.highlightMovesFor(from);
                    break;

                //player wants to highlight all moves
                case 3:
                    board.currentPlayer.printAvailableMoves(board);
                    System.out.println();
                    break;
            }
        }
    }

}
