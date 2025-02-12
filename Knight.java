import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece
{
    public Knight(boolean isWhite)
    {
        super(isWhite);
        this.value = 3;  // Knights are typically valued at 3 points
    }

    @Override
    public List<int[]> getValidMoves(int startX, int startY, ChessBoard board)
    {
        List<int[]> validMoves = new ArrayList<>();

        // Possible move offsets for a knight (L-shaped moves)
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},  // Moves 2 squares in one direction, 1 square in perpendicular direction
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}   // Moves 1 square in one direction, 2 squares in perpendicular direction
        };

        // Loop through each possible knight move
        for (int[] move : knightMoves)
        {
            int newX = startX + move[0];
            int newY = startY + move[1];

            // Check if the move is within bounds
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8)
            {
                // If the square is empty or occupied by an opponent's piece, add it as a valid move
                if (board.getBoard()[newX][newY] == null || board.getBoard()[newX][newY].isWhite() != isWhite())
                {
                    validMoves.add(new int[]{newX, newY});
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
        return isWhite() ? "N" : "n";  // White Knight is 'N', Black Knight is 'n'
    }

    @Override
    public String getImageFileName()
    {
        return (isWhite() ? "white" : "black") + "knight.png";  // File name for Knight images
    }
}
