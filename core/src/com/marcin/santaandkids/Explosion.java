package com.marcin.santaandkids;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Explosion extends Actor{
	
	private Vector2 vector;
	
	private boolean explosing;
	
	private float endExplosionTime;
	private float explosionRange;
	
	private Image explosionImage;
	
	public Explosion(float x, float y, float explosionRange){
		this.vector = new Vector2();
		this.vector.x = x;
		this.vector.y = y;
		this.explosionRange = explosionRange;
		this.explosing = true;
		this.explosionImage = new Image(GameScreen.getExplosionTexture());
		this.explosionImage.setSize(0, 0);
	}
	
	public Image getExplosionImage(){
		return explosionImage;
	}
	public boolean isExplosing(){
		return explosing;
	}
	
	public void draw(Batch batch, float parentAlpha){
		explosing = true;
		if(explosionImage.getImageHeight() < explosionRange){
			explosionImage.setSize(explosionImage.getImageWidth()+12, explosionImage.getImageHeight()+12);
			explosionImage.setPosition(this.vector.x-(explosionImage.getWidth()/2)+32, this.vector.y-(explosionImage.getHeight()/2)+16);
			explosionImage.draw(batch, parentAlpha);
		}else if(endExplosionTime == 0){
			endExplosionTime = System.nanoTime()/100000000;
		}
		if(endExplosionTime == 0 || (System.nanoTime()/100000000) - endExplosionTime < 5){
			explosionImage.draw(batch, parentAlpha);
		}else{
			explosing = false;
			Logic.getPresents().remove(this);
			this.remove();
			
		}
	}

}
