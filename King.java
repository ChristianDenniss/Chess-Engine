public class King extends Piece
{
    public King(boolean isWhite) 
    {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        // A King can move one square in any direction
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        // King can move one square in any direction: horizontally, vertically, or diagonally
        if (dx <= 1 && dy <= 1)
        {
            // Check if the destination square is occupied by a piece of the same color
            Piece targetPiece = board.getBoard()[endX][endY];
            if (targetPiece != null && targetPiece.isWhite() == this.isWhite())
            {
                return false; // Cannot capture a piece of the same color
            }
            return true; // Valid move
        }

        return false; // Invalid move (the King can only move one square)
    }

    // Method to move the King to a new position on the board
    @Override
    public boolean move(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        if (isValidMove(startX, startY, endX, endY, board))
        {
            // Move the King piece to the new position
            board.getBoard()[endX][endY] = this;
            board.getBoard()[startX][startY] = null;
            return true;
        }
        return false; // Return false if the move is invalid
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
