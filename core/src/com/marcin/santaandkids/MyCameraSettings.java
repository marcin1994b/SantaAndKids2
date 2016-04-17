package com.marcin.santaandkids;

import com.badlogic.gdx.math.Vector3;

public class MyCameraSettings {
	
	private Vector3 vector;
	private boolean movingToRight;
	private boolean movingToLeft;
	private boolean movingToUp;
	private boolean movingToDown;
	private int speed;
	
	public MyCameraSettings(){
		vector = new Vector3();
		movingToRight = false;
		movingToLeft = false;
		movingToUp = false;
		movingToDown = false;
		speed = 2;
	}
	
	public void setVector(int x, int y, int z){
		vector.x = x;
		vector.y = y;
		vector.z = z;
	}
	public void setMovingToRight(boolean tmp){
		movingToRight = tmp;
	}
	public void setMovingToLeft(boolean tmp){
		movingToLeft = tmp;
		}
	public void setMovingToUp(boolean tmp){
		movingToUp = tmp;
	}
	public void setMovingToDown(boolean tmp){
		movingToDown = tmp;
	}
	public void setSpeed(int tmp){
		speed = tmp;
	}
	
	public Vector3 getVector(){
		return vector;
	}
	public boolean isMovingToRight(){
		return movingToRight;
	}
	public boolean isMovingToLeft(){
		return movingToLeft;
	}
	public boolean isMovingToUp(){
		return movingToUp;
	}
	public boolean isMovingToDown(){
		return movingToDown;
	}
	public int getSpeed(){
		return speed;
	}
	
	public void stop(){
		movingToRight = false;
		movingToLeft = false;
		movingToUp = false;
		movingToDown = false;
	}
	

}
