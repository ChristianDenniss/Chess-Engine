public class Queen extends Piece
{
    public Queen(boolean isWhite) 
    {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        // The Queen can move horizontally, vertically, or diagonally
        // 1. Horizontal or vertical movement (dx == 0 or dy == 0)
        if (dx == 0 || dy == 0)
        {
            return isPathClear(startX, startY, endX, endY, board);
        }
        // 2. Diagonal movement (dx == dy)
        else if (dx == dy)
        {
            return isPathClear(startX, startY, endX, endY, board);
        }

        return false;  // Invalid move if neither horizontal, vertical, nor diagonal
    }

    @Override
    public boolean move(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        if (isValidMove(startX, startY, endX, endY, board))
        {
            // Move the Queen piece to the new position
            board.getBoard()[endX][endY] = this;
            board.getBoard()[startX][startY] = null;
            return true;
        }
        return false;  // Return false if the move is invalid
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

    // Helper method to check if the path is clear (no pieces in the way)
    private boolean isPathClear(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        int xDirection = Integer.signum(endX - startX);
        int yDirection = Integer.signum(endY - startY);

        int x = startX + xDirection;
        int y = startY + yDirection;

        // Check if there are pieces in the path
        while (x != endX || y != endY)
        {
            if (board.getBoard()[x][y] != null)  // If there is a piece in the way
            {
                return false;
            }
            x += xDirection;
            y += yDirection;
        }
        // Ensure the destination square is not occupied by a piece of the same color
        Piece targetPiece = board.getBoard()[endX][endY];
        if (targetPiece != null && targetPiece.isWhite() == this.isWhite())
        {
            return false;  // Can't capture a piece of the same color
        }

        return true;  // Path is clear and move is valid
    }
}
