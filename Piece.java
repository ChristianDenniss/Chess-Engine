public abstract class Piece
{
    protected boolean isWhite;

    public Piece(boolean isWhite)
    {
        //true will make a white piece
        this.isWhite = isWhite; 
    }

    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board);

    public boolean isWhite() 
    { 
        //check color of piece and return boolean check
        return isWhite; 
    }
    
    public abstract String getImageFileName();
    
    public abstract boolean move(int startX, int startY, int endX, int endY, ChessBoard board);
}