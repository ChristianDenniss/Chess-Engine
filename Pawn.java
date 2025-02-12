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
    
        // Normal one-step move forward
        if (row + 1 < 8 && chessBoard.getBoard()[row + 1][col] == null) 
        {
            validMoves.add(new int[] { row + 1, col });
        }
    
        // Initial two-step move forward
        if (row == 1 && chessBoard.getBoard()[row + 2][col] == null && chessBoard.getBoard()[row + 1][col] == null) 
        {
            validMoves.add(new int[] { row + 2, col });
        }
    
        // Diagonal capture moves (only for opponent pieces)
        if (row + 1 < 8) {
            if (col - 1 >= 0 && chessBoard.getBoard()[row + 1][col - 1] != null && !chessBoard.getBoard()[row + 1][col - 1].isWhite()) 
            {
                validMoves.add(new int[] { row + 1, col - 1 });
            }
            if (col + 1 < 8 && chessBoard.getBoard()[row + 1][col + 1] != null && !chessBoard.getBoard()[row + 1][col + 1].isWhite()) 
            {
                validMoves.add(new int[] { row + 1, col + 1 });
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
        return isWhite() ? "P" : "p";  // White Pawn is 'P', Black Pawn is 'p'
    }

    public boolean isFirstMove()
    {
        return isFirstMove;
    }
}
