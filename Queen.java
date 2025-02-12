import java.util.List;
import java.util.ArrayList;


public class Queen extends Piece
{
   
    public Queen(boolean isWhite)
    {
        super(isWhite);
        this.value = 9;
    }

    @Override
    public List<int[]> getValidMoves(int startX, int startY, ChessBoard board)
    {
        List<int[]> validMoves = new ArrayList<>();
    
        // Directions for Queen: horizontal, vertical, and diagonal
        int[][] directions = {
            {-1, 0}, // Up
            {1, 0},  // Down
            {0, -1}, // Left
            {0, 1},  // Right
            {-1, -1}, // Diagonal up-left
            {-1, 1},  // Diagonal up-right
            {1, -1},  // Diagonal down-left
            {1, 1}    // Diagonal down-right
        };
    
        // Loop through each direction
        for (int[] direction : directions)
        {
            int x = startX;
            int y = startY;
    
            // Move in the current direction
            while (true)
            {
                x += direction[0];
                y += direction[1];
    
                // Check if the move is within bounds
                if (x < 0 || x >= 8 || y < 0 || y >= 8)
                {
                    break; // Stop if out of bounds
                }
    
                // If the square is empty, add it as a valid move
                if (board.getBoard()[x][y] == null)
                {
                    validMoves.add(new int[]{x, y});
                }
                else
                {
                    // If the square contains an opponent's piece, add it as a capture move
                    if (board.getBoard()[x][y].isWhite() != isWhite())
                    {
                        validMoves.add(new int[]{x, y});
                    }
                    break; // Stop if there's a piece, whether it is friendly or not
                }
            }
        }
    
        return validMoves;
    }

    @Override
    public boolean move(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        // Get the list of valid moves for the Queen
        List<int[]> validMoves = getValidMoves(startX, startY, board);
    
        // Check if the end position is in the list of valid moves
        for (int[] validMove : validMoves)
        {
            if (validMove[0] == endX && validMove[1] == endY)
            {
                // Move is valid, update the board
                board.getBoard()[endX][endY] = this;
                board.getBoard()[startX][startY] = null;
                return true;
            }
        }
    
        // If no valid move found, return false
        return false;
    }

    @Override
    public String toString()
    {
        return isWhite() ? "Q" : "q";  // White Queen is 'Q', Black Queen is 'q'
    }

    @Override
    public String getImageFileName()
    {
        return (isWhite() ? "white" : "black") + "queen.png";  // File name for Queen images
    }
}
