import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class SoundHandler
{
    private static MediaPlayer mediaPlayer; // Static MediaPlayer to handle the sounds

    public static void playMoveSound()
    {
        playSound("resources/move.mp3");
    }

    public static void playErrorSound()
    {
        playSound("resources/notify.mp3");
    }
    
    public static void playCaptureSound()
    {
        playSound("resources/capture.mp3");
    }

    private static void playSound(String filePath)
    {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
        {
            mediaPlayer.stop(); // Stop the old sound if it's still playing
        }

        // Create and play the new sound
        Media sound = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
