import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * This represents the primary class for a game/animation.
 *
 * @author Robert C. Duvall
 */
class BallWorld {
    private static final double ENEMY_GROWTH_FACTOR = 1.1;
    private static final int OPPONENT_SIZE = 40;
    private static final int PLAYER_SPEED = 4;

    private Scene myScene;
    private Group myRoot;
    private ImageView myPlayer;
    private Circle myEnemy;
    private Point2D myEnemyVelocity;
    private Random myGenerator = new Random();


    /**
     * Create the game's scene
     */
    public Scene init (Stage s, int width, int height) {
        // create a scene graph to organize the scene
        myRoot = new Group();
        // make some shapes and set their properties
        myPlayer = new ImageView(new Image(getClass().getResourceAsStream("images/duke.gif")));
        myPlayer.setTranslateX(myGenerator.nextInt(width));
        myPlayer.setTranslateY(myGenerator.nextInt(height));
        myEnemy = new Circle(myGenerator.nextInt(width), myGenerator.nextInt(height), OPPONENT_SIZE);
        myEnemy.setFill(Color.RED);
        myEnemy.setOnMouseClicked(e -> handleMouseInput(e));
        myEnemyVelocity = new Point2D(myGenerator.nextInt(5) - 3, myGenerator.nextInt(5) - 3);
        // remember shapes for viewing later
        myRoot.getChildren().add(myEnemy);
        myRoot.getChildren().add(myPlayer);

        // create a place to display the shapes and react to input
        myScene = new Scene(myRoot, width, height, Color.WHITE);
        myScene.setOnKeyPressed(e -> handleKeyInput(e));
        return myScene;
    }


    /**
     * Create the game's frame
     */
    public KeyFrame start (int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate), e -> updateSprites());
    }

    /**
     * What to do each game frame
     *
     * Change the sprite properties each frame by a tiny amount to animate them
     *
     * Note, there are more sophisticated ways to animate shapes, but these simple ways work too.
     */
    private void updateSprites () {
        myPlayer.setRotate(myPlayer.getRotate() + 1);
        myEnemy.setCenterX(myEnemy.getCenterX() + myEnemyVelocity.getX());
        myEnemy.setCenterY(myEnemy.getCenterY() + myEnemyVelocity.getY());
        if (myEnemy.getCenterX() >= myScene.getWidth() || myEnemy.getCenterX() <= 0) {
            myEnemyVelocity = new Point2D(myEnemyVelocity.getX() * -1, myEnemyVelocity.getY());
        }
        if (myEnemy.getCenterY() >= myScene.getHeight() || myEnemy.getCenterY() <= 0) {
            myEnemyVelocity = new Point2D(myEnemyVelocity.getX(), myEnemyVelocity.getY() * -1);
        }
        checkCollide(myPlayer, myEnemy);
    }

    /**
     * What to do each time a key is pressed
     */
    private void handleKeyInput (KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.RIGHT) {
            myPlayer.setTranslateX(myPlayer.getTranslateX() + PLAYER_SPEED);
        }
        else if (keyCode == KeyCode.LEFT) {
            myPlayer.setTranslateX(myPlayer.getTranslateX() - PLAYER_SPEED);
        }
        else if (keyCode == KeyCode.UP) {
            myPlayer.setTranslateY(myPlayer.getTranslateY() - PLAYER_SPEED);
        }
        else if (keyCode == KeyCode.DOWN) {
            myPlayer.setTranslateY(myPlayer.getTranslateY() + PLAYER_SPEED);
        }
    }
    
    /**
     * What to do each time the mouse is clicked
     */
    private void handleMouseInput (MouseEvent e) {
        myEnemy.setScaleX(myEnemy.getScaleX() * ENEMY_GROWTH_FACTOR);
        myEnemy.setScaleY(myEnemy.getScaleY() * ENEMY_GROWTH_FACTOR);
    }

    /**
     * What to do each time shapes collide
     */
    private void checkCollide (Node player, Node enemy) {
        // check for collision
        if (player.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
            System.out.println("Collide!");
        }
    }
}
