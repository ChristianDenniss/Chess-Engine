public class Rook extends Piece
{
    public Rook(boolean isWhite) 
    {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        // A Rook can move horizontally or vertically
        if (startX != endX && startY != endY) 
        {
            return false;  // The Rook must move in a straight line (horizontally or vertically)
        }

        // Check if there are any pieces blocking the path
        if (startX == endX) 
        {
            // Moving vertically, check rows in between
            int minY = Math.min(startY, endY);
            int maxY = Math.max(startY, endY);
            for (int y = minY + 1; y < maxY; y++) 
            {
                if (board.getBoard()[startX][y] != null) 
                {
                    return false;  // Path is blocked by another piece
                }
            }
        } 
        else if (startY == endY) 
        {
            // Moving horizontally, check columns in between
            int minX = Math.min(startX, endX);
            int maxX = Math.max(startX, endX);
            for (int x = minX + 1; x < maxX; x++) 
            {
                if (board.getBoard()[x][startY] != null) 
                {
                    return false;  // Path is blocked by another piece
                }
            }
        }

        // Check if the destination square is occupied by a piece of the same color
        Piece targetPiece = board.getBoard()[endX][endY];
        if (targetPiece != null && targetPiece.isWhite() == this.isWhite()) 
        {
            return false;  // Cannot capture a piece of the same color
        }

        return true;  // Valid move
    }

    @Override
    public boolean move(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        if (isValidMove(startX, startY, endX, endY, board))
        {
            // Move the Rook piece to the new position
            board.getBoard()[endX][endY] = this;
            board.getBoard()[startX][startY] = null;
            return true;
        }
        return false; // Return false if the move is invalid
    }

    @Override
    public String toString()
    {
        return isWhite() ? "R" : "r";  // White Rook is 'R', Black Rook is 'r'
    }
    
    @Override
    public String getImageFileName()
    {
        return (isWhite() ? "white" : "black") + "rook.png";  // File name for Rook images
    }
}
