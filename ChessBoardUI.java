import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardUI
{
    private static final int TILE_SIZE = 80;
    private static final String IMAGE_PATH = "resources/";

    private GridPane grid;
    private ChessBoard chessBoard;
    private Piece[][] board;

    private Piece selectedPiece = null;  // To track the selected piece
    private int selectedRow = -1;  // Row of the selected piece
    private int selectedCol = -1;  // Column of the selected piece

    // Add the selected position tracking variables
    private int selectedPieceX = -1;  // Row of the selected piece
    private int selectedPieceY = -1;  // Column of the selected piece

    // To store the legal move highlights
    private List<Rectangle> legalMoveHighlights = new ArrayList<>();

    // Scene field
    private Scene scene;

    // Constructor to set up the UI elements
    public ChessBoardUI()
    {
        grid = new GridPane();
        chessBoard = new ChessBoard();
        board = chessBoard.getBoard();
        setupBoard();

        // Create the scene and set it
        scene = new Scene(grid, 8 * TILE_SIZE, 8 * TILE_SIZE);
    }

    // Method to set up the board with pieces and tiles
    public void setupBoard()
    {
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                // Create the square
                Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE);
                square.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.DARKGRAY);

                // Add click event to the square
                final int currentRow = row;
                final int currentCol = col;
                square.setOnMouseClicked(event -> handleSquareClick(currentRow, currentCol));

                // Add the square to the grid first, so it's at the bottom layer
                grid.add(square, col, row);

                // Check if there's a piece on the square and add the image if so
                Piece piece = board[row][col];
                if (piece != null)
                {
                    try
                    {
                        Image image = new Image(new FileInputStream(IMAGE_PATH + piece.getImageFileName()));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(TILE_SIZE);
                        imageView.setFitHeight(TILE_SIZE);
                        // Make the image view "transparent" to mouse events
                        imageView.setMouseTransparent(true); // Prevent the image from blocking the click event
                        // Add the image view on top of the rectangle
                        grid.add(imageView, col, row);
                    }
                    catch (FileNotFoundException e)
                    {
                        System.out.println("Image not found: " + piece.getImageFileName());
                    }
                }
            }
        }
    }
    
    public void promotePawn(int x, int y)
    {
        Piece piece = this.chessBoard.getBoard()[x][y];

        if (piece instanceof Pawn)
        {
            boolean isWhite = piece.isWhite();
            this.chessBoard.getBoard()[x][y] = new Queen(isWhite); // Replace pawn with queen
            
            System.out.println("Pawn promoted to Queen!");
            refreshBoardUI();
        }
    }

    
    private void handleSquareClick(int row, int col)
    {
        System.out.println("Square clicked: Row = " + row + ", Column = " + col);
        resetTileColors();
    
        Piece piece = board[row][col];
        System.out.println("Piece at clicked square: " + (piece != null ? piece.toString() : "None"));
    
        if (piece != null)
        {
            if (selectedPiece != null)
            {
                if (selectedPiece == piece)
                {
                    System.out.println("Deselected the piece: " + selectedPiece.toString());
                    selectedPiece = null;
                    resetTileColors();
                }
                else if (selectedPiece.isWhite() == piece.isWhite())
                {
                    System.out.println("Switched to a new piece: " + piece.toString());
                    selectedPiece = piece;
                    selectedPieceX = row;
                    selectedPieceY = col;
                    clearLegalMoveHighlights();
                    highlightLegalMoves(piece, row, col);
                }
                else
                {
                    System.out.println("Captured opponent's piece: " + piece.toString());
                    if (selectedPiece.move(selectedPieceX, selectedPieceY, row, col, chessBoard))
                    {
                        board[row][col] = selectedPiece;
                        board[selectedPieceX][selectedPieceY] = null;
    
                        // Check if a pawn should be promoted
                        if (selectedPiece instanceof Pawn && ((row == 0 && !selectedPiece.isWhite()) || (row == 7 && selectedPiece.isWhite())))
                        {
                            promotePawn(row, col);
                        }
    
                        refreshBoardUI();
                        SoundHandler.playCaptureSound();
                        selectedPiece = null;
                        resetTileColors();
                    }
                    else
                    {
                        System.out.println("Invalid move.");
                        SoundHandler.playErrorSound();
                        selectedPiece = null;
                        resetTileColors();
                        clearLegalMoveHighlights();
                    }
                }
            }
            else
            {
                selectedPiece = piece;
                selectedPieceX = row;
                selectedPieceY = col;
                highlightSelectedTile(row, col);
                highlightLegalMoves(piece, row, col);
            }
        }
        else
        {
            if (selectedPiece != null)
            {
                System.out.println("Moving piece to square...");
                Piece targetPiece = board[row][col];
    
                if (targetPiece == null || (targetPiece != null && targetPiece.isWhite() != selectedPiece.isWhite()))
                {
                    if (selectedPiece.move(selectedPieceX, selectedPieceY, row, col, chessBoard))
                    {
                        board[row][col] = selectedPiece;
                        board[selectedPieceX][selectedPieceY] = null;
    
                        if (targetPiece != null)
                        {
                            System.out.println("Captured opponent's piece: " + targetPiece.toString());
                        }
    
                        // **Check if the moved piece is a pawn and has reached promotion rank**
                        if (selectedPiece instanceof Pawn && ((row == 0 && !selectedPiece.isWhite()) || (row == 7 && selectedPiece.isWhite())))
                        {
                            promotePawn(row, col);
                        }
    
                        refreshBoardUI();
                        SoundHandler.playMoveSound();
                        selectedPiece = null;
                        resetTileColors();
                        clearLegalMoveHighlights();
                    }
                    else
                    {
                        System.out.println("Invalid move.");
                        SoundHandler.playErrorSound();
                        selectedPiece = null;
                        resetTileColors();
                        clearLegalMoveHighlights();
                    }
                }
                else
                {
                    System.out.println("Invalid move, target square occupied by friendly piece.");
                    SoundHandler.playErrorSound();
                    selectedPiece = null;
                    resetTileColors();
                    clearLegalMoveHighlights();
                }
            }
            else
            {
                System.out.println("No piece selected and square is empty.");
            }
        }
    }




    // Method to highlight the legal moves of the selected piece
    private void highlightLegalMoves(Piece piece, int row, int col)
    {
        legalMoveHighlights.clear(); // Clear old highlights
        List<int[]> validMoves = piece.getValidMoves(row, col, chessBoard);

        for (int[] move : validMoves)
        {
            int targetRow = move[0];
            int targetCol = move[1];

            for (javafx.scene.Node node : grid.getChildren())
            {
                if (GridPane.getRowIndex(node) == targetRow && GridPane.getColumnIndex(node) == targetCol && node instanceof Rectangle)
                {
                    Rectangle square = (Rectangle) node;
                    square.setFill(Color.LIGHTGREEN);  // Highlight the legal move tile with light green
                    legalMoveHighlights.add(square); // Store the highlighted square
                }
            }
        }
    }

    // Method to clear only the legal move highlights
    private void clearLegalMoveHighlights()
    {
        // Only clear the highlights of the legal moves
        for (Rectangle highlight : legalMoveHighlights)
        {
            highlight.setFill((GridPane.getRowIndex(highlight) + GridPane.getColumnIndex(highlight)) % 2 == 0 ? Color.BEIGE : Color.DARKGRAY);
        }
        legalMoveHighlights.clear();  // Clear the stored highlights
    }

    // Method to reset the colors of all tiles to their original state
    private void resetTileColors()
    {
        // Reset the colors of all tiles to their original state
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                for (javafx.scene.Node node : grid.getChildren())
                {
                    if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Rectangle)
                    {
                        Rectangle square = (Rectangle) node;
                        // Reset the color to the original alternating tile colors
                        square.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.DARKGRAY);
                    }
                }
            }
        }
    }

    // Method to refresh the board UI after a move
    private void refreshBoardUI()
    {
        // Remove all children (old pieces and squares)
        grid.getChildren().clear();

        // Re-render the board after the move
        setupBoard();
    }

    // Method to highlight the selected tile
    private void highlightSelectedTile(int row, int col)
    {
        for (javafx.scene.Node node : grid.getChildren())
        {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Rectangle)
            {
                Rectangle square = (Rectangle) node;
                square.setFill(Color.LIGHTGREEN);  // Highlight the selected tile with yellow
            }
        }
    }

    // Method to get the scene
    public Scene getScene()
    {
        return scene;
    }
}
