import java.util.List;
import java.util.ArrayList;

public class King extends Piece
{
    public King(boolean isWhite)
    {
        super(isWhite);
        this.value = 1000;  // King's value is typically considered very high
    }

    @Override
    public List<int[]> getValidMoves(int startX, int startY, ChessBoard board)
    {
        List<int[]> validMoves = new ArrayList<>();

        // Directions for the King: horizontal, vertical, and diagonal (1 square in any direction)
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
            int x = startX + direction[0];
            int y = startY + direction[1];

            // Check if the move is within bounds
            if (x >= 0 && x < 8 && y >= 0 && y < 8)
            {
                // If the square is empty or contains an opponent's piece, add it as a valid move
                if (board.getBoard()[x][y] == null || board.getBoard()[x][y].isWhite() != isWhite())
                {
                    validMoves.add(new int[]{x, y});
                }
            }
        }

        return validMoves;
    }

    @Override
    public boolean move(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        // Check if the move is valid by checking if the target position is in valid moves
        List<int[]> validMoves = getValidMoves(startX, startY, board);
        for (int[] validMove : validMoves)
        {
            if (validMove[0] == endX && validMove[1] == endY)
            {
                // Make the move
                board.getBoard()[endX][endY] = this;
                board.getBoard()[startX][startY] = null;
                
                return true;
            }
        }
        return false;  // Invalid move
    }

    @Override
    public String toString()
    {
        return isWhite() ? "K" : "k";  // White King is 'K', Black King is 'k'
    }

    @Override
    public String getImageFileName()
    {
        return (isWhite() ? "white" : "black") + "king.png";  // File name for King images
    }
}
