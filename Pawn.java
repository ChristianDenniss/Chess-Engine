import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece
{
    private boolean isFirstMove;

    public Pawn(boolean isWhite)
    {
        super(isWhite);
        this.value = 1; // Pawns are typically valued at 1 point
        this.isFirstMove = true;
    }

    // Example of Pawn getValidMoves implementation
    @Override
    public List<int[]> getValidMoves(int row, int col, ChessBoard chessBoard) 
    {
        List<int[]> validMoves = new ArrayList<>();
        
        // Determine direction based on pawn color
        int direction = isWhite() ? 1 : -1;  // White moves down (+1), Black moves up (-1)
    
        // Normal one-step move forward
        if (row + direction >= 0 && row + direction < 8 && chessBoard.getBoard()[row + direction][col] == null) 
        {
            validMoves.add(new int[] { row + direction, col });
        }
    
        // Initial two-step move forward (only for first move)
        if (isFirstMove) 
        {
            int initialRow = isWhite() ? 1 : 6;
            if (row == initialRow 
                && chessBoard.getBoard()[row + 2 * direction][col] == null 
                && chessBoard.getBoard()[row + direction][col] == null) 
            {
                validMoves.add(new int[] { row + 2 * direction, col });
            }
        }
    
        // Diagonal capture moves (only for opponent pieces)
        if (row + direction >= 0 && row + direction < 8) 
        {
            // Left diagonal capture (only if there is an opponent's piece)
            if (col - 1 >= 0 && chessBoard.getBoard()[row + direction][col - 1] != null 
                && chessBoard.getBoard()[row + direction][col - 1].isWhite() != isWhite()) 
            {
                validMoves.add(new int[] { row + direction, col - 1 });
            }
        
            // Right diagonal capture (only if there is an opponent's piece)
            if (col + 1 < 8 && chessBoard.getBoard()[row + direction][col + 1] != null 
                && chessBoard.getBoard()[row + direction][col + 1].isWhite() != isWhite()) 
            {
                validMoves.add(new int[] { row + direction, col + 1 });
            }
        }
    
        return validMoves;
    }



    @Override
    public boolean move(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        // Get the list of valid moves for the piece from its current position
        List<int[]> validMoves = getValidMoves(startX, startY, board);

        // Check if the move is valid by comparing the end position with valid moves
        for (int[] validMove : validMoves)
        {
            if (validMove[0] == endX && validMove[1] == endY)
            {
                // Make the move
                board.getBoard()[endX][endY] = this;
                board.getBoard()[startX][startY] = null;

                // If it's the piece's first move, mark it as no longer the first move
                if (isFirstMove)
                {
                    isFirstMove = false;
                }

                // Check if the piece is on the last rank for possible promotion (for pawns)
                if (endX == 0 || endX == 7)
                {
                    promote(endX, endY, board);
                }

                return true;  // Move was successfully made
                
            }
        }

        return false;  // Move is invalid
    }

    // Method to promote the pawn
    private void promote(int endX, int endY, ChessBoard board)
    {
        board.getBoard()[endX][endY] = new Queen(isWhite());
        
        // Update the visual representation of the piece
        
        
        System.out.println("Pawn promoted to Queen!");
    }

    @Override
    public String getImageFileName()
    {
        return (isWhite() ? "white" : "black") + "pawn.png";  // File name for Pawn images
    }

    @Override
    public String toString()
    {
        return (isWhite() ? "White Pawn" : "Black Pawn") 
                + " (Value: " + value + ", First Move: " + isFirstMove + ")";
    }


    public boolean isFirstMove()
    {
        return isFirstMove;
    }
}
