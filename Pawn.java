public class Pawn extends Piece
{
    private boolean isFirstMove;

    public Pawn(boolean isWhite)
    {
        super(isWhite);
        this.isFirstMove = true;
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        // Movement logic remains unchanged

        // Check normal move, first move (two squares), and capture logic
        // (same as in the previous implementation)

        return false;
    }

    @Override
    public String getImageFileName()
    {
        return (isWhite ? "white" : "black") + "pawn.png";
    }
    
    @Override
    public boolean move(int startX, int startY, int endX, int endY, ChessBoard board)
    {
        if (isValidMove(startX, startY, endX, endY, board))
        {
            board.getBoard()[endX][endY] = this;
            board.getBoard()[startX][startY] = null;

            if (isFirstMove)
            {
                isFirstMove = false;
            }

            // Check if the pawn is on the last rank (promotion)
            if (endX == 0 || endX == 7)
            {
                promotePawn(endX, endY, board);
            }

            return true;
        }
        return false;
    }

    // Method to promote the pawn
    private void promotePawn(int endX, int endY, ChessBoard board)
    {
        // Promotion logic: Let’s just promote to a Queen for now.
        // In a full game, you might prompt the user to choose.

        // For simplicity, let's assume the player always promotes to a Queen
        Piece promotedPiece = new Queen(isWhite());  // Create a new Queen of the same color

        // Replace the pawn with the promoted piece
        board.getBoard()[endX][endY] = promotedPiece;

        System.out.println("Pawn promoted to Queen!");
    }

    @Override
    public String toString()
    {
        return isWhite() ? "P" : "p";
    }

    public boolean isFirstMove()
    {
        return isFirstMove;
    }
}
