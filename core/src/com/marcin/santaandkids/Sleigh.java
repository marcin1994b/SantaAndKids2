package com.marcin.santaandkids;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Sleigh extends Character{
	
	
	public Sleigh(int x, int y, Texture texture){
		this.setVector(x, y);
	}
	
	public void draw(Batch batch, float parentAlpha){
		batch.draw(GameScreen.getSleighTexture(), getVector().x, getVector().y);
	}

}
