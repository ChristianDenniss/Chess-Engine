public class Player
{
    private String name;
    private boolean isWhite;
    private int score;
    private boolean isAI;

    public Player(String name, boolean isWhite, boolean isAI)
    {
        this.name = name;
        this.isWhite = isWhite;
        this.isAI = isAI;
        this.score = 0;
        
    }

    public String getName()
    {
        return name;
    }

    public boolean isWhite()
    {
        return isWhite;
    }

    public int getScore()
    {
        return score;
    }

    public boolean isAI()
    {
        return isAI;
    }
}
