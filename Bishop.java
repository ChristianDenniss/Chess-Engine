import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece
{
    public Bishop(boolean isWhite)
    {
        super(isWhite);
        this.value = 3;  // Bishops are typically valued at 3 points
    }

    @Override
    public List<int[]> getValidMoves(int startX, int startY, ChessBoard board)
    {
        List<int[]> validMoves = new ArrayList<>();

        // Directions for Bishop: diagonal movement
        int[][] directions = {
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
        // Check if the move is valid by comparing the end position with valid moves
        List<int[]> validMoves = getValidMoves(startX, startY, board);
        for (int[] validMove : validMoves)
        {
            if (validMove[0] == endX && validMove[1] == endY)
            {
                // Make the move
                board.getBoard()[endX][endY] = this;
                board.getBoard()[startX][startY] = null;
                
                return true;  // Move was successfully made
            }
        }

        return false;  // Invalid move
    }

    @Override
    public String toString()
    {
        return isWhite() ? "B" : "b";  // White Bishop is 'B', Black Bishop is 'b'
    }

    @Override
    public String getImageFileName()
    {
        return (isWhite() ? "white" : "black") + "bishop.png";  // File name for Bishop images
    }
}
