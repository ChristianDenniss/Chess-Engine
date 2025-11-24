public class Move
{
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Piece piece;

    public Move(int startX, int startY, int endX, int endY, Piece piece)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.piece = piece;
    }

    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getEndColumn() { return endX; }
    public int getEndRow() { return endY; }
    public Piece getPiece() { return piece; }

    @Override
    public String toString()
    {
        return "Move from (" + startX + ", " + startY + ") to (" + endX + ", " + endY + ") by " + piece.getClass().getSimpleName();
    }
}
