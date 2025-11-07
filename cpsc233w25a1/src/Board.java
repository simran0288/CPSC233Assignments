import java.util.Arrays;

/**
 * CPSC 233 W25 Assignment 1 Board class
 *
 * The Board class represents the game board for Connect-L, a two-player game
 * where players alternate placing pieces on a grid. The board is used to track
 * the state of the game and determine if a player has won by aligning a certain
 * number of their pieces in a row, column, or diagonal.
 *
 * The board is stored as a 2D array, where each cell represents a position on the
 * board. The possible values for each cell are:
 * - 0 (EMP): The space is empty, no piece has been placed here.
 * - 1 (RED): A red player's piece is placed here.
 * - 2 (BLU): A blue player's piece is placed here.
 *
 * The Board class provides functionality to:
 * - Initialize the board with the specified number of rows and columns.
 * - Place a player's piece on the board at a specified column.
 * - Check if a column or row has been filled.
 * - Detect if a player has won by aligning pieces horizontally, vertically, or diagonally.
 * - Remove the last placed piece from a column if necessary.
 *
 * It interacts with the Game class to retrieve constants representing the red and blue
 * pieces and ensures that game logic, such as turn-taking and win conditions, are properly enforced.
 *
 * This class is a key part of the Connect-L game implementation and manages the core
 * functionality of maintaining the board state and checking for winning conditions.
 *
 * @author Simrandeep Kaur
 * @email simrandeep.simrandee@ucalgary.ca
 * Tutorial T06
 * Date Jan 29, 2025
 * @version 1.1
 */
public class Board {

    /**
     * No piece in board (empty)
     */
    public static final int EMP = Game.EMP;
    /**
     * Connect-L Red Piece
     */
    public static final int RED = Game.RED;
    /**
     * Connect-L Blue Piece
     */
    public static final int BLU = Game.BLU;

    //Students should enter their functions below here

    /**
     * Creates a 2D board with the specified number of rows and columns.
     * The board is initialized with zeros (representing empty spaces).
     *
     * @param rows    The number of rows in the board.
     * @param columns The number of columns in the board.
     * @return A 2D integer array representing the game board.
     */
    public static int[][] createBoard(int rows, int columns) {
        int[][] board = new int[rows][columns]; // Create a 2D array to represent the board.
        return board; // Return the initialized board.
    }

    /**
     * Returns the number of rows in the board.
     *
     * @param board The 2D integer array representing the game board.
     * @return The number of rows in the board.
     */
    public static int rowCount(int[][] board) {
        return board.length; // Return the length of the board, which corresponds to the number of rows.
    }

    /**
     * Returns the number of columns in the board.
     *
     * @param board The 2D integer array representing the game board.
     * @return The number of columns in the board.
     */
    public static int columnCount(int[][] board) {
        return board[0].length; // Return the length of the first row, which corresponds to the number of columns.
    }

    /**
     * Checks if the given row and column are valid indices for the board.
     *
     * @param board The 2D integer array representing the game board.
     * @param row The row index to be checked.
     * @param column The column index to be checked.
     * @return true if the row and column are within valid bounds of the board, false otherwise.
     */
    public static boolean valid(int[][] board, int row, int column) {
        int totalRows = rowCount(board); // Get the total number of rows in the board.
        int totalColumns = columnCount(board); // Get the total number of columns in the board.
        return (row >= 0 && row < totalRows) && (column >= 0 && column < totalColumns); // Check if the indices are within valid bounds.
    }

    /**
     * Checks if there is room left to play a piece in the given column.
     *
     * @param board The 2D integer array representing the game board.
     * @param column The column index where the player wants to play.
     * @return true if there is room to play in the specified column, false otherwise.
     */
    public static boolean canPlay(int[][] board, int column) {
        for (int row = 0; row < board.length; row++) { // Iterate through each row in the column.
            if (board[row][column] == EMP) { // Check if the current position is empty.
                return true; // Return true if space is available.
            }
        }
        return false; // Return false if there is no space left in the column.
    }

    /**
     * Plays a piece in the given column, and returns the row where the piece landed.
     *
     * @param board The 2D integer array representing the game board.
     * @param column The column index where the player wants to play the piece.
     * @param piece The piece to be played (RED == 1, BLU == 2).
     * @return The row index where the piece landed, or -1 if there is no room to play.
     */
    public static int play(int[][] board, int column, int piece) {
        int totalRows = rowCount(board); // Get the total number of rows in the board.
        for (int row = totalRows-1; row >= 0; row--) { // Loop through rows from the bottom to top.
            if (board[row][column] == EMP) { // Check if the position is empty.
                board[row][column] = piece; // Place the piece in the empty spot.
                return row; // Return the row where the piece was placed.
            }
        }
        return -1; // Return -1 if the column is full and no piece can be placed.
    }

    /**
     * Removes the last played piece from the top-most row in the given column.
     *
     * @param board The 2D integer array representing the game board.
     * @param column The column index from which the last played piece will be removed.
     * @return The row index from which the piece was removed, or -1 if the column is empty.
     */
    public static int removeLastPlay(int[][] board, int column) {
        int topMostRowWithPiece; // Variable to store the index of the top-most row with a piece.
        for (topMostRowWithPiece = 0; topMostRowWithPiece < rowCount(board); topMostRowWithPiece++) {
            if (board[topMostRowWithPiece][column] != EMP) { // Check if the current row has a piece.
                board[topMostRowWithPiece][column] = EMP;  // Remove the piece by setting the spot to 0.
                return topMostRowWithPiece; // Return the row where the piece was removed from.
            }
        }
        return -1; // Return -1 if there is no piece to remove (the column is empty).
    }

    /**
     * Checks if the game board is full (i.e., no empty spots).
     *
     * @param board The 2D integer array representing the game board.
     * @return true if the board is full, false otherwise.
     */
    public static boolean full(int[][] board) {
        for (int row = 0; row < board.length; row++) { // Iterate through each row.
            for (int column = 0; column < columnCount(board); column++) { // Iterate through each column in the row.
                if (board[row][column] == EMP) { // Check if the current spot is empty (0).
                    return false; // If there is an empty spot, return false.
                }
            }
        }
        return true; // If no empty spots are found, return true (the board is full).
    }

    /**
     * Checks if there is a winning sequence of consecutive pieces in the specified row.
     *
     * @param board The 2D integer array representing the game board.
     * @param row The row index to check for a winning sequence.
     * @param piece The piece (RED==1, BLU==2) to check for a winning sequence.
     * @param length The required length of consecutive pieces for a win.
     * @return true if a winning sequence exists, false otherwise.
     */
    public static boolean winInRow(int[][] board, int row, int piece, int length) {
        int numOfConsecutivePieces;
        int totalColumns = columnCount(board); // Get the number of columns in the board.
        for (int col = 0; col < totalColumns; col++) { // Iterate through each column in the specified row.
            if (board[row][col] == piece) { // Check if the current position has the piece.
                numOfConsecutivePieces = 1; // Start counting consecutive pieces.
                if ((valid(board, row - 1, col) && board[row - 1][col] == piece) ||
                        (valid(board, row + 1, col) && board[row + 1][col] == piece)){ //Check if there is perpendicular piece at the beginning.
                    int loopOneColumn = col+1; // Start checking from the next column.
                    while (valid(board,row,loopOneColumn)&& board[row][loopOneColumn] == piece) {
                        numOfConsecutivePieces++; // Increment the count of consecutive pieces.
                        loopOneColumn++; // Move to the next column.
                        if (numOfConsecutivePieces >= length) { // Check if the length is met.
                            return true; // A win is found, return true.
                        }
                    }
                }
                else {
                    for (int loopThreeColumn = col + 1; loopThreeColumn < totalColumns; loopThreeColumn++) {
                        if (board[row][loopThreeColumn] == piece) { // Check if the piece matches.
                            numOfConsecutivePieces++; // Increment the count of consecutive pieces.
                            if (numOfConsecutivePieces >= length) { // Check if the length is met.
                                if ((valid(board, row - 1, loopThreeColumn) && board[row - 1][loopThreeColumn] == piece) ||
                                        (valid(board, row + 1, loopThreeColumn) && board[row + 1][loopThreeColumn]
                                                == piece)) { // Check if there is perpendicular piece at the end.
                                    return true; // A win is found, return true.
                                }
                            }
                        } else
                            numOfConsecutivePieces = 0; // Reset if pieces don't match.
                    }
                }
            }
        }
        return false; // No winning sequence found.
    }

    /**
     * Checks if there is a winning sequence of consecutive pieces in the specified column.
     *
     * @param board The 2D integer array representing the game board.
     * @param column The column index to check for a winning sequence.
     * @param piece The piece (RED==1, BLU==2) to check for a winning sequence.
     * @param length The required length of consecutive pieces for a win.
     * @return true if a winning sequence exists, false otherwise.
     */
    public static boolean winInColumn(int[][] board, int column, int piece, int length) {
        int numOfConsecutivePieces;
        int totalRows = rowCount(board); // Get the number of rows in the board.
        for (int row = totalRows-1; row >= 0; row--) { // Iterate through each row in the specified column.
            if (board[row][column] == piece) { // Check if the current position has the piece.
                numOfConsecutivePieces = 1; // Start counting consecutive pieces.
                // Check if any of the perpendicular cells to the column contain the same piece.
                if ((valid(board, row, column-1) && (board[row][column - 1] == piece)) ||
                        (valid(board, row, column+1) && board[row][column+1] == piece)){
                    int loopOneRow = row-1; // Start checking from the previous row.
                    while (valid(board,loopOneRow,column) && board[loopOneRow][column] == piece) {
                        numOfConsecutivePieces++; // Increment the count of consecutive pieces.
                        loopOneRow--; // Move to the previous row.
                        if (numOfConsecutivePieces >= length) { // Check if the length is met.
                            return true; // A win is found, return true.
                        }
                    }
                }
                else {
                    for (int loopThreeRow = row - 1; loopThreeRow >= 0; loopThreeRow--) { // Iterate over each row from bottom to top
                        if (board[loopThreeRow][column] == piece) { // Check if the piece matches.
                            numOfConsecutivePieces++; // Increment the count of consecutive pieces.
                            if (numOfConsecutivePieces >= length){ // Check if the length is met.
                                // Check if any of the perpendicular cells to the column contain the same piece.
                                if ((valid(board,loopThreeRow,column-1) && board[loopThreeRow][column - 1] == piece) ||
                                        (valid(board, loopThreeRow, column+1) && board[loopThreeRow][column + 1] == piece)) {
                                    return true; // A win is found, return true.
                                }
                            }
                        }
                        else
                            numOfConsecutivePieces = 0; // Reset if pieces don't match.
                    }
                }
            }
        }
        return false; // No winning sequence found.
    }

    /**
     * Checks if there is a winning sequence of consecutive pieces in a diagonal from top-right to bottom-left (backslash direction).
     *
     * @param board The 2D integer array representing the game board.
     * @param piece The piece (RED==1, BLU==2) to check for a winning sequence.
     * @param length The required length of consecutive pieces for a win.
     * @return true if a winning sequence exists in the backslash diagonal, false otherwise.
     */
    public static boolean winInDiagonalBackslash(int[][] board, int piece, int length) {
        int numOfConsecutivePieces;
        int totalColumns = columnCount(board); // Get the number of columns in the board.
        int totalRows = rowCount(board); // Get the number of rows in the board.
        // Loop through each row and column to find the first occurrence of the piece.
        for (int loopOnerow = totalRows-1; loopOnerow >= 0; loopOnerow--) {
            for (int loopOneColumn = totalColumns-1; loopOneColumn >= 0; loopOneColumn--) {
                if (board[loopOnerow][loopOneColumn] == piece) { // Check if the current position contains the piece.
                    // Check if any of the perpendicular cells to the diagonal contain the same piece.
                    if ((valid(board,loopOnerow-1,loopOneColumn+1) && board[loopOnerow-1][loopOneColumn+1] == piece) ||
                            (valid(board,loopOnerow+1,loopOneColumn-1) && board[loopOnerow+1][loopOneColumn-1] == piece)){
                        numOfConsecutivePieces = 1; // Start counting consecutive pieces.
                        int loopTwoRow = loopOnerow-1;
                        int loopTwoColumn = loopOneColumn-1;
                        while ((valid(board,loopTwoRow,loopTwoColumn) && board[loopTwoRow][loopTwoColumn] == piece)){
                            numOfConsecutivePieces++; // Increment the count of consecutive pieces.
                            loopTwoColumn--; // Move diagonally left and up.
                            loopTwoRow--; // Move diagonally left and up.
                            if (numOfConsecutivePieces >= length){ // Check if the length is met.
                                return true; // A win is found, return true.
                            }
                        }
                    }
                    else {
                        int loopThreeRow = loopOnerow-1;
                        int loopThreeColumn = loopOneColumn-1;
                        numOfConsecutivePieces = 1; // Start counting consecutive pieces.
                        while (loopThreeRow >= 0 && loopThreeColumn >= 0 && board[loopThreeRow][loopThreeColumn] == piece) {
                            numOfConsecutivePieces++; // Increment the count of consecutive pieces.
                            if (numOfConsecutivePieces >= length) { // Check if the length is met.
                                // Check if any of the perpendicular cells have the same piece.
                                if ((valid(board, loopThreeRow - 1, loopThreeColumn + 1) && (board[loopThreeRow - 1][loopThreeColumn + 1] == piece) ||
                                        (valid(board, loopThreeRow + 1, loopThreeColumn - 1) && board[loopThreeRow + 1][loopThreeColumn - 1] == piece))) {
                                    return true; // A win is found, return true.
                                }
                            }
                            loopThreeColumn--; // Move diagonally left and up.
                            loopThreeRow--; // Move diagonally left and up.
                        }
                    }
                }
            }
        }
        return false; // No winning sequence found.
    }

    /**
     * Checks if there is a winning sequence of consecutive pieces in a diagonal from bottom-left to top-right (forward slash direction).
     *
     * @param board The 2D integer array representing the game board.
     * @param piece The piece (RED==1, BLU==2) to check for a winning sequence.
     * @param length The required length of consecutive pieces for a win.
     * @return true if a winning sequence exists in the forward slash diagonal, false otherwise.
     */
    public static boolean winInDiagonalForwardSlash(int[][] board, int piece, int length){
        int numOfConsecutivePieces;
        int totalColumns = columnCount(board); // Get the number of columns in the board.
        int totalRows = rowCount(board); // Get the number of rows in the board.
        // Loop through each row and column to find the first occurrence of the piece.
        for (int loopOneRow = totalRows-1; loopOneRow >= 0; loopOneRow--) {
            for (int loopOneColumn = 0; loopOneColumn < totalColumns; loopOneColumn++) {
                if (board[loopOneRow][loopOneColumn] == piece) { // Check if the current position contains the piece.
                    //Check if any of the perpendicular cells has the same piece.
                    if ((valid(board, loopOneRow - 1, loopOneColumn - 1) && board[loopOneRow - 1][loopOneColumn - 1] == piece) ||
                            (valid(board, loopOneRow + 1, loopOneColumn + 1) && board[loopOneRow + 1][loopOneColumn + 1] == piece)) {
                        numOfConsecutivePieces = 1; // Start counting consecutive pieces.
                        int loopTwoRow = loopOneRow - 1;
                        int loopTwoColumn = loopOneColumn + 1;
                        while (valid(board, loopTwoRow, loopTwoColumn) && board[loopTwoRow][loopTwoColumn] == piece) {
                            numOfConsecutivePieces++; // Increment the count of consecutive pieces.
                            loopTwoRow--; // Move up.
                            loopTwoColumn++; // Move right
                            if (numOfConsecutivePieces >= length) { // Check if the length is met.
                                return true; // A win is found, return true.
                            }
                        }
                    }else {
                        numOfConsecutivePieces = 1; // Start counting consecutive pieces.
                        //Iterate through each diagonal cell.
                        for (int loopThreeRow = loopOneRow - 1, loopThreeColumn = loopOneColumn + 1; loopThreeRow >= 0
                                && loopThreeColumn < totalColumns; loopThreeRow--, loopThreeColumn++) {
                            if (board[loopThreeRow][loopThreeColumn] == piece) { //Check if the current cell has the same piece.
                                numOfConsecutivePieces++;  // Increment the count of consecutive pieces.
                                if (numOfConsecutivePieces >= length) { // Check if the length is met.
                                    //Check if any of the perpendicular cells has the same piece.
                                    if ((valid(board, loopThreeRow - 1, loopThreeColumn - 1) &&
                                            board[loopThreeRow - 1][loopThreeColumn - 1] == piece) ||
                                            (valid(board, loopThreeRow + 1, loopThreeColumn + 1) &&
                                                    board[loopThreeRow + 1][loopThreeColumn + 1] == piece)){
                                        return true; // A win is found, return true
                                    }
                                }
                            } else
                                numOfConsecutivePieces = 0; // Reset the count if no match is found.
                        }
                    }
                }
            }
        }
        return false; // No winning sequence found.
    }

    /**
     * Provides a hint for the next move by determining a column where placing a piece
     * would result in a winning move (either in row, column, or diagonal) or blocking the opponent from winning.
     *
     * @param board The 2D integer array representing the game board.
     * @param piece The piece (RED==1, BLU==2) for which the hint is being calculated.
     * @param length The required length of consecutive pieces for a win.
     * @return An array where the first element is the row and the second element is the column
     *         for a winning move, or [-1, -1] if no winning move is available.
     */
    public static int[] hint(int[][] board, int piece, int length) {
        int[] array = new int[2]; // Array to store the row and column of the potential winning move.
        // Loop through each column to check if a move can be made.
        for (int column = 0; column < columnCount(board); column++) {
            if (canPlay(board, column)){
                int row = play(board,column,piece); // Play the piece in the column.
                // Check if the move results in a win in any row, column, or diagonal.
                if (winInAnyColumn(board, piece, length) || winInAnyRow(board, piece, length) ||
                        winInAnyDiagonal(board, piece, length)){
                    removeLastPlay(board, column); // Undo the move if it results in a win.
                    array = new int[]{row, column}; // Store the row and column of the winning move.
                    return array; // Return the winning move.
                }
                else{
                    removeLastPlay(board, column); // Undo the move if no win is found.

                }

            }

        }
        // Return [-1, -1] if no winning move is found.
        array[0] = -1;
        array[1] = -1;
        return array;
    }


    //Students should enter their functions above here

    /**
     * Is there a win in given board in any row of board
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @param piece The piece to look for length in a row for any row
     * @return True if there is length in any row, False otherwise
     */
    private static boolean winInAnyRow(int[][] board, int piece, int length) {
        for (int row = 0; row < board.length; row++) {
            if (winInRow(board, row, piece, length)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is there a win in given board in any column of board
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @param piece The piece to look for length in a row for any column
     * @return True if there is length in any column, False otherwise
     */
    private static boolean winInAnyColumn(int[][] board, int piece, int length) {
        for (int col = 0; col < board[0].length; col++) {
            if (winInColumn(board, col, piece, length)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Is there a win in given board in any diagonal of board
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @param piece The piece to look for length in a row for any diagonal
     * @return True if there is length in any diagonal /\, False otherwise
     */
    private static boolean winInAnyDiagonal(int[][] board, int piece, int length) {
        return winInDiagonalBackslash(board, piece, length) || winInDiagonalForwardSlash(board, piece, length);
    }

    /**
     * Has the given piece won the board
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @param piece The piece to check for a win
     * @return True if piece has won
     */
    public static boolean won(int[][] board, int piece, int length) {
        return winInAnyRow(board, piece, length) || winInAnyColumn(board, piece, length) || winInAnyDiagonal(board, piece, length);
    }

    /**
     * This function determines if the game is complete due to a win or tie by either player
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @return True if game is complete, False otherwise
     */
    public static boolean isGameOver(int[][] board, int length) {
        return full(board) || won(board, RED, length) || won(board, BLU, length);
    }

}
