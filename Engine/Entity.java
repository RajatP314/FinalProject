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
import java.util.*;

public abstract class Entity{

	public int x, y; //Position
	public int xv, yv; //Velocity
	public int xa, ya; //Acceleration
	public int w, h; //Dimensions

	private int maxXV, maxYV; //Max velocity
	private int frictionX, frictionY;

	private ArrayList<Entity> list; //For identifying the entity
	private int index;

	public abstract void draw();

	public void kinematics(){
		xv+=xa;
		yv+=ya;
		x+=xv;
		y+=yv;
		xv*=frictionX;
		yv*=frictionY;
	}



	public ArrayList<Entity> getList(){ return list; }
	public int getIndex(){ return index; }

}