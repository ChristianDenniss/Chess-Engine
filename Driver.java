import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        // Create the ChessBoardUI object
        ChessBoardUI chessBoardUI = new ChessBoardUI();

        // Set the scene using the ChessBoardUI object
        primaryStage.setScene(chessBoardUI.getScene());
        primaryStage.setTitle("Chess Board");
        primaryStage.show();
    }

}
