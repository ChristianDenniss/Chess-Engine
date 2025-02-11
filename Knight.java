public class Knight extends Piece
{
    public Knight(boolean isWhite) 
    {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        // Check if the move is in the L-shaped pattern: 2 squares in one direction, 1 square in a perpendicular direction.
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        // Knight's valid move: either 2 squares in one direction and 1 in the other
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2))
        {
            // Check if the destination square is occupied by a piece of the same color
            Piece targetPiece = board.getBoard()[endX][endY];
            if (targetPiece != null && targetPiece.isWhite() == this.isWhite()) 
            {
                return false;  // Cannot capture a piece of the same color
            }
            return true;  // Valid L-shaped move
        }
        return false;  // Invalid move if not in L-shape
    }

    @Override
    public boolean move(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        if (isValidMove(startX, startY, endX, endY, board))
        {
            // Move the Knight piece to the new position
            board.getBoard()[endX][endY] = this;
            board.getBoard()[startX][startY] = null;
            return true;
        }
        return false;  // Return false if the move is invalid
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
