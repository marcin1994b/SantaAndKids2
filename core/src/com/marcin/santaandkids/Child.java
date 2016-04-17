package com.marcin.santaandkids;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;


public class Child extends Character implements Runnable{
	
	private int childType;
	
	private Random random;
	
	private boolean isSleeping;
	private boolean isHappy;
	
	private int distanceToChangeDirection;
	private int distanceToBeTired;
	
	private float sleepingTime = 0;
	private float sleepTime = 0;
	
	private Present present;
	
	private long diff;
	private long start = System.currentTimeMillis();

	public Child(int speed, boolean inGame, int childType) {
		this.random = Logic.getRandom();
		this.setVector((random.nextInt(40)+5)*32, (random.nextInt(40)+5)*32);
		this.distanceToBeTired = (random.nextInt(5)+5)*32;
		this.setSpeed(speed);
		this.setInGame(inGame);
		this.childType = childType;
		
	}
	
	//---------setters and getters--------
	
	public void setSleeping(boolean sleeping){
		this.isSleeping = sleeping;
	}
	public void setHappy(boolean happy){
		this.isHappy = happy;
	}
	
	public boolean isSleeping(){
		return isSleeping;
	}
	public boolean isHappys(){
		return isHappy;
	}
	
	//-----------public functions---------

	@Override
	public void run() {
		while(this.isInGame()){
			move();
			mySleep(60);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		if(isMovingToDown() == true ){
			if(childType == 0){
				batch.draw(GameScreen.getKidOneTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), getVector().x, getVector().y);
			}else if(childType == 1){ 
				batch.draw(GameScreen.getKidTwoTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), getVector().x, getVector().y);
			}else if(childType == 2){
				batch.draw(GameScreen.getKidThreeTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), getVector().x, getVector().y);
			}else if(childType == 3){
				batch.draw(GameScreen.getKidFourTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), getVector().x, getVector().y);
			}
		}else if(isMovingToLeft() == true){
			if(childType == 0){
				batch.draw(GameScreen.getKidOneTexture().get(Constans.TEXTURE_OF_LEFT_SIDE), getVector().x, getVector().y);
			}else if(childType == 1){ 
				batch.draw(GameScreen.getKidTwoTexture().get(Constans.TEXTURE_OF_LEFT_SIDE), getVector().x, getVector().y);
			}else if(childType == 2){
				batch.draw(GameScreen.getKidThreeTexture().get(Constans.TEXTURE_OF_LEFT_SIDE), getVector().x, getVector().y);
			}else if(childType == 3){
				batch.draw(GameScreen.getKidFourTexture().get(Constans.TEXTURE_OF_LEFT_SIDE), getVector().x, getVector().y);
			}
		}else if(isMovingToRight() == true){
			if(childType == 0){
				batch.draw(GameScreen.getKidOneTexture().get(Constans.TEXTURE_OF_RIGHT_SIDE), getVector().x, getVector().y);
			}else if(childType == 1){ 
				batch.draw(GameScreen.getKidTwoTexture().get(Constans.TEXTURE_OF_RIGHT_SIDE), getVector().x, getVector().y);
			}else if(childType == 2){
				batch.draw(GameScreen.getKidThreeTexture().get(Constans.TEXTURE_OF_RIGHT_SIDE), getVector().x, getVector().y);
			}else if(childType == 3){
				batch.draw(GameScreen.getKidFourTexture().get(Constans.TEXTURE_OF_RIGHT_SIDE), getVector().x, getVector().y);
			}
		}else if(isMovingToUp() == true){
			if(childType == 0){
				batch.draw(GameScreen.getKidOneTexture().get(Constans.TEXTURE_OF_BACK_SIDE), getVector().x, getVector().y);
			}else if(childType == 1){ 
				batch.draw(GameScreen.getKidTwoTexture().get(Constans.TEXTURE_OF_BACK_SIDE), getVector().x, getVector().y);
			}else if(childType == 2){
				batch.draw(GameScreen.getKidThreeTexture().get(Constans.TEXTURE_OF_BACK_SIDE), getVector().x, getVector().y);
			}else if(childType == 3){
				batch.draw(GameScreen.getKidFourTexture().get(Constans.TEXTURE_OF_BACK_SIDE), getVector().x, getVector().y);
			}
		}else if(this.isInGame() == true){
			if(childType == 0){
				batch.draw(GameScreen.getKidOneTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), getVector().x, getVector().y);
			}else if(childType == 1){ 
				batch.draw(GameScreen.getKidTwoTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), getVector().x, getVector().y);
			}else if(childType == 2){
				batch.draw(GameScreen.getKidThreeTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), getVector().x, getVector().y);
			}else if(childType == 3){
				batch.draw(GameScreen.getKidFourTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), getVector().x, getVector().y);
			}
		}
	}
	
	//----------private functions---------
	
	private void move(){
		if(Logic.isGameStarted() == true){
			if(isLife(Logic.getExplosions()) == true && isInGame() == true){
				if(distanceToBeTired > 0){
					present = findPresent(Logic.getPresents());
					if(present != null){
						runAwayFromPresent(present);
					}else if(isSantaNear(Logic.getSanta()) == true){
						runToSanta(Logic.getSanta());
					}else if(distanceToChangeDirection < 0){
						stop();
						randomDistance();
						randomDirection();
						setSpeed(2);
					}else{
						setSpeed(2);
					}
					if(collisionDetector(Logic.getMyTiledMap().getTiledMap()) != true){
						moving();
					}else{
						stop();
						randomDistance();
						randomDirection();
					}
					distanceToChangeDirection = distanceToChangeDirection - getSpeed();
					if(getSpeed() == 4){
						distanceToBeTired = distanceToBeTired - getSpeed();
					}
				}else{
					if(sleepingTime == 0){
						sleepTime = (random.nextInt(3)+2)*30;
						sleepingTime = System.nanoTime()/100000000;
					}else if(System.nanoTime()/100000000 - sleepingTime > sleepTime){
						distanceToBeTired = 1200;
						sleepingTime = 0;
					}
				}
			}else if(isInGame() == true){
				Logic.getMyStage().getBloodStage().addActor(new Blood(getVector().x, getVector().y));
				setInGame(false);
				stop();
				leaveBonus();
				Logic.setPlayerScore(Logic.getPlayerScore() + 200);
				//System.out.println(Logic.getPlayerScore());
				synchronized(Logic.getPresents()) {
					Logic.getChildren().remove(this);
				}
				
				
				
			}
		}
		
	}
	
	private void randomDistance(){
		distanceToChangeDirection = (random.nextInt(4)+5)*Constans.HEIGHT_OF_TILE;
	}
	
	private void randomDirection(){
		switch(random.nextInt(4)+1){
		case Constans.RIGHT:
			setMoveDirection(Constans.RIGHT);
			break;
		case Constans.LEFT:
			setMoveDirection(Constans.LEFT);
			break;
		case Constans.UP:
			setMoveDirection(Constans.UP);
			break;
		case Constans.DOWN:
			setMoveDirection(Constans.DOWN);
			break;
		}
	}
	
	private Rectangle kidRectangle;
	private Rectangle explosionRectangle;
	
	private boolean isLife(ArrayList<Explosion> explosions){
		if(kidRectangle == null || explosionRectangle == null){
			kidRectangle = new Rectangle();
			explosionRectangle = new Rectangle();
		}
		kidRectangle.set(this.getVector().x+16, this.getVector().y + 16, 32, 32);
		for(int i = 0; i < explosions.size(); i++){
			if(explosions.get(i).isExplosing() == true){
				explosionRectangle.set(explosions.get(i).getExplosionImage().getX(), explosions.get(i).getExplosionImage().getY(), explosions.get(i).getExplosionImage().getWidth(), explosions.get(i).getExplosionImage().getHeight());
				if(explosionRectangle.overlaps(kidRectangle) == true){
					return false;
				}
			}
		}
		return true;
	}
	
	private void runAwayFromPresent(Present present){
		if(present.getVector().x >= getVector().x){
			setMoveDirection(Constans.LEFT);
		}else if(present.getVector().x < getVector().x){
			setMoveDirection(Constans.RIGHT);
		}else if(present.getVector().y >= getVector().y){
			setMoveDirection(Constans.DOWN);
		}else if(present.getVector().y < getVector().y){
			setMoveDirection(Constans.UP);
		}
		setSpeed(4);
	}
	
	private Present findPresent(ArrayList<Present> presents){
		if(presents.size() > 0){
			for(int i = 0; i < presents.size(); i++){
				if(presents.get(i).isBonus() == false){
					if((getVector().x+32) +Constans.VISIBILITY >= (presents.get(i).getVector().x+32) && (getVector().x+32) - Constans.VISIBILITY <= (presents.get(i).getVector().x+32) &&
							(getVector().y+32) +Constans.VISIBILITY >= (presents.get(i).getVector().y+32) && (getVector().y+32) - Constans.VISIBILITY <= (presents.get(i).getVector().y+32)){
						return presents.get(i);
					}
				}
			}
		}
		return null;
	}
	
	private boolean isSantaNear(Santa santa){
		if((getVector().x+32) +Constans.VISIBILITY >= santa.getVector().x && (getVector().x+32) - Constans.VISIBILITY <= santa.getVector().x &&
				(getVector().y+32) +Constans.VISIBILITY >= santa.getVector().y && (getVector().y+32) - Constans.VISIBILITY <= santa.getVector().y){
			return true;
		}
		return false;
	}
	
	private void runToSanta(Santa santa){
		if(santa.getVector().x > getVector().x + 5 ){
			setMoveDirection(Constans.RIGHT);
		}else if(santa.getVector().y >= getVector().y + 5){
			setMoveDirection(Constans.UP);
		}else if(santa.getVector().x < getVector().x - 5){
			setMoveDirection(Constans.LEFT);
		}else if(santa.getVector().y < getVector().y - 5){
			setMoveDirection(Constans.DOWN);
		}
		setSpeed(4);
	}
	
	private void leaveBonus(){
		if(random.nextInt(3) == 1){
			System.out.println("Zostawiam prezent!!!");
			if(Logic.getSanta().getPresentType() == 1){
				present = new Present(this.getVector().x-2, this.getVector().y+8, 2, true);
				Logic.getMyStage().getPresentsStage().addActor(present);
				Logic.getPresents().add(present);
			}else if(Logic.getSanta().getPresentType() == 2){
				present = new Present(this.getVector().x-2, this.getVector().y+8, 3, true);
				Logic.getMyStage().getPresentsStage().addActor(present);
				Logic.getPresents().add(present);
			}else if(Logic.getSanta().getPresentType() >= 3){
				present = new Present(this.getVector().x-2, this.getVector().y+8, 4, true);
				Logic.getMyStage().getPresentsStage().addActor(present);
				Logic.getPresents().add(present);
			}
			
			
		}
	}

	private void mySleep(int fps) {
	    if(fps>0){
	      diff = System.currentTimeMillis() - start;
	      long targetDelay = 1000/fps;
	      if (diff < targetDelay) {
	        try{
	            Thread.sleep(targetDelay - diff);
	          } catch (InterruptedException e) {}
	        }   
	      start = System.currentTimeMillis();
	    }
	}

}
