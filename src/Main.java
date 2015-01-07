import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This is the main program, it is basically boilerplate to create an animated scene.
 *
 * @author Robert C. Duvall
 */
public class Main extends Application {
    private static final int NUM_FRAMES_PER_SECOND = 60;
    private BallWorld myGame;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        s.setTitle("BallWorld!");
        // create your own game here
        myGame = new BallWorld();
        // attach game to the stage and display it
        Scene scene = myGame.init(s, 400, 400);
        s.setScene(scene);
        s.show();

        // setup the game's loop
        KeyFrame frame = myGame.start(NUM_FRAMES_PER_SECOND);
        Timeline animation = new Timeline();
        animation.setCycleCount(Animation.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
