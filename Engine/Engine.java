import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

public class Engine extends Application implements EventHandler<InputEvent>{

	Group root;
	Canvas c;
	GraphicsContext f;
	Image trooper;
	int xpos, xvel;
	int ypos, yvel;
	Animator animator;
	boolean[] keyList;
	int state;

	public static void main(String[] args){

		launch();
	}
	public void start(Stage stage){
		state = 0;
		keyList = new boolean[128];
		stage.setTitle("Test JavaFX");
		root = new Group();
		c = new Canvas(800, 600);
		root.getChildren().add(c);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		f = c.getGraphicsContext2D();
		xpos = 100;
		ypos = 100;
		trooper = new Image("trooper.jpg");
		f.drawImage(trooper, xpos, 100);
		animator = new Animator();
		animator.start();
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, this);
		URL resource = getClass().getResource("test.wav");
		AudioClip clip = new AudioClip(resource.toString());
		clip.play();
		stage.show();
	}

	public void init(){
		xpos = 100;
		ypos = 100;
		xvel = 10;
		yvel = 10;
	}

	public void handle(final InputEvent event){
		char key = 0;
		KeyCode code = ((KeyEvent)event).getCode();
		if(code == KeyCode.LEFT){
			key = 37;
		} else if(code == KeyCode.RIGHT){
			key = 39;
		} else if(code == KeyCode.UP){
			key = 38;
		} else if(code == KeyCode.DOWN){
			key = 40;
		} else {
			key = ((KeyEvent)event).getText().charAt(0);
		}
		if(((KeyEvent)event).getEventType() == KeyEvent.KEY_PRESSED){
			keyList[key] = true;
			if(state == 0 && keyList[32]){
				state = 1;
			}
			if(state == 2 && keyList[32]){
				state = 1;
				init();
			}
		}
		if(((KeyEvent)event).getEventType() == KeyEvent.KEY_RELEASED){
			keyList[key] = false;
		}
	}

	public class Animator extends AnimationTimer{
		public void handle(long now){
			f.clearRect(0,0,c.getWidth(), c.getHeight());
			switch(state){
				case 0: //Start Menu
					f.setFill(Color.BLUE);
					f.setFont( new Font("Impact", 32) );
					f.fillText("Press space to begin", 100, 50);
					break;
				case 1: //Actual Gameplay
					if(keyList[37] == true){
						xpos-=xvel;
					}
					if(keyList[38] == true){
						ypos-=yvel;
					}
					if(keyList[39] == true){
						xpos+=xvel;
					}
					if(keyList[40] == true){
						ypos+=yvel;
					}

					if(xpos+trooper.getWidth() > (int) c.getWidth()){
						xpos = (int) c.getWidth() - (int)trooper.getWidth();
					}
					if(xpos < 0){
						state = 2;
					}
					if(ypos+trooper.getHeight() > (int) c.getHeight()){
						ypos = (int) c.getHeight() - (int)trooper.getHeight();
					}
					if(ypos < 0){
						ypos = 0;
					}
					f.drawImage(trooper, xpos, ypos);
					break;
				case 2: //Game Over
					f.setFill(Color.RED);
					f.setFont( new Font("Times New Roman", 48) );
					f.fillText("Press space to restart", 100, 200);
					break;
				default: break;
			}
		}
	}
}