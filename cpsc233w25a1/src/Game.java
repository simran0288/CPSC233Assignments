import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.math.BigInteger;
import java.util.List;
import java.util.*;

/**
 * CPSC 233 W25 Assignment 1 Provided Game code
 *
 * A Class for a Modified Connect-4 Style Game called Connect-L
 *
 * (for GUI purposes this class extends Canvas for drawing loop)
 *
 * Common boards for real game are 7x6, 5×4, 6×5, 8×7, 9×7, 10×7, 8×8
 *
 * Allows user to play a game on boards with lengths of 4,5,6,7,8 for width/height.
 *
 * A win will not just be 4 in a row but instead an L shape X+1 pieces where user picks X >= 3 and < the largest dimension of board.
 * Examples X==3 OOO      O       OO     O
 *                 O      OOO      O      O O
 *                                 O       O
 * There is a simple implemented AI (That could have bugs due to lack of testing?!).
 * The AI relies on Board being implemented correctly.
 * To complete this game a student will complete Board.java for the game to operate correctly,
 * as well as BoardTest.java to test the functions required.
 *
 * @author Jonathan Hudson
 * @email jwhudson@ucalgary.ca
 * @version 1.0
 */
public class Game extends Canvas {

    //GAME PIECE CONSTANTS (public for visibility for JUnit Test)
    /**
     * No piece in board (empty)
     */
    public static final int EMP = 0;
    /**
     * Connect-L red piece
     */
    public static final int RED = 1;
    /**
     * Connect-L blue piece
     */
    public static final int BLU = 2;




    /*----------------------------------------------------------------------------------------------------------
     * INSTRUCTOR CODE (YOU SHOULD NOT NEED TO BE BELOW THIS LINE FOR REGULAR ASSIGNMENT)
     * ---------------------------------------------------------------------------------------------------------- */

    //GAME BOARD SIZE (pixels)
    private static final int BOARD_SIZE = 600;
    //These are temporary as we will adjust them if the board is rectangular
    private static int window_width = BOARD_SIZE;
    private static int window_height = BOARD_SIZE;
    //DRAWING CONSTANTS
    //stroke of lines
    private static final int STROKE_SIZE = 10;
    private static final Stroke STROKE = new BasicStroke(STROKE_SIZE);
    //size of font
    private static final int FONT_SIZE = 50;
    private static final Font FONT = new Font("Times", Font.BOLD, FONT_SIZE);
    //border sizes
    private static final int X_O_PIXELS_BORDER = 15;
    private static final int TEXT_BORDER = 1;
    //colours
    private static final Color BGD_COLOUR = Color.white;
    private static final Color BOARD_COLOUR = Color.yellow;
    private static final Color RED_PIECE_COLOUR = Color.red;
    private static final Color BLUE_PIECE_COLOUR = Color.blue;
    private static final Color HINT_COLOUR = Color.black;
    private static final Color WIN_COLOUR = Color.yellow;
    private static final Color LOSE_COLOUR = Color.yellow;
    private static final Color TIE_COLOUR = Color.yellow;
    //GAME DIFFICULTY PROMPT CONSTANTS
    private static final String DIFF_PROMPT_DEF = """
            Difficulties:
            \t0\tAI plays randomly
            \t1\tAI looks at its own and your next play
            \t2\tAI looks two moves ahead for each player
            \t3\tAI looks three moves ahead for each player""";
    private static final String DIFF_PROMPT_5X4 = DIFF_PROMPT_DEF + "\n\t4\tAI looks ahead to end of game\n" +
            "\t\t(Note a difficulty of 4 uses an AI algorithm that may slow down some computers and you will have to wait.";
    private static final int MIN_AI = 0;
    private static final int MAX_AI = 3;
    private static final int MAX_AI_5X4 = 4;
    // GAME CONSTANTS
    private static final int MIN_BOARD_SIZE = 4;
    private static final int MAX_BOARD_SIZE = 8;
    //BOARD GAME VARIABLES
    //Getting input from user (keyboard)
    private static final Scanner scanner = new Scanner(System.in);
    //Getting input from user (GUI)
    private static final Integer[] mouse = new Integer[]{0, 0};
    //DRAWING GAME STATE VARIABLES
    private static Canvas canvas;
    private static boolean draw_game_over = false;
    private static int draw_end_game_type = 0;
    private static int draw_winner_piece = EMP;
    private static boolean draw_hint = false;
    private static int draw_hint_piece = EMP;
    private static int[] draw_hint_location = null;
    //The board of the current game
    private static int[][] board;

    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE RUNS THE CORE GAME LOOP
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * Start game
     *
     * @param args No arguments expected for this game
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            System.err.println("Program accepts no arguments!");
            System.exit(1);
        }
        try {
            // Get size of board from user and create the 2D array that is the board
            board = Board.createBoard(inputRows(), inputColumns());
            //Setup GUI window
            setupWindow();
            //Run game loop
            run();
        } catch (Exception e) {
            System.out.println("Exception occurred running program!");
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Run the game loop
     */
    private static void run() {
        int length = inputWinLength(board);
        //GET GAME SETUP
        // Get difficulty from the user (only 4x4,5x4,4x5 board allows full AI == 4)
        int difficulty_input = inputDifficulty(board);
        // Get user choice of piece (RED goes first so picking RED means user goes first, picking BLUE means computer goes first
        int[] pieces = inputPlayerPiece();
        int human = pieces[0];
        int computer = pieces[1];
        // Ask what type of hints to give player
        String h = inputHintMode();
        // Now we can start the game?
        //Check if we want mouse input
        String gui_string;
        do {
            System.out.println("Enter G to enter input with mouse, otherwise use shell to play: ");
            gui_string = scanner.nextLine().trim();
        } while (!gui_string.equals("G") && !gui_string.isEmpty());
        boolean gui_flag = gui_string.equals("G");

        //PLAY GAME
        System.out.println("Play a game!");
        int player = RED;
        int plays = 0;
        //While game continues
        while (!Board.isGameOver(board, length)) {
            //If human then we have to collect input
            if (human == player) {
                System.out.println("Human player's turn.");
                // Get and draw hint
                inputHint(board, human, computer, h, length);
                //We ask for repaint as hint will have changed drawing variables
                canvas.repaint();
                // Depending on flag get input for user playing via GUI or via input() prompts in shell
                if (gui_flag) {
                    inputMouseNextPlay(board, human);
                } else {
                    inputScannerNextPlay(board, human);
                }
                //Turn off hint and redraw with human move made
                draw_hint = false;
                canvas.repaint();
                // Switch to other player
                player = computer;
            } else {
                //If AI is on, then get the play and complete it
                int[] move = AI(board, computer, human, difficulty_input, length);
                int col = move[1];
                Board.play(board, move[1], computer);
                System.out.printf("AI plays at %d%n", col);
                //Switch to other player
                player = human;
            }
            //Track each play for complexity update purposes
            plays += 1;
        }

        //HANDLE GAME IS COMPLETE
        //This last chunk determines drawing game state of end-game
        if (Board.won(board, RED, length)) {
            draw_winner_piece = RED;
            if (human == RED) {
                draw_end_game_type = 1;
            } else {
                draw_end_game_type = -1;
            }
        } else if (Board.won(board, BLU, length)) {
            draw_winner_piece = BLU;
            if (human == BLU) {
                draw_end_game_type = 1;
            } else {
                draw_end_game_type = -1;
            }
        } else {
            draw_end_game_type = 0;
        }
        draw_game_over = true;
        //Repaint with end-game drawing setup to get colour/message of end game
        canvas.repaint();
        //Game is done
        System.out.println("Game is over!");
    }

    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE IS USED TO GET USER INPUT FROM THE TERMINAL
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * Is this string a valid user input between start, and end integer inclusive
     *
     * @param value The value to check
     * @param start The start integer
     * @param end   The end integer inclusive
     * @return True if the value is in that range (inclusive start, end step size 1)
     */
    private static boolean isInputInvalid(String value, int start, int end) {
        // Lazy way to check if the given string is in an integer range without attempting to parse
        // This is a bad way to do this (only done to leave exception for students until later)
        // Very slow for large ranges (but unnoticeable for this program usage)
        for (int i = start; i <= end; i++) {
            if (value.equals(i + "")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Re-prompt user until a valid row count is given
     *
     * @return A valid int for row number
     */
    private static int inputRows() {
        String input_rows;
        do {
            System.out.printf("Pick a board row count in [%d,%d]: ", MIN_BOARD_SIZE, MAX_BOARD_SIZE);
            input_rows = scanner.nextLine().trim();
        } while (isInputInvalid(input_rows, MIN_BOARD_SIZE, MAX_BOARD_SIZE));
        return Integer.parseInt(input_rows);
    }

    /**
     * Re-prompt user until a valid column count is given
     *
     * @return A valid int for column number
     */
    private static int inputColumns() {
        String input_columns;
        do {
            System.out.printf("Pick a board column count in [%d,%d]: ", MIN_BOARD_SIZE, MAX_BOARD_SIZE);
            input_columns = scanner.nextLine().trim();
        } while (isInputInvalid(input_columns, MIN_BOARD_SIZE, MAX_BOARD_SIZE));
        return Integer.parseInt(input_columns);
    }

    /**
     * Re-prompt user until a valid win length is given
     *
     * @param board The board to determine the maximum win length possible from
     * @return A valid int for the win length
     */
    private static int inputWinLength(int[][] board) {
        String input_length;
        int max = Math.max(board.length, board[0].length);
        do {
            System.out.printf("Pick a length of long side of L-shape in [%d,%d]: ", MIN_BOARD_SIZE-1, max-1);
            input_length = scanner.nextLine().trim();
        } while (isInputInvalid(input_length, MIN_BOARD_SIZE-1, max-1));
        return Integer.parseInt(input_length);
    }

    /**
     * Get difficulty of AI for game from user
     *
     * @param board 2D array that is the game board
     * @return Integer difficulty from user
     */
    private static int inputDifficulty(int[][] board) {
        String difficulty_string;
        //We limit difficulty for boards that are not 4x4,4x5,5x4 (different message and upper limit)
        if (board.length == MIN_BOARD_SIZE && board[0].length == MIN_BOARD_SIZE) {
            do {
                System.out.println(DIFF_PROMPT_5X4);
                System.out.print("Select a difficulty: ");
                difficulty_string = scanner.nextLine().trim();
            } while (isInputInvalid(difficulty_string, MIN_AI, MAX_AI_5X4));
        }
        //Larger boards don't get best AI option
        else {
            do {
                System.out.println(DIFF_PROMPT_DEF);
                System.out.print("Select a difficulty: ");
                difficulty_string = scanner.nextLine().trim();
            } while (isInputInvalid(difficulty_string, MIN_AI, MAX_AI));
        }
        return Integer.parseInt(difficulty_string);
    }

    /**
     * Get what piece the human and computer are, one is (R)ed, other is (B)lue
     *
     * @return Either (Red, Blue) or (Blue, Red) for (human, computer) based on user choice
     */
    private static int[] inputPlayerPiece() {
        while (true) {
            System.out.print("Enter choice of (R)ed or (B)lue: ");
            String piece_string = scanner.nextLine().trim();
            if (piece_string.equalsIgnoreCase("R")) {
                System.out.println("Human is Red.");
                System.out.println("Computer is Blue.");
                return new int[]{RED, BLU};
            } else if (piece_string.equalsIgnoreCase("B")) {
                System.out.println("Human is Blue.");
                System.out.println("Computer is Red.");
                return new int[]{BLU, RED};
            }
            System.out.printf("Previous entry \"%s\" was invalid!%n", piece_string);
        }
    }

    /**
     * Get what type of hint to give user
     *
     * @return "h" for winning hint, "" for no hint, "a" for hidden advanced AI hint
     */
    private static String inputHintMode() {
        String message = "Enter 'h' for game winning hints or <Enter> for no hints: ";
        String hint_mode_string;
        do {
            System.out.print(message);
            hint_mode_string = scanner.nextLine().trim();
        } while (!hint_mode_string.equals("h") && !hint_mode_string.equals("a") && !hint_mode_string.isEmpty());
        return hint_mode_string;
    }

    /**
     * Plays the game via Scanner from user
     *
     * @param board The board of game
     * @param human The human's piece
     */
    private static void inputScannerNextPlay(int[][] board, int human) {
        while (true) {
            //Get a column input that fit in board
            String input_column_string = null;
            while (input_column_string == null || isInputInvalid(input_column_string, 0, board[0].length - 1)) {
                System.out.printf("Enter column: %n");
                input_column_string = scanner.nextLine().trim();
            }
            int col = Integer.parseInt(input_column_string);
            System.out.printf("User entered %d%n", col);
            //Check if play is validly open before accepting
            if (Board.canPlay(board, col)) {
                Board.play(board, col, human);
                return;
            } else {
                System.err.printf("Chosen location %d is full!%n", col);
            }
        }
    }

    /**
     * Based on hinting mode we will get and show hint on board for the game
     *
     * @param board    The 2D array board in which hint should be found
     * @param human    The human piece
     * @param computer The computer piece
     * @param h        The hint mode 'h','a' or ''
     */
    private static void inputHint(int[][] board, int human, int computer, String h, int length) {
        //Basic look-head to block opponent win or win
        if (h.equals("h")) {
            System.out.println("Wait for hint");
            //Get human win, CPU win hints if they exist
            int[] immediate_win_hint = Board.hint(board, human, length);
            int[] opponent_win_block = Board.hint(board, computer, length);
            //Tell player to win if that is there
            if (immediate_win_hint[0] != -1) {
                System.out.printf("Hint to win in (%d,%d)%n", immediate_win_hint[0], immediate_win_hint[1]);
                draw_hint = true;
                draw_hint_location = immediate_win_hint;
                draw_hint_piece = human;
            }
            //Tell player to block CPU if that exists second
            else if (opponent_win_block[0] != -1) {
                System.out.printf("Hint to stop opponent win in (%d,%d)%n", opponent_win_block[0], opponent_win_block[1]);
                draw_hint = true;
                draw_hint_location = opponent_win_block;
                draw_hint_piece = human;
            }
            //Otherwise, no hint
            else {
                System.out.println("No regular hint");
            }
        }
        //AI hint, which will be limited by board size
        else if (h.equals("a")) {
            //Get a hint for human that is a win
            int[] immediate_win_hint = Board.hint(board, human, length);
            int[] hint;
            //Use immediate win hint if it exists
            if (immediate_win_hint[0] != -1) {
                System.out.println("Wait for hint (quick)");
                hint = immediate_win_hint;
            }
            //Otherwise, if 3x3 board do an AI search for whole board
            else if (board.length == MIN_BOARD_SIZE && board[0].length == MIN_BOARD_SIZE) {
                System.out.println("Wait for hint (really slow!)");
                hint = AI(board, human, computer, 4, length);
            }
            //If not 3x3 then only do AI search for 3 plays ahead
            else {
                System.out.println("Wait for hint (slow)");
                hint = AI(board, human, computer, 3, length);
            }
            //If hint found then show this hint
            if (hint[0] != -1) {
                System.out.printf("Hint is column %d%n", hint[1]);
                draw_hint = true;
                draw_hint_location = hint;
                draw_hint_piece = human;
            } else {
                System.out.println("No advanced hint");
            }
        }
    }

    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE IS USED TO GET USER INPUT FROM THE GUI
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * Plays the game via GUI mouse click from user
     *
     * @param board The board of game
     * @param human The human's piece
     */
    private static void inputMouseNextPlay(int[][] board, int human) {
        //We'll need to use these variables to determine which square was clicked on
        int square_height = window_height / board.length;
        int square_width = window_width / board[0].length;
        //Loop until a click on the board
        while (true) {
            //We wait on a mouse event on board
            try {
                synchronized (mouse) {
                    mouse.wait();
                }
            } catch (InterruptedException e) {
                //We are going to ignore this
            }
            // From the stored mouse value determine a row and column
            int row = (mouse[1] / square_height);
            int col = (mouse[0] / square_width);
            //If math says the row,col calculation isn't in board then loop for another click
            if (row < 0 || row > board.length - 1) {
                continue;
            }
            if (col < 0 || col > board[0].length - 1) {
                continue;
            }
            //If we can play in this location we make this play and return to game
            if (Board.canPlay(board, col)) {
                Board.play(board, col, human);
                System.out.printf("User entered %d%n", col);
                return;
            }
        }
    }

    /**
     * This is a hidden internal class to enable a Mouse listener
     * We only need the mouse clicked event in this program but are required to implement all the others
     */
    private static class MyMouse extends JComponent implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //When a mouse is clicked we stored that value and notify anyone that might be waiting for it
            mouse[0] = e.getX();
            mouse[1] = e.getY();
            synchronized (mouse) {
                mouse.notifyAll();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE IS USED TO DRAW THE GAME
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * This allows us to re-paint the GUI window regularly (we have to update game state variables to draw different things
     *
     * @param graphics The graphics given when painting is requested
     */
    @Override
    public void paint(Graphics graphics) {
        drawBoard((Graphics2D) graphics);
    }

    /**
     * Set up the Java Swing window (really simple window a canvas and a mouse listener)
     */
    private static void setupWindow() {
        JFrame frame = new JFrame("Connect-L (Modified Connect-4 Style Game)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        canvas = new Game();
        if(board != null){
            //If we have rectangle with more rows
            if(board.length > board[0].length){
                window_height = (window_width /board[0].length) * board.length;
            }
            if(board.length < board[0].length){
                window_width = (window_height /board.length) * board[0].length;
            }
        }
        canvas.setSize(window_width, window_height);
        canvas.setBackground(BGD_COLOUR);
        frame.add(canvas);
        Component mouseClick = new MyMouse();
        canvas.addMouseListener((MouseListener) mouseClick);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This handles the bulk of load drawing the GUI game
     *
     * @param graphics The graphics we are drawing to
     */
    private static void drawBoard(Graphics2D graphics) {
        // Clear board to white before redraw
        graphics.clearRect(0, 0, window_width, window_height);
        // Get size of a box
        int row_pixel_size = (window_height / board.length);
        int col_pixel_size = (window_width / board[0].length);
        // Now draw board in given colour (have to determine if game is over or not)
        //These were all setup elsewhere
        Color draw_board_colour = BOARD_COLOUR;
        if (draw_game_over) {
            if (draw_end_game_type == 1) {
                draw_board_colour = WIN_COLOUR;
            } else if (draw_end_game_type == 0) {
                draw_board_colour = TIE_COLOUR;
            } else {
                draw_board_colour = LOSE_COLOUR;
            }
        }
        graphics.setColor(draw_board_colour);
        //Draw the squares
        graphics.setStroke(STROKE);
        graphics.fillRect(0, 0, window_width, window_height);
        // Draw any pieces played in board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == RED) {
                    drawPiece(graphics, col * col_pixel_size, row * row_pixel_size, col_pixel_size, row_pixel_size, RED_PIECE_COLOUR);
                } else if (board[row][col] == BLU) {
                    drawPiece(graphics, col * col_pixel_size, row * row_pixel_size, col_pixel_size, row_pixel_size, BLUE_PIECE_COLOUR);
                } else{
                    drawPiece(graphics, col * col_pixel_size, row * row_pixel_size, col_pixel_size, row_pixel_size, BGD_COLOUR);
                }
            }
        }
        //If there is a hint triggered then draw it
        if (draw_hint) {
            drawHint(graphics);
        }
        //Draw the text for game over (most of this code is trying to center it)
        graphics.setFont(FONT);
        if (draw_game_over) {
            String message = "Board full. Tie Game.";
            if (draw_end_game_type != 0) {
                String piece = draw_winner_piece == RED ? "RED" : "BLUE";
                message = piece + " won!";
            }
            //Use font metrics to try and center the text over a white box for readability (text and fonts are messy, sigh)
            Rectangle2D rect = graphics.getFontMetrics().getStringBounds(message, graphics);
            int stringLen = (int) rect.getWidth();
            int font_ascent = graphics.getFontMetrics().getMaxAscent();
            int font_descent = graphics.getFontMetrics().getMaxDescent();
            int start_x = window_width / 2 - stringLen / 2;
            int start_y_rect = window_height / 2 - font_ascent / 2;
            int start_y_text = window_height / 2 + font_ascent / 2;
            graphics.setStroke(new BasicStroke(0));
            graphics.setColor(BGD_COLOUR);
            graphics.fillRect(start_x - TEXT_BORDER, start_y_rect - TEXT_BORDER, stringLen + TEXT_BORDER * 2, font_ascent + font_descent + TEXT_BORDER * 2);
            graphics.setColor(Color.BLACK);
            graphics.drawString(message, start_x, start_y_text);
        }
    }

    /**
     * Draw Piece in box beginning at (x,y) with given square size and color
     * Uses X_O_PIXELS_BORDER to create border to O
     *
     * @param graphics Where we are drawing to
     * @param x        The x pixel location of top left of box to draw O in
     * @param y        The y pixel location of top left of box to draw O in
     * @param size_x   The pixel width of the box to draw O in
     * @param size_y   The pixel height of the box ot draw O in
     * @param colour   The color to draw the lines of the O in
     */
    private static void drawPiece(Graphics2D graphics, int x, int y, int size_x, int size_y, Color colour) {
        graphics.setColor(colour);
        graphics.setStroke(STROKE);
        graphics.fillOval(x + X_O_PIXELS_BORDER, y + X_O_PIXELS_BORDER, size_x - X_O_PIXELS_BORDER * 2, size_y - X_O_PIXELS_BORDER * 2);
    }

    /**
     * Draw hint information and X or O based on piece in given row, col of board
     * Quite similar to pieces from drawBoard/drawX/drawO as we are drawing a sub-part of board in different colour for hint
     *
     * @param graphics Where we are drawing to
     */
    private static void drawHint(Graphics2D graphics) {
        int row = draw_hint_location[0];
        int col = draw_hint_location[1];
        graphics.setColor(HINT_COLOUR);
        graphics.setStroke(STROKE);
        // Get size of a box
        int row_pixel_size = (window_height / board.length);
        int col_pixel_size = (window_width / board[0].length);
        graphics.drawRect(col * col_pixel_size, row * row_pixel_size, col_pixel_size + 1, row_pixel_size + 1);
        drawPiece(graphics, col * col_pixel_size, row * row_pixel_size, col_pixel_size, row_pixel_size, HINT_COLOUR);
    }


    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE IS USED FOR THE AI
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * Calling AI, if level 4 we do full recursive minimax, if not we recurse only to certain depth
     * If level=0 AI we just pick random open spot
     *
     * @param original_board The 2D array board in which game is being played
     * @param player1        The piece of player1, X/O
     * @param player2        The piece of player2, the other of X/O
     * @param level          The difficultly level of AI
     * @return A (row, col) spot to play at
     */
    private static int[] AI(int[][] original_board, int player1, int player2, int level, int length) {
        if (player1 != RED && player1 != BLU) {
            throw new RuntimeException("AI player1 should be X/O not {player1}");
        }
        if (player2 != RED && player2 != BLU) {
            throw new RuntimeException("AI player2 should be X/O not {player2}");
        }
        if (player1 == RED && player2 != BLU) {
            throw new RuntimeException("AI player1/player2 can't be X/X");
        }
        if (player1 == BLU && player2 != RED) {
            throw new RuntimeException("AI player1/player2 can't be O/O");
        }
        if (level < 0 || level > 4) {
            throw new RuntimeException("AI level has to be 0 <= level <= 4");
        }
        //Create copy of board for safety of AI not modifying input board
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        board = copy;
        //Do unlimited lookahead if level >= 4
        if (level == 4) {
            Integer[] result = minimax(board, player1, player2, player1, 1, Integer.MAX_VALUE, length);
            return new int[]{result[0], result[1]};
        }
        //Otherwise, multiply level by two(to get plays by each side for each level)
        else if (level > 0) {
            Integer[] result = minimax(board, player1, player2, player1, 1, level * 2 + 1, length);
            return new int[]{result[0], result[1]};
        } else {
            //Otherwise random
            java.util.List<Integer> moves = openMoves(board);
            Collections.shuffle(moves);
            for (Integer col : moves) {
                if (Board.canPlay(board, col)) {
                    int row = Board.play(board, col, EMP);
                    return new int[]{row,col};
                }
            }
        }
        //There is no move to be made
        return new int[]{-1,-1};
    }

    /**
     * Get all open moves in the board (i.e. BLANK spots)
     *
     * @param board The 2D array board to get open moves from (playable spots)
     * @return A list of col move locations that are open to be played in
     */
    private static java.util.List<Integer> openMoves(int[][] board) {
        java.util.List<Integer> moves = new ArrayList<>();
            for (int col = 0; col < board[0].length; col++) {
                if (Board.canPlay(board, col)) {
                    moves.add(col);
                }
            }
        return moves;
    }

    /**
     * Minimax suggest of what row, col to play in for player1 as initial call, and player as current tree call
     *
     * @param board     2D array which is board game is being played in
     * @param player1   Player 1 piece, X/O
     * @param player2   Player 2 piece, the other of X/O
     * @param player    The player current playing
     * @param depth     The depth of the minimax
     * @param max_depth The max depth of the minimax
     * @return The score of this path
     */
    private static Integer[] minimax(int[][] board, int player1, int player2, int player, int depth, int max_depth, int length) {
        Integer[] best;
        //We will be either maximizing value if player1 called AI
        if (player == player1) {
            best = new Integer[]{null, null, Integer.MIN_VALUE, 1};
        }
        //Or minimizing if player2 did
        else {
            best = new Integer[]{null, null, Integer.MAX_VALUE, 1};
        }
        //If we run out of depth or game ends then get board state
        if (depth == max_depth || Board.isGameOver(board, length)) {
            int score = evaluate(board, depth, player1, player2, length);
            return new Integer[]{null, null, score, 1};
        }
        //Get all open moves
        List<Integer> moves = openMoves(board);
        for (Integer col : moves) {
            //Make play
            int row = Board.play(board, col, player);
            //Set next player to be other guy
            int next_player;
            if (player == player1) {
                next_player = player2;
            } else {
                next_player = player1;
            }
            //Get score by exploring down tree
            Integer[] score = minimax(board, player1, player2, next_player, depth + 1, max_depth, length);
            score[0] = row;
            score[1] = col;
            //Undo the play
            Board.removeLastPlay(board, col);
            //Depending on if we are currently on player1 or player 2 we update the best upwards, or downwards
            if (player == player1) {
                if (score[2] > best[2]) {
                    best = score;
                }
            } else {
                if (score[2] < best[2]) {
                    best = score;
                }
            }
        }
        return best;
    }

    /**
     * Evaluate the board, 100 for player 1 win, -100 for player 1 loses, 0 for neutral
     * We adjust this starting score about how far away this win is, so that the result is a struggle
     *
     * @param board   The 2D array board to evaluate
     * @param player1 The piece of player 1, X/O
     * @param player2 The piece of player 2, the other of X/O
     * @return The value 0 -> tied, 1 -> player 1 win, -1 -> player 1 loses
     */
    private static int evaluate(int[][] board, int depth, int player1, int player2, int length) {
        int score = 0;
        if (Board.won(board, player1, length)) {
            score = 100 - depth;
        } else if (Board.won(board, player2, length)) {
            score = depth - 100;
        }
        return score;
    }


}
