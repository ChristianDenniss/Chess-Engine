import java.util.List;

public class ChessBoard
{
    private Piece[][] board;

    public ChessBoard()
    {
        this.board = new Piece[8][8];
        
        setupBoard();
        
    }

    private void setupBoard()
    {
        // White pieces
        board[0][0] = new Rook(true); board[0][1] = new Knight(true); board[0][2] = new Bishop(true);
        board[0][3] = new Queen(true); board[0][4] = new King(true); board[0][5] = new Bishop(true);
        board[0][6] = new Knight(true); board[0][7] = new Rook(true);
        
        //create white row of pawns
        for (int i = 0; i < 8; i++)
        {
            board[1][i] = new Pawn(true);
        }

        // Black named pieces
        board[7][0] = new Rook(false); board[7][1] = new Knight(false); board[7][2] = new Bishop(false);
        board[7][3] = new Queen(false); board[7][4] = new King(false); board[7][5] = new Bishop(false);
        board[7][6] = new Knight(false); board[7][7] = new Rook(false);
        
        //create the black row of pawns 
        for (int i = 0; i < 8; i++)
        {
            board[6][i] = new Pawn(false);
        }

        // Empty squares
        for (int i = 2; i < 6; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                board[i][j] = null;
            }
        }
    }
    
    public boolean isKingInCheck(boolean isWhite)
    {
        int kingX = -1, kingY = -1;
        
        // Find the king's position
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.isWhite() == isWhite)
                {
                    kingX = row;
                    kingY = col;
                    break;
                }
            }
        }
    
        // Ensure the king was found
        if (kingX == -1 || kingY == -1)
        {
            return false; // Should never happen in a valid game
        }
    
        // Check if any enemy piece can attack the king
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                Piece piece = board[row][col];
                if (piece != null && piece.isWhite() != isWhite)
                {
                    List<int[]> opponentMoves = piece.getValidMoves(row, col, this);
                    for (int[] move : opponentMoves)
                    {
                        if (move[0] == kingX && move[1] == kingY)
                        {
                            return true; // King is under attack
                        }
                    }
                }
            }
        }
        return false;
    }

    
    // Method to print the board, instead of using toString 
    public void printBoard()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j] == null)
                {
                    System.out.print(". ");
                }
                else
                {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // Method to get a piece at a specific position
    public Piece getPiece(int x, int y)
    {
        return board[x][y];
    }
    
    public Piece[][] getBoard()
    {
        return board;
    }
    
    // Method to move a piece
    public boolean movePiece(int startX, int startY, int endX, int endY)
    {
        Piece piece = board[startX][startY];
        
        if (piece != null)
        {
            // Get the list of valid moves for the piece
            List<int[]> validMoves = piece.getValidMoves(startX, startY, this);
            
            // Check if the destination is in the list of valid moves
            for (int[] validMove : validMoves)
            {
                if (validMove[0] == endX && validMove[1] == endY)
                {
                    // Move the piece
                    board[endX][endY] = piece;
                    board[startX][startY] = null;
                    return true; // Successful move
                }
            }
        }
        
        return false; // Invalid move
    }
}
