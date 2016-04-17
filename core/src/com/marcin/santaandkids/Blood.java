package com.marcin.santaandkids;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Blood extends Actor{
	
	private Vector2 vector;
	
	public Blood(float x, float y){
		this.vector = new Vector2();
		this.vector.x = x;
		this.vector.y = y;
	}
	
	public void draw(Batch batch, float parentAlpha){
		batch.draw(GameScreen.getBloodTexture(), this.vector.x, this.vector.y);
	}
	

}
