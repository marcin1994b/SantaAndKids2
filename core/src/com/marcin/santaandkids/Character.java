package com.marcin.santaandkids;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Character extends Actor{

	private Vector2 vector;
	
	private int speed;
	
	private boolean inGame;
	
	private boolean movingToRight;
	private boolean movingToLeft;
	private boolean movingToUp;
	private boolean movingToDown;
	

	
	//-------Setters---------
	public void setVector(int x, int y){
		if(this.vector == null){
			this.vector = new Vector2();
		}
		this.vector.x = x;
		this.vector.y = y;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public void setInGame(boolean inGame){
		this.inGame = inGame;
	}
	public void setMovingToRight(boolean right){
		this.movingToRight = right;
	}
	public void setMovingToLeft(boolean left){
		this.movingToLeft = left;
	}
	public void setMovingToUp(boolean up){
		this.movingToUp = up;
	}
	public void setMovingToDown(boolean down){
		this.movingToDown = down;
	}
	
	//-------Getters---------
	public Vector2 getVector(){
		return this.vector;
	}
	public int getSpeed(){
		return this.speed;
	}
	public boolean isInGame(){
		return this.inGame;
	}
	public boolean isMovingToRight(){
		if(movingToRight == true && movingToDown == false && movingToLeft == false && movingToUp == false){
			return true;
		}else{
			return false;
		}
	}
	public boolean isMovingToLeft(){
		if(movingToRight == false && movingToDown == false && movingToLeft == true && movingToUp == false){
			return true;
		}else{
			return false;
		}
	}
	public boolean isMovingToUp(){
		if(movingToRight == false && movingToDown == false && movingToLeft == false && movingToUp == true){
			return true;
		}else{
			return false;
		}
	}
	public boolean isMovingToDown(){
		if(movingToRight == false && movingToDown == true && movingToLeft == false && movingToUp == false){
			return true;
		}else{
			return false;
		}
	}
	public Rectangle getRectangle() {
		return new Rectangle(this.vector.x, this.vector.y, 64, 64);
	}
	
	//--------Moving---------
	public void stop(){
		movingToRight = false;
		movingToLeft = false;
		movingToUp = false;
		movingToDown = false;
	}
	
	public void moving(){
		if(isMovingToDown() == true){
			vector.y = vector.y - speed;
		}else if(isMovingToLeft() == true){
			vector.x = vector.x - speed;
		}else if(isMovingToRight() == true){
			vector.x = vector.x + speed;
		}else if(isMovingToUp() == true){
			vector.y = vector.y + speed;
		}
	}
	public boolean isMoving(){
		if(movingToDown == true || movingToLeft == true || movingToRight == true || movingToUp == true){
			return true;
		}
		return false;
	}
	public void setMoveDirection(int direction){
		if(direction == Constans.DOWN){
			movingToDown = true;
			movingToLeft = false;
			movingToRight = false;
			movingToUp = false;
		}else if(direction == Constans.LEFT){
			movingToLeft = true;
			movingToDown = false;
			movingToRight = false;
			movingToUp = false;
		}else if(direction == Constans.RIGHT){
			movingToRight = true;
			movingToLeft = false;
			movingToDown = false;
			movingToUp = false;
		}else if(direction == Constans.UP){
			movingToUp = true;
			movingToLeft = false;
			movingToRight = false;
			movingToDown = false;
		}
	}
	public boolean collisionDetector(TiledMap map){
		MapObjects objects = map.getLayers().get("collision").getObjects();
	    Rectangle rectangle;
	    
	    if(this.isMovingToLeft() == true){
	    	for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
	    		rectangle = rectangleObject.getRectangle();
	    		if(rectangle.x+rectangle.width >= this.getVector().x+16 && rectangle.x <= this.getVector().x+16 && this.getVector().y+36 >= rectangle.y && this.getVector().y+36 <= rectangle.y+rectangle.height){
	    			return true;
	    		}
	    	}
	    }else if(this.isMovingToDown() == true){
	    	for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
	    		rectangle = rectangleObject.getRectangle();
	    		if(rectangle.x+rectangle.width >= this.getVector().x+36 && rectangle.x <= this.getVector().x+36 && this.getVector().y >= rectangle.y && this.getVector().y <= rectangle.y+rectangle.height){
	    			return true;
	    		}
	    	}
	    	
	    }else if(this.isMovingToRight() == true){
	    	for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
	    		rectangle = rectangleObject.getRectangle();
	    		if(rectangle.x+rectangle.width >= this.getVector().x+48 && rectangle.x <= this.getVector().x+48 && this.getVector().y+32 >= rectangle.y && this.getVector().y+32 <= rectangle.y+rectangle.height){
	    			return true;
	    		}
	    	}
	    	
	    }else if(this.isMovingToUp() == true){
	    	for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
	    		rectangle = rectangleObject.getRectangle();
	    		if(rectangle.x+rectangle.width >= this.getVector().x+32 && rectangle.x <= this.getVector().x+32 && this.getVector().y+48 >= rectangle.y && this.getVector().y+48 <= rectangle.y+rectangle.height){
	    			return true;
	    		}
	    	}
	    }
		return false;
	}

}
