import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CPSC 233 W25 Assignment 1 BoardTest Class
 * Holds a helper deep copy, example tests of deep copy and tests for all functions of Board class.
 * @author Simrandeep Kaur
 * @email simrandeep.simrandee@ucalgary.ca
 * Tutorial T06
 * Date Feb 09, 2025
 * @version 1.1
 */
public class BoardTest {
    /**
     * Used to make a copy of board before functions run, so that verify a function was non-destructive on board is easy
     * @param board The board to make deep copy of
     * @return A deep copy of given board
     */
    public int[][] deepCopy(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return copy;
    }

    @Test
    public void deepCopyTestWithoutDeepEquals() {
        int[][] expected = new int[][]{{0, 1}};
        int[][] actual = deepCopy(expected);
        assertEquals(expected[0][0], actual[0][0]);
        assertEquals(expected[0][1], actual[0][1]);
    }


    @Test
    public void deepCopyTestNoChange() {
        int[][] expected = new int[][]{{0, 1}};
        int[][] actual = deepCopy(expected);
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    public void deepCopyTestChangeEntryIn2D() {
        int[][] expected = new int[][]{{0, 1}};
        int[][] actual = deepCopy(expected);
        actual[0][0] = 99;
        assertFalse(Arrays.deepEquals(expected, actual));
    }

    @Test
    public void deepCopyTestSet1DRefToDiffButIdenticalArray() {
        int[][] expected = new int[][]{{0, 1}};
        int[][] actual = deepCopy(expected);
        actual[0] = new int[]{0,1};
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    public void deepCopyTestSet1DRefToDiffArray() {
        int[][] expected = new int[][]{{0, 1}};
        int[][] actual = deepCopy(expected);
        actual[0] = new int[]{0,99};
        assertFalse(Arrays.deepEquals(expected, actual));
    }

    @Test
    void createBoardTest1() {
        // Create 6X6 board with all sells set to Board.EMP
        int[][] expected = new int[][]{{Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
        {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
        {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
        {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
        {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
        {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP}};
        // Create same size board with createBoard function
        int[][] actual = Board.createBoard(6,6);
        // Compare the expected and actual boards to ensure they are equal.
        assertTrue(Arrays.deepEquals(expected, actual));
    }
    @Test
    void createBoardTest2() {
        // Expected 5x4 empty board.
        int[][] expected = new int[][]{{Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP}};
        // Create same size board with createBoard function
        int[][] actual = Board.createBoard(5,4);
        // Compare the expected and actual boards.
        assertTrue(Arrays.deepEquals(expected, actual));
    }
    @Test
    void createBoardTest3() {
        // Expected 4x6 empty board.
        int[][] expected = new int[][]{{Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP}};
        // Create same size board with createBoard function
        int[][]actual = Board.createBoard(4,6);
        // Compare the expected and actual boards.
        assertTrue(Arrays.deepEquals(expected, actual));
    }
    @Test
    void createBoardTest4() {
        // Expected 4x8 empty board.
        int[][] expected = new int[][]{{Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP, Board.EMP}};
        // Create same size board with createBoard function
        int[][] actual = Board.createBoard(4, 8);
        // Compare the expected and actual boards.
        assertTrue(Arrays.deepEquals(expected, actual));
    }
    @Test
    void createBoardTest5() {
        // Expected 8x4 empty board.
        int[][] expected = new int[][]{{Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},
                {Board.EMP, Board.EMP, Board.EMP, Board.EMP},};
        // Create same size board with createBoard function
        int[][] actual = Board.createBoard(8, 4);
        // Compare the expected and actual boards.
        assertTrue(Arrays.deepEquals(expected, actual));
    }
    @Test
    void rowCountTest1() {
        // Create a board with 8 rows and 4 columns.
        int[][] board = Board.createBoard(8, 4);
        // Expected row count is 8.
        int expected = 8;
        // Get the actual row count from the rowCount function.
        int actual = Board.rowCount(board);
        // Assert that the expected and actual row counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void rowCountTest2() {
        // Create a board with 4 rows and 8 columns.
        int[][] board = Board.createBoard(4, 8);
        // Expected row count is 4
        int expected = 4;
        // Get the actual row count from the rowCount function.
        int actual = Board.rowCount(board);
        // Assert that the expected and actual row counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void rowCountTest3() {
        // Create a board with 7 rows and 4 columns.
        int[][] board = Board.createBoard(7, 4);
        // Expected row count is 7
        int expected = 7;
        // Get the actual row count from the rowCount function.
        int actual = Board.rowCount(board);
        // Assert that the expected and actual row counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void rowCountTest4() {
        // Create a board with 5 rows and 7 columns.
        int[][] board = Board.createBoard(5, 7);
        // Expected row count is 5
        int expected = 5;
        // Get the actual row count from the rowCount function.
        int actual = Board.rowCount(board);
        // Assert that the expected and actual row counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void rowCountTest5() {
        // Create a board with 6 rows and 6 columns.
        int[][] board = Board.createBoard(6, 6);
        // Expected row count is 6
        int expected = 6;
        // Get the actual row count from the rowCount function.
        int actual = Board.rowCount(board);
        // Assert that the expected and actual row counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void columnCountTest1() {
        // Create a board with 4 rows and 5 columns.
        int[][] board = Board.createBoard(4, 5);
        // Expected column count is 5
        int expected = 5;
        // Get the actual column count using the columnCount function
        int actual = Board.columnCount(board);
        // Assert that the expected and actual column counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void columnCountTest2() {
        // Create a board with 8 rows and 6 columns.
        int[][] board = Board.createBoard(8, 6);
        // Expected column count is 6
        int expected = 6;
        // Get the actual column count using the columnCount function
        int actual = Board.columnCount(board);
        // Assert that the expected and actual column counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void columnCountTest3() {
        // Create a board with 8 rows and 7 columns.
        int[][] board = Board.createBoard(8, 7);
        // Expected column count is 7
        int expected = 7;
        // Get the actual column count using the columnCount function
        int actual = Board.columnCount(board);
        // Assert that the expected and actual column counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void columnCountTest4() {
        // Create a board with 7 rows and 4 columns.
        int[][] board = Board.createBoard(7, 4);
        // Expected column count is 4
        int expected = 4;
        // Get the actual column count using the columnCount function
        int actual = Board.columnCount(board);
        // Assert that the expected and actual column counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void columnCountTest5() {
        // Create a board with 8 rows and 8 columns.
        int[][] board = Board.createBoard(8, 8);
        // Expected column count is 8
        int expected = 8;
        // Get the actual column count using the columnCount function
        int actual = Board.columnCount(board);
        // Assert that the expected and actual column counts are equal.
        assertEquals(expected, actual);
    }
    @Test
    void validTest1() {
        // Create an 8x8 board.
        int [][] board = Board.createBoard(8, 8);
        // Expected result is true for a valid position at (7, 7).
        boolean expected = true;
        // Check if the position is valid using valid function.
        boolean actual = Board.valid(board,7,7);
        // Assert that the expected and actual results are equal.
        assertEquals(expected, actual);
    }
    @Test
    void validTest2() {
        // Create an 6x7 board.
        int[][] board = Board.createBoard(6, 7);
        // Expected result is false for an invalid position (6, 6) since it's out of bounds.
        boolean expected = false;
        // Check if the position is valid using valid function.
        boolean actual = Board.valid(board, 6, 6);
        // Assert that the expected and actual results are equal.
        assertEquals(expected, actual);
    }
    @Test
    void validTest3() {
        // Create an 4x4 board.
        int[][] board = Board.createBoard(4, 4);
        // Expected result is false for an invalid position (3, 5) since it's out of bounds.
        boolean expected = false;
        // Check if the position is valid using valid function.
        boolean actual = Board.valid(board, 3, 5);
        // Assert that the expected and actual results are equal.
        assertEquals(expected, actual);
    }
    @Test
    void validTest4() {
        // Create an 7x4 board.
        int[][] board = Board.createBoard(7, 4);
        // Expected result is true for a valid position at (0, 1).
        boolean expected = true;
        // Check if the position is valid using valid function.
        boolean actual = Board.valid(board, 0, 1);
        // Assert that the expected and actual results are equal.
        assertEquals(expected, actual);
    }
    @Test
    void validTest5() {
        // Create an 5x5 board.
        int[][] board = Board.createBoard(5, 5);
        // Expected result is false for an invalid position (5, 4) since it's out of bounds.
        boolean expected = false;
        // Check if the position is valid using valid function.
        boolean actual = Board.valid(board, 5, 4);
        // Assert that the expected and actual results are equal.
        assertEquals(expected, actual);
    }
    @Test
    void canPlayTest1() {
        int[][] board = Board.createBoard(8, 8);
        boolean expected = true;
        boolean actual = Board.canPlay(board, 7); // Check if column 7 is playable (should be true since it's empty)
        assertEquals(expected, actual);
    }

    @Test
    void canPlayTest2() {
        int[][] board = Board.createBoard(8, 8);
        board[7][7] = Board.RED; // Mark the bottom of column 7 as filled
        boolean expected = true;
        boolean actual = Board.canPlay(board, 7); // Check if the column still has space
        assertEquals(expected, actual);
    }

    @Test
    void canPlayTest3() {
        int[][] board = Board.createBoard(6, 7);
        board[5][3] = Board.RED; // Mark the spot at row 5, column 3 as filled
        boolean expected = true;
        boolean actual = Board.canPlay(board, 3); // Check if the column still has space
        assertEquals(expected, actual);
    }

    @Test
    void canPlayTest4() {
        int[][] board = Board.createBoard(4, 5);
        for (int row = 0; row < 4; row++) {
            board[row][2] = Board.BLU; // Fill the entire column 2
        }
        boolean expected = false;
        boolean actual = Board.canPlay(board, 2); // Column 2 is completely filled, check if playable
        assertEquals(expected, actual);
    }

    @Test
    void canPlayTest5() {
        int[][] board = Board.createBoard(7, 6);
        for (int row = 0; row < 7; row++) {
            board[row][5] = Board.RED; // Fill the entire column 5
        }
        boolean expected = false;
        boolean actual = Board.canPlay(board, 5); // Column 5 is completely filled, so it should not be playable
        assertEquals(expected, actual);
    }
    @Test
    void playTest1() {
        int[][] board = Board.createBoard(6, 7);
        int expected = 1; // The piece will be placed in the last row (index 5) of column 0
        Board.play(board, 0, Board.RED); // Play red piece in column 0
        int actual = board[5][0]; //Check if the function placed the piece in correct row
        assertEquals(expected, actual);
    }

    @Test
    void playTest2() {
        int[][] board = Board.createBoard(6, 7);
        board[5][2] = Board.RED; // Fill the last row of column 2
        int expected = 4; // The piece will be placed in row 4 of column 2
        int actual = Board.play(board, 2, Board.BLU); // Play blue piece in column 2
        assertEquals(expected, actual);
    }

    @Test
    void playTest3() {
        int[][] board = Board.createBoard(6, 7);
        board[5][3] = Board.RED; // Fill the last row of column 3
        board[4][3] = Board.RED; // Fill row 4 of column 3
        int expected = 3; // The piece will be placed in row 3 of column 3
        int actual = Board.play(board, 3, Board.BLU); // Play blue piece in column 3
        assertEquals(expected, actual);
    }

    @Test
    void playTest4() {
        int[][] board = Board.createBoard(6, 7);
        for (int row = 0; row < 6; row++) {
            board[row][6] = Board.RED; // Fill column 6 completely
        }
        int expected = -1; // Column 6 is full, no piece can be placed
        int actual = Board.play(board, 6, Board.BLU); // Try playing blue piece in column 6
        assertEquals(expected, actual);
    }

    @Test
    void playTest5() {
        int[][] board = Board.createBoard(6, 7);
        int expected = 5; // The piece will be placed in the first row (index 0) of column 4
        int actual = Board.play(board, 4, Board.RED); // Play red piece in column 4
        assertEquals(expected, actual);
    }
    @Test
    void removeLastPlayTest1() {
        int[][] board = Board.createBoard(6, 7);
        board[5][3] = Board.RED; // Place a piece in row 5, column 3
        int expected = 5; // The piece is removed from row 5
        int actual = Board.removeLastPlay(board, 3); // Remove the last piece in column 3
        assertEquals(expected, actual); // Check if the returned row is correct
        assertEquals(Board.EMP, board[5][3]); // Verify that the spot is now empty
    }

    @Test
    void removeLastPlayTest2() {
        int[][] board = Board.createBoard(8, 8);
        board[4][2] = Board.RED; // Place a piece in row 4, column 2
        int expected = 4; // The piece is removed from row 4
        int actual = Board.removeLastPlay(board, 2); // Remove the last piece in column 2
        assertEquals(expected, actual); // Check if the returned row is correct
        assertEquals(Board.EMP, board[4][2]); // Verify that the spot is now empty
    }

    @Test
    void removeLastPlayTest3() {
        int[][] board = Board.createBoard(5, 8);
        int expected = -1; // No pieces to remove, as column 6 is empty
        int actual = Board.removeLastPlay(board, 6); // Try removing from an empty column
        assertEquals(expected, actual); // Check if the returned value is -1
    }

    @Test
    void removeLastPlayTest4() {
        int[][] board = Board.createBoard(6, 7);
        board[5][1] = Board.RED; // Place a piece in row 5, column 1
        board[3][1] = Board.RED; // Place a piece in row 3, column 1
        int expected = 3; // The piece is removed from row 3, the next piece in the column
        int actual = Board.removeLastPlay(board, 1); // Remove the last piece in column 1
        assertEquals(expected, actual); // Check if the returned row is correct
        assertEquals(Board.EMP, board[3][1]); // Verify that the spot is now empty
    }

    @Test
    void removeLastPlayTest5() {
        int[][] board = Board.createBoard(6, 7);
        board[0][0] = Board.RED; // Place a piece in row 0, column 0
        int expected = 0; // The piece is removed from row 0
        int actual = Board.removeLastPlay(board, 0); // Remove the last piece in column 0
        assertEquals(expected, actual); // Check if the returned row is correct
        assertEquals(Board.EMP, board[0][0]); // Verify that the spot is now empty
    }
    @Test
    void fullTest1() {
        int[][] board = Board.createBoard(8, 7);
        board[0][0] = Board.RED; // Place a piece in row 0, column 0
        board[0][1] = Board.RED; // Place a piece in row 0, column 1
        // Other spots are empty
        boolean expected = false; // The board is not full
        boolean actual = Board.full(board);
        assertEquals(expected, actual); // Check if the board is not full
    }

    @Test
    void fullTest2() {
        int[][] board = Board.createBoard(6, 8);
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 8; column++) {
                board[row][column] = Board.RED; // Fill the board completely
            }
        }
        boolean expected = true; // The board is full
        boolean actual = Board.full(board);
        assertEquals(expected, actual); // Check if the board is full
    }

    @Test
    void fullTest3() {
        int[][] board = Board.createBoard(4, 5);
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 5; column++) {
                if (row == 3 && column == 4) {
                    board[row][column] = Board.EMP; // Leave one spot empty
                } else {
                    board[row][column] = Board.RED; // Fill the rest
                }
            }
        }
        boolean expected = false; // The board is not full
        boolean actual = Board.full(board);
        assertEquals(expected, actual); // Check if the board is not full
    }

    @Test
    void fullTest4() {
        int[][] board = Board.createBoard(5, 5);
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                board[row][column] = Board.RED; // Fill the board completely
            }
        }
        board[4][4] = Board.EMP; // Leave the last spot empty
        boolean expected = false; // The board is not full
        boolean actual = Board.full(board);
        assertEquals(expected, actual); // Check if the board is not full
    }

    @Test
    void fullTest5() {
        int[][] board = Board.createBoard(5, 5);
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                board[row][column] = Board.RED; // Fill the board completely
            }
        }
        boolean expected = true; // The board is full
        boolean actual = Board.full(board);
        assertEquals(expected, actual); // Check if the board is full
    }
    @Test
    void winInRowTest1() {
        int[][] board = Board.createBoard(7, 7);
        board[3][0] = Board.RED; board[3][1] = Board.RED; board[3][2] = Board.RED; board[3][3] = Board.RED; // 4 pieces in a row
        int row = 3;
        int piece = Board.RED;
        int length = 4;
        boolean expected = false; // We do not expect a win with 4 consecutive pieces in a row without a perpendicular piece
        boolean actual = Board.winInRow(board, row, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInRowTest2() {
        int[][] board = Board.createBoard(6, 7);
        board[2][3] = Board.RED; board[2][4] = Board.RED; board[2][5] = Board.RED; // 3 pieces in a row, no perpendicular piece
        int row = 2;
        int piece = Board.RED;
        int length = 3;
        boolean expected = false; // There is no perpendicular piece, hence no win
        boolean actual = Board.winInRow(board, row, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInRowTest3() {
        int[][] board = Board.createBoard(6, 7);
        board[4][2] = Board.RED; board[4][3] = Board.RED; board[4][4] = Board.RED; // 3 pieces in a row
        board[3][3] = Board.RED; // Perpendicular piece added at the middle
        int row = 4;
        int piece = Board.RED;
        int length = 3;
        boolean expected = false; // We do not expect a win with 3 consecutive pieces and a perpendicular piece in middle
        boolean actual = Board.winInRow(board, row, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInRowTest4() {
        int[][] board = Board.createBoard(6, 7);
        board[1][2] = Board.BLU; board[1][3] = Board.BLU; board[1][4] = Board.BLU; // 3 pieces in a row
        board[0][2] = Board.BLU; // Perpendicular piece added at the start
        int row = 1;
        int piece = Board.BLU;
        int length = 3;
        boolean expected = true; // We expect a win with 3 consecutive pieces and a perpendicular piece at the start
        boolean actual = Board.winInRow(board, row, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInRowTest5() {
        int[][] board = Board.createBoard(6, 7);
        board[5][0] = Board.RED; board[5][1] = Board.RED; board[5][2] = Board.RED; board[5][3] = Board.RED; // 4 pieces in a row
        board[4][3] = Board.RED; // Perpendicular piece added at the end
        int row = 5;
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // We expect a win with 4 consecutive pieces and a perpendicular piece at the end
        boolean actual = Board.winInRow(board, row, piece, length);
        assertEquals(expected, actual);
    }
    @Test
    void winInColumnTest1() {
        int[][] board = Board.createBoard(6, 7);
        board[5][0] = Board.RED;
        board[4][0] = Board.RED;
        board[3][0] = Board.RED; // 3 consecutive pieces in column 0
        board[3][1] = Board.RED; // Perpendicular piece in column 1
        int column = 0;
        int piece = Board.RED;
        int length = 3;
        boolean expected = true; // Expected true, since there are 3 in a row and one perpendicular piece in column 1.
        boolean actual = Board.winInColumn(board, column, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInColumnTest2() {
        int[][] board = Board.createBoard(6, 7);
        board[5][2] = Board.RED;
        board[4][2] = Board.RED;
        board[3][2] = Board.RED;
        board[3][1] = Board.RED; // Perpendicular piece in column 1
        int column = 2;
        int piece = Board.RED;
        int length = 3;
        boolean expected = true; // Expected true, since there are 3 in a row and one perpendicular piece in column 1.
        boolean actual = Board.winInColumn(board, column, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInColumnTest3() {
        int[][] board = Board.createBoard(6, 7);
        board[5][3] = Board.RED;
        board[4][3] = Board.RED;
        board[3][3] = Board.RED;
        board[5][2] = Board.RED; // Perpendicular piece in column 2
        int column = 3;
        int piece = Board.RED;
        int length = 3;
        boolean expected = true; // Expected true, since there are 3 in a row and one perpendicular piece in column 2.
        boolean actual = Board.winInColumn(board, column, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInColumnTest4() {
        int[][] board = Board.createBoard(6, 7);
        board[5][1] = Board.RED;
        board[4][1] = Board.RED;
        board[3][1] = Board.RED;
        board[2][1] = Board.RED; // 4 consecutive pieces in column 1
        board[3][0] = Board.RED; // Perpendicular piece in column 0
        int column = 1;
        int piece = Board.RED;
        int length = 4;
        boolean expected = false; // Expected false, as the perpendicular piece is at the middle
        boolean actual = Board.winInColumn(board, column, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInColumnTest5() {
        int[][] board = Board.createBoard(6, 7);
        board[5][5] = Board.RED;
        board[4][5] = Board.RED;
        board[3][5] = Board.RED;
        board[2][5] = Board.RED; // 4 consecutive pieces in column 5
        int column = 5;
        int piece = Board.RED;
        int length = 4;
        boolean expected = false; // Expected false, since there is no perpendicular piece.
        boolean actual = Board.winInColumn(board, column, piece, length);
        assertEquals(expected, actual);
    }
    @Test
    void winInDiagonalBackslashTest1() {
        int[][] board = Board.createBoard(6, 7);
        board[0][0] = Board.RED;
        board[1][1] = Board.RED;
        board[2][2] = Board.RED;
        board[3][3] = Board.RED; // Diagonal backslash sequence
        board[4][2] = Board.RED; // Perpendicular piece at end
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // A diagonal backslash win is expected.
        boolean actual = Board.winInDiagonalBackslash(board, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInDiagonalBackslashTest2() {
        int[][] board = Board.createBoard(6, 7);
        board[0][1] = Board.RED;
        board[1][2] = Board.RED;
        board[2][3] = Board.RED;
        board[3][4] = Board.RED; // Diagonal backslash sequence
        board[1][0] = Board.RED; // Perpendicular piece at beginning
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // A diagonal backslash win is expected.
        boolean actual = Board.winInDiagonalBackslash(board, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInDiagonalBackslashTest3() {
        int[][] board = Board.createBoard(6, 7);
        board[1][2] = Board.RED;
        board[2][3] = Board.RED;
        board[3][4] = Board.RED;
        board[4][5] = Board.RED; // Diagonal backslash sequence
        board[0][3] = Board.RED; // Perpendicular piece.
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // A diagonal backslash win is expected.
        boolean actual = Board.winInDiagonalBackslash(board, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInDiagonalBackslashTest4() {
        int[][] board = Board.createBoard(6, 8);
        board[0][3] = Board.RED;
        board[1][4] = Board.RED;
        board[2][5] = Board.RED;
        board[3][6] = Board.RED; // Diagonal backslash sequence
        board[2][7] = Board.RED; // Perpendicular piece.
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // A diagonal backslash win is expected.
        boolean actual = Board.winInDiagonalBackslash(board, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInDiagonalBackslashTest5() {
        int[][] board = Board.createBoard(6, 7);
        board[0][4] = Board.RED;
        board[1][5] = Board.RED;
        board[2][6] = Board.RED;
        board[3][0] = Board.RED; // Diagonal backslash sequence
        int piece = Board.RED;
        int length = 4;
        boolean expected = false; // A diagonal backslash win is not expected as no perpendicular piece.
        boolean actual = Board.winInDiagonalBackslash(board, piece, length);
        assertEquals(expected, actual);
    }
    @Test
    void winInDiagonalForwardSlashTest1() {
        int[][] board = Board.createBoard(7, 7);
        board[5][0] = Board.RED;
        board[4][1] = Board.RED;
        board[3][2] = Board.RED;
        board[2][3] = Board.RED; // Diagonal forward slash from bottom-left to top-right
        board[6][1] = Board.RED; // Perpendicular piece
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // A forward slash diagonal win is expected.
        boolean actual = Board.winInDiagonalForwardSlash(board, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInDiagonalForwardSlashTest2() {
        int[][] board = Board.createBoard(6, 7);
        board[5][1] = Board.RED;
        board[4][2] = Board.RED;
        board[3][3] = Board.RED;
        board[2][4] = Board.RED; // Diagonal forward slash from bottom-left to top-right
        board[4][0] = Board.RED; // Perpendicular piece
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // A forward slash diagonal win is expected.
        boolean actual = Board.winInDiagonalForwardSlash(board, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInDiagonalForwardSlashTest3() {
        int[][] board = Board.createBoard(6, 7);
        board[5][2] = Board.RED;
        board[4][3] = Board.RED;
        board[3][4] = Board.RED;
        board[2][5] = Board.RED; // Diagonal forward slash from bottom-left to top-right
        board[1][4] = Board.RED; // Perpendicular piece
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // A forward slash diagonal win is expected.
        boolean actual = Board.winInDiagonalForwardSlash(board, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInDiagonalForwardSlashTest4() {
        int[][] board = Board.createBoard(6, 8);
        board[5][3] = Board.RED;
        board[4][4] = Board.RED;
        board[3][5] = Board.RED;
        board[2][6] = Board.RED; // Diagonal forward slash from bottom-left to top-right
        board[3][7] = Board.RED; // Perpendicular piece
        int piece = Board.RED;
        int length = 4;
        boolean expected = true; // A forward slash diagonal win is expected.
        boolean actual = Board.winInDiagonalForwardSlash(board, piece, length);
        assertEquals(expected, actual);
    }

    @Test
    void winInDiagonalForwardSlashTest5() {
        int[][] board = Board.createBoard(6, 8);
        board[5][4] = Board.RED;
        board[4][5] = Board.RED;
        board[3][6] = Board.RED;
        board[2][7] = Board.RED; // Diagonal forward slash from bottom-left to top-right
        board[1][1] = Board.RED; // Perpendicular piece but not at the end or start
        int piece = Board.RED;
        int length = 4;
        boolean expected = false; // A forward slash diagonal win is not expected.
        boolean actual = Board.winInDiagonalForwardSlash(board, piece, length);
        assertEquals(expected, actual);
    }
    @Test
    void hintTest1() {
        int[][] board = Board.createBoard(6, 7);
        // Create a scenario where human is red and a winning move can be played in column 1
        board[5][2] = Board.RED;
        board[4][2] = Board.RED;
        board[3][2] = Board.RED;
        int piece = Board.RED; // Human is checking for a winning move
        int length = 3; // Length to win

        // The winning move would be at row 5, column 1
        int[] expected = {5, 1};
        int[] actual = Board.hint(board, piece, length);
        assertArrayEquals(expected, actual);
    }

    @Test
    void hintTest2() {
        int[][] board = Board.createBoard(6, 7);
        // Create a scenario where human is blue and has a winning move in column 0
        board[5][0] = Board.BLU;
        board[5][1] = Board.BLU;
        board[5][2] = Board.BLU;
        int piece = Board.BLU; // Human is checking for a winning move
        int length = 3; // Length to win

        // The winning move would be at row 4, column 0
        int[] expected = {4, 0};
        int[] actual = Board.hint(board, piece, length);
        assertArrayEquals(expected, actual);
    }

    @Test
    void hintTest3() {
        int[][] board = Board.createBoard(6, 7);
        //Computer is blue and is winning in forwardSlash if the perpendicular piece is placed
        board[5][0] = Board.RED;
        board[4][1] = Board.RED;
        board[3][2] = Board.RED;
        board[5][3] = Board.BLU;
        int piece = Board.BLU; // Human is checking to stop opponent from winning
        int length = 3; // Length to win

        // Spot to place piece on to stop opponent
        int[] expected = {4, 3};
        int[] actual = Board.hint(board, piece, length);
        assertArrayEquals(expected, actual);
    }

    @Test
    void hintTest4() {
        int[][] board = Board.createBoard(4, 4);
        // Create a scenario where human is red and can win in backward slash by placing a piece in column 0 and row 2
        board[3][3] = Board.RED;
        board[1][1] = Board.RED;
        board[2][2] = Board.RED;
        board[3][0] = Board.BLU;
        int piece = Board.RED; // Human is checking for a winning move
        int length = 3; // Length to win

        // The winning move would be at row 2, column 0
        int[] expected = {2, 0};
        int[] actual = Board.hint(board, piece, length);
        assertArrayEquals(expected, actual);
    }

    @Test
    void hintTest5() {
        int[][] board = Board.createBoard(4, 4);
        // Create a scenario where human is red and no winning move exists, and the board is full.
        board[0][0] = Board.RED;
        board[0][1] = Board.BLU;
        board[0][2] = Board.RED;
        board[0][3] = Board.BLU;
        board[1][0] = Board.RED;
        board[1][1] = Board.BLU;
        board[1][2] = Board.RED;
        board[1][3] = Board.BLU;
        board[2][0] = Board.RED;
        board[2][1] = Board.BLU;
        board[2][2] = Board.RED;
        board[2][3] = Board.BLU;
        board[3][0] = Board.RED;
        board[3][1] = Board.BLU;
        board[3][2] = Board.RED;
        board[3][3] = Board.BLU;
        int piece = Board.RED; // Human is checking for a winning move
        int length = 3; // Length to win

        // Since the board is full and no winning move exists, it should return [-1, -1]
        int[] expected = {-1, -1};
        int[] actual = Board.hint(board, piece, length);
        assertArrayEquals(expected, actual);
    }







}

