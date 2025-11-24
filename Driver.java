 

import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        Player user = new Player("White Player", true, false);
        
        ChessBoardUI chessBoardUI = new ChessBoardUI(user);
        chessBoardUI.getChessBoard();
        
        
        // Set the scene using the ChessBoardUI object
        primaryStage.setScene(chessBoardUI.getScene());
        primaryStage.setTitle("Chess Board");
        primaryStage.show();
    }
}
