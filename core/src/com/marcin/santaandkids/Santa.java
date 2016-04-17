package com.marcin.santaandkids;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Santa extends Character {
	
	private int numberOfPresents = 5;
	
	private Rectangle santaRectangle;
	private Rectangle presentRectangle;
	
	private int presentType;
	
	private int condition;
	
	public Santa(){}
	
	public Santa(int x, int y, int speed, boolean inGame) {
		this.setVector(x, y);
		this.setSpeed(speed);
		this.setInGame(inGame);
		this.presentType = 1;

		santaRectangle = new Rectangle(getVector().x, getVector().y, 64, 64);
		condition = 512;

	}
	
	public void setPresentType(int type){
		this.presentType = type;
	}
	public void setNumberOfPresents(int numberOfPresents){
		this.numberOfPresents = numberOfPresents;
	}
	public void setCondition(int condition){
			this.condition = condition;
	}
	public int getNumberOfPresents(){
		return this.numberOfPresents;
	}
	public int getPresentType(){
		return this.presentType;
	}
	public int getCondition(){
		return this.condition;
	}

	public Present leavePresent(){
		return new Present(this.getVector().x, this.getVector().y, getPresentType(), false);
	}
	
	public void getUpBonus(ArrayList<Present> presents){
		for(int i = 0; i < presents.size(); i++){
			if(presents.get(i).isBonus() == true){
				santaRectangle.set(getVector().x+16, getVector().y+16, 32, 32);
				presentRectangle = new Rectangle(presents.get(i).getVector().x+16, presents.get(i).getVector().y+16, 32, 32);
				if(santaRectangle.overlaps(presentRectangle) == true){
					if(getPresentType() < presents.get(i).getRange()){
						setPresentType(presents.get(i).getRange());
					}
					setNumberOfPresents(getNumberOfPresents()+1);
					presents.get(i).setInGame(false);
					presents.remove(presents.get(i));
					System.out.println("Podnioslem!");
					Logic.setNotification("Bonus: damage +5");
				}
			}
		}
		
	}
	
	public void draw(Batch batch, float parentAlpha){
		if(isInGame() == true){
			if(isMovingToDown() == true ){
				batch.draw(GameScreen.getSantaTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), this.getVector().x, this.getVector().y);
			}else if(isMovingToLeft() == true){
				batch.draw(GameScreen.getSantaTexture().get(Constans.TEXTURE_OF_LEFT_SIDE), this.getVector().x, this.getVector().y);
			}else if(isMovingToRight() == true){
				batch.draw(GameScreen.getSantaTexture().get(Constans.TEXTURE_OF_RIGHT_SIDE), this.getVector().x, this.getVector().y);
			}else if(isMovingToUp() == true){
				batch.draw(GameScreen.getSantaTexture().get(Constans.TEXTURE_OF_BACK_SIDE), this.getVector().x, this.getVector().y);
			}else{
				batch.draw(GameScreen.getSantaTexture().get(Constans.TEXTURE_OF_FRONT_SIDE), this.getVector().x, this.getVector().y);
			}
		}
	}
	
	public void moving(MyTiledMap map){
		if(isInGame() == true){
			if(isMovingToDown() == true){
				if(getSpeed() == Constans.SPEED_OF_SANTA_RUN && getCondition() > 4){
					setCondition(getCondition() - Constans.SPEED_OF_SANTA_RUN);
				}else if(getCondition() < 512){
					if(getSpeed() != Constans.SPEED_OF_SANTA_NORMAL){
						setSpeed(Constans.SPEED_OF_SANTA_NORMAL);
					}
					setCondition(getCondition() +1);
				}
				getVector().y = getVector().y - getSpeed();
			}else if(isMovingToLeft() == true){
				if(getSpeed() == Constans.SPEED_OF_SANTA_RUN && getCondition() > 4){
					setCondition(getCondition() - Constans.SPEED_OF_SANTA_RUN);
				}else if(getCondition() < 512){
					if(getSpeed() != Constans.SPEED_OF_SANTA_NORMAL){
						setSpeed(Constans.SPEED_OF_SANTA_NORMAL);
					}
					setCondition(getCondition() +1);
				}
				getVector().x = getVector().x - getSpeed();
			}else if(isMovingToRight() == true){
				if(getSpeed() == Constans.SPEED_OF_SANTA_RUN && getCondition() > 4){
					setCondition(getCondition() - Constans.SPEED_OF_SANTA_RUN);
				}else if(getCondition() < 512){
					if(getSpeed() != Constans.SPEED_OF_SANTA_NORMAL){
						setSpeed(Constans.SPEED_OF_SANTA_NORMAL);
					}
					setCondition(getCondition() +1);
				}
				getVector().x = getVector().x + getSpeed();
			}else if(isMovingToUp() == true){
				if(getSpeed() == Constans.SPEED_OF_SANTA_RUN && getCondition() > 4){
					setCondition(getCondition() - Constans.SPEED_OF_SANTA_RUN);
				}else if(getCondition() < 512){
					if(getSpeed() != Constans.SPEED_OF_SANTA_NORMAL){
						setSpeed(Constans.SPEED_OF_SANTA_NORMAL);
					}
					setCondition(getCondition() +1);
				}
				getVector().y = getVector().y + getSpeed();
			}else{
				if(getCondition() <= 510)
				setCondition(getCondition() + 2);
			}
			
		}
	}
	
	public boolean isLife(ArrayList<Explosion> explosions){
		santaRectangle.set(this.getVector().x+16, this.getVector().y + 16, 32, 32);
		Rectangle explosionRectangle = new Rectangle();
		for(int i = 0; i < explosions.size(); i++){
			if(explosions.get(i).isExplosing() == true){
				explosionRectangle.set(explosions.get(i).getExplosionImage().getX(), explosions.get(i).getExplosionImage().getY(), explosions.get(i).getExplosionImage().getWidth(), explosions.get(i).getExplosionImage().getHeight());
				if(explosionRectangle.overlaps(santaRectangle) == true){
					return false;
				}
			}
		}
		return true;
	}


	
	
	
	

}
