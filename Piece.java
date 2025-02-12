import java.util.List;

public abstract class Piece
{
    protected boolean isWhite;
    protected int value;

    public Piece(boolean isWhite)
    {
        //true will make a white piece
        this.isWhite = isWhite; 
        this.value = 1;
    }

    public abstract List<int[]> getValidMoves(int startX, int startY, ChessBoard board);

    public boolean isWhite() 
    { 
        //check color of piece and return boolean check
        return isWhite; 
    }
    
    public abstract String getImageFileName();
    
    public abstract boolean move(int startX, int startY, int endX, int endY, ChessBoard board);

    public int getValue()
    {
        return value;
    }

}
