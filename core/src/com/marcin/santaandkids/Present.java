package com.marcin.santaandkids;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Present extends Actor{
	
	private Vector2 vector;
	
	private boolean explosing;
	
	private float startTime;
	
	private boolean inGame;
	
	private boolean bonus;
	
	private int range;
	
	public Present(float x, float y, int range, boolean bonus){
		this.vector = new Vector2();
		this.vector.x = x;
		this.vector.y = y;
		this.startTime = System.nanoTime()/100000000;
		this.inGame = true;
		this.bonus = bonus;
		this.range = range;
		
	}
	
	public void setVector(float x, float y){
		this.vector.x = x;
		this.vector.y = y;
	}
	public void setRange(int range){
		this.range = range;
	}
	public void setInGame(boolean inGame){
		this.inGame = inGame;
	}
	
	public Vector2 getVector(){
		return this.vector;
	}
	public boolean isExplosing(){
		return this.explosing;
	}
	public boolean isBonus(){
		return this.bonus;
	}
	public int getRange(){
		return this.range;
	}
	@Override
	public void draw(Batch batch, float parentAlpha){
		if(this.inGame == true){
			if(bonus == false){
				if((System.nanoTime()/100000000) - startTime < 20){
					if(range == 1){
						batch.draw(GameScreen.getPresentsTexture().get(0), vector.x, vector.y);
					}else if(range == 2){
						batch.draw(GameScreen.getPresentsTexture().get(1), vector.x, vector.y);
					}else if(range == 3){
						batch.draw(GameScreen.getPresentsTexture().get(2), vector.x, vector.y);
					}else if(range == 4){
						batch.draw(GameScreen.getPresentsTexture().get(3), vector.x, vector.y);
					}
				}else{
					inGame = false;
					Explosion exp = new Explosion(getVector().x, getVector().y, 64+64*range);
					Logic.getPresents().remove(this);
					Logic.getExplosions().add(exp);
					Logic.getMyStage().getExplosionStage().addActor(exp);
				}
			}else if(bonus == true){
				if(((System.nanoTime()/100000000) - startTime) % 8 <= 4){
					if(range == 1){
						batch.draw(GameScreen.getPresentsTexture().get(0), vector.x, vector.y);
					}else if(range == 2){
						batch.draw(GameScreen.getPresentsTexture().get(1), vector.x, vector.y);
					}else if(range == 3){
						batch.draw(GameScreen.getPresentsTexture().get(2), vector.x, vector.y);
					}else if(range == 4){
						batch.draw(GameScreen.getPresentsTexture().get(3), vector.x, vector.y);
					}
				}
			}
		}
	}
	

}
