import java.util.List;

public class ChessAI
{
    private ChessBoard board;
    private boolean isWhite; // true for white, false for black
    private static final int MAX_DEPTH = 5; // Reduced for better performance

    // Piece value constants (adjusted for better balance)
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 10000;

    // Piece-square tables (for simple positional evaluation)
    private static final int[][] PAWN_TABLE = {
        { 0,  5,  5,  0,  5, 10, 50,  0},
        { 0, 10, -5,  0,  5, 15, 50,  0},
        { 0, 10, 10, 20, 30, 40, 50,  0},
        { 0, 10, 20, 25, 35, 45, 50,  0},
        { 0, 10, 20, 25, 35, 45, 50,  0},
        { 0, 10, 10, 20, 30, 40, 50,  0},
        { 0, 10, -5,  0,  5, 15, 50,  0},
        { 0,  5,  5,  0,  5, 10, 50,  0}
    };

    // Constructor
    public ChessAI(ChessBoard board, boolean isWhite)
    {
        this.board = board;
        this.isWhite = isWhite;
    }

    // Evaluates board with material and positional bonuses
    private int evaluateBoard(ChessBoard simulatedBoard)
    {
        int score = 0;

        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                Piece piece = simulatedBoard.getBoard()[row][col];
                if (piece != null) 
                {
                    int pieceValue = piece.getValue();
                    int positionBonus = getPieceSquareBonus(piece, row, col);
                    score += (piece.isWhite() == isWhite) ? (pieceValue + positionBonus) : -(pieceValue + positionBonus);
                }
            }
        }

        return score;
    }

    // Positional bonuses using piece-square tables
    private int getPieceSquareBonus(Piece piece, int row, int col)
    {
        if (piece instanceof Pawn)
        {
            return piece.isWhite() ? PAWN_TABLE[row][col] : PAWN_TABLE[7 - row][col];
        }
        return 0; // Add similar tables for other pieces if needed
    }

    // Move ordering for better alpha-beta pruning
    private void orderMoves(List<Move> moves)
    {
        moves.sort((a, b) -> Integer.compare(getMoveScore(b), getMoveScore(a)));
    }

    private int getMoveScore(Move move)
    {
        Piece captured = board.getPiece(move.getEndRow(), move.getEndColumn());
        return (captured != null) ? captured.getValue() : 0;
    }

    // Minimax with alpha-beta pruning
    private int minimax(ChessBoard simulatedBoard, int depth, boolean isMaximizingPlayer, int alpha, int beta)
    {
        if (depth == 0) 
        {
            return evaluateBoard(simulatedBoard);
        }

        List<Move> moves = simulatedBoard.generateValidMoves(isMaximizingPlayer ? isWhite : !isWhite);
        orderMoves(moves); // Order moves for better efficiency

        if (isMaximizingPlayer) 
        {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves)
            {
                simulatedBoard.applyMove(move);
                int eval = minimax(simulatedBoard, depth - 1, false, alpha, beta);
                simulatedBoard.undoLastMove();
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) 
                {
                    break;
                }
            }
            return maxEval;
        } 
        else 
        {
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves)
            {
                simulatedBoard.applyMove(move);
                int eval = minimax(simulatedBoard, depth - 1, true, alpha, beta);
                simulatedBoard.undoLastMove();
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) 
                {
                    break;
                }
            }
            return minEval;
        }
    }

    // Get the best move
    public Move getBestMove()
    {
        ChessBoard simulatedBoard = new ChessBoard(board);
        List<Move> moves = simulatedBoard.generateValidMoves(isWhite);
        orderMoves(moves);

        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        for (Move move : moves)
        {
            simulatedBoard.applyMove(move);
            int moveValue = minimax(simulatedBoard, MAX_DEPTH - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            simulatedBoard.undoLastMove();

            if (moveValue > bestValue)
            {
                bestValue = moveValue;
                bestMove = move;
            }
        }
        return bestMove;
    }

    // Execute best move
    public void makeMove()
    {
        Move bestMove = getBestMove();

        if (bestMove == null) // Fallback to a random move
        {
            List<Move> validMoves = board.generateValidMoves(isWhite);
            if (!validMoves.isEmpty()) 
            {
                bestMove = validMoves.get((int) (Math.random() * validMoves.size()));
                System.out.println("No optimal move found, making random move.");
            } 
            else 
            {
                System.out.println("No valid moves available! This should not happen.");
                return;
            }
        }

        board.applyMove(bestMove);
    }
}
