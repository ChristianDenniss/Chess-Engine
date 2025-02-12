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
    private void setupBoard()
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

    private void handleSquareClick(int row, int col)
    {
        // Debugging: Log the clicked square
        System.out.println("Square clicked: Row = " + row + ", Column = " + col);
    
        // Reset the colors of all tiles first
        resetTileColors();
    
        // Get the piece at the clicked position
        Piece piece = board[row][col];
        System.out.println("Piece at clicked square: " + (piece != null ? piece.toString() : "None"));
    
        // If there's a piece on the square, handle it
        if (piece != null)
        {
            // If a piece is already selected
            if (selectedPiece != null)
            {
                // If you clicked the same piece, deselect it
                if (selectedPiece == piece)
                {
                    System.out.println("Deselected the piece: " + selectedPiece.toString());
                    selectedPiece = null;
                    resetTileColors();  // Reset tile colors after deselecting
                }
                // If the clicked piece is of the same color, switch to the new piece
                else if (selectedPiece.isWhite() == piece.isWhite())
                {
                    System.out.println("Switched to a new piece: " + piece.toString());
                    selectedPiece = piece;  // Switch to the newly clicked piece
                    selectedPieceX = row;
                    selectedPieceY = col;
                    clearLegalMoveHighlights();  // Clear the old legal move highlights
                    highlightLegalMoves(piece, row, col);  // Highlight the legal moves of the new piece
                }
                // If the clicked piece is of the opposite color, capture the opponent's piece
                else
                {
                    System.out.println("Captured opponent's piece: " + piece.toString());
                    // Move the selected piece to the target square (capture the opponent's piece)
                    if (selectedPiece.move(selectedPieceX, selectedPieceY, row, col, chessBoard))
                    {
                        board[row][col] = selectedPiece;
                        board[selectedPieceX][selectedPieceY] = null;  // Clear the original square
    
                        // Update the UI after the move
                        refreshBoardUI();
    
                        // Deselect the piece after capturing
                        selectedPiece = null;
                        resetTileColors();  // Reset all tile colors after the move
                    }
                    else
                    {
                        System.out.println("Invalid move.");
                        selectedPiece = null;  // Reset selection if the move is invalid
                        resetTileColors();  // Reset tile colors
                        clearLegalMoveHighlights();  // Clear the old legal move highlights
                    }
                }
            }
            else
            {
                // No piece selected, select the new piece and highlight it
                selectedPiece = piece;
                selectedPieceX = row;
                selectedPieceY = col;
                highlightSelectedTile(row, col);  // Highlight the newly selected tile
                highlightLegalMoves(piece, row, col);  // Highlight the legal moves of the selected piece
            }
        }
        else
        {
            // If the square is empty and a piece is selected, move the selected piece
            if (selectedPiece != null)
            {
                System.out.println("Moving piece to square.");
    
                // Get the piece at the target square (where we want to move)
                Piece targetPiece = board[row][col];
    
                // If the target square is empty or has an opponent's piece, proceed
                if (targetPiece == null || (targetPiece != null && targetPiece.isWhite() != selectedPiece.isWhite()))
                {
                    if (selectedPiece.move(selectedPieceX, selectedPieceY, row, col, chessBoard))
                    {
                        // Move the selected piece to the target square
                        board[row][col] = selectedPiece;  // Place the selected piece on the target square
                        board[selectedPieceX][selectedPieceY] = null;  // Clear the original square
    
                        // If there's an opponent's piece on the target square, remove it (capture)
                        if (targetPiece != null)
                        {
                            System.out.println("Captured opponent's piece: " + targetPiece.toString());
                        }
    
                        // Update the UI after the move
                        refreshBoardUI();
    
                        // Deselect the piece after moving
                        selectedPiece = null;
                        resetTileColors();  // Reset all tile colors after the move
                        clearLegalMoveHighlights();  // Clear the legal move highlights
                    }
                    else
                    {
                        System.out.println("Invalid move.");
                        selectedPiece = null;  // Reset selection if the move is invalid
                        resetTileColors();  // Reset tile colors
                        clearLegalMoveHighlights();  // Clear the old legal move highlights
                    }
                }
                else
                {
                    System.out.println("Invalid move, target square occupied by friendly piece.");
                    selectedPiece = null;  // Reset selection if the move is invalid
                    resetTileColors();  // Reset tile colors
                    clearLegalMoveHighlights();  // Clear the old legal move highlights
                }
            }
            else
            {
                // If the square is empty and no piece is selected, do nothing
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
