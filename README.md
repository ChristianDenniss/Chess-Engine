
# Chess Game with JavaFX

This project implements a chess game with a graphical user interface (GUI) using JavaFX. The game includes basic functionality such as piece movement, legal move highlights, pawn promotion, and player interaction through a grid-based interface.

## Features

> **Interactive Chess Board:**  
> The chessboard is represented by an 8x8 grid where each square is clickable.

> **Piece Movement:**  
> Players can move pieces by selecting a piece and clicking a destination square.

> **Pawn Promotion:**  
> Pawns are automatically promoted to queens when they reach the opponent's back rank.

> **Legal Move Highlights:**  
> When a piece is selected, the legal moves are highlighted on the board.

> **Capture Pieces:**  
> Pieces can capture opponent pieces when moved to their square.

> **Sound Effects:**  
> Sound effects are played on piece movement and capture events.

## Installation

1. Clone the repository to your local machine:

    ```bash
    git clone https://github.com/yourusername/chess-game.git
    ```

2. Navigate to the project directory:

    ```bash
    cd chess-game
    ```

3. Compile and run the project using your IDE or a build tool (e.g., Maven, Gradle).

4. Ensure that JavaFX is properly configured in your IDE for running JavaFX applications.

## Dependencies

- **JavaFX:** Required to run the GUI. Make sure you have the correct JavaFX libraries installed.
- **SoundHandler:** Manages the sounds for moves and captures.

## Usage

1. Run the `ChessBoardUI` class to start the game.
2. Click on pieces to select them and then click on the square to move them.
3. The game will automatically promote a pawn to a queen when it reaches the opposite back rank.
4. Capturing pieces is done by moving a piece onto an opponent's piece.
5. Legal moves are highlighted when a piece is selected, and invalid moves are prevented.

## Files

- **ChessBoardUI.java:** The main user interface class for the game, responsible for rendering the board and handling user input.
- **ChessBoard.java:** The core game logic, representing the chessboard and managing pieces.
- **Piece.java:** The abstract class that all individual chess pieces inherit from (e.g., Pawn, Queen, Rook).
- **SoundHandler.java:** A utility class for playing sound effects during the game.
- **Resources:** Contains image files for the pieces (e.g., queen_white.png, queen_black.png).

---

## Future Plans

> **AI Opponent:**  
> Add an artificial intelligence (AI) opponent to allow single-player gameplay. The AI will analyze the board and make optimal moves based on a simple evaluation function.

> **Check and Checkmate Detection:**  
> Implement logic to detect when a player's king is in check and handle checkmate conditions. This will include visual cues on the board when the king is in danger.

> **Move History:**  
> Add a move history feature that allows players to see all previous moves made during the game. This could be displayed in a sidebar or log for easy reference.

> **Undo and Redo Moves:**  
> Implement an undo/redo feature so players can go back to previous moves. This will allow for better gameplay and mistake correction.

> **Time Control:**  
> Add a timer for each player, implementing standard chess time control (e.g., 5 minutes per player). The timer will count down and add a level of challenge for competitive play.

> **User Interface Improvements:**  
> Enhance the visual aesthetics of the game, including better animations for piece movement and captures. Add more customization options, such as changing themes or board styles.

> **Multiplayer Mode:**  
> Implement a network multiplayer mode to allow players to compete against each other over the internet. The server-client model would facilitate real-time gameplay between two users.

---

## License

This project is licensed under the MIT License - see the LICENSE file for further details.


