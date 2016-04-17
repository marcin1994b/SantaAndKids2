package com.marcin.santaandkids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyStage{
	
	private static Stage santaStage;
	private static Stage presentStage;
	private static Stage childrenStage;
	private static Stage bloodStage;
	private static Stage explosionStage;
	
	public MyStage(int width, int height){
		santaStage = new Stage(new FitViewport(width, height, new OrthographicCamera()));
		presentStage = new Stage(new FitViewport(width, height, santaStage.getCamera()));
		childrenStage = new Stage(new FitViewport(width, height, santaStage.getCamera()));
		bloodStage = new Stage(new FitViewport(width, height, santaStage.getCamera()));
		explosionStage = new Stage(new FitViewport(width, height, santaStage.getCamera()));
	}
	
	public Stage getSantaStage(){
		return santaStage;
	}
	public Stage getPresentsStage(){
		return presentStage;
	}
	public Stage getChildrenStage(){
		return childrenStage;
	}
	public Stage getBloodStage(){
		return bloodStage;
	}
	public Stage getExplosionStage(){
		return explosionStage;
	}
	
	public void update(){
		santaStage.getCamera().update();
	}
	
	public void draw(){
		bloodStage.act(Math.min(Gdx.graphics.getDeltaTime(), 2 / 30f));
		bloodStage.draw();
		
		santaStage.act(Math.min(Gdx.graphics.getDeltaTime(), 2 / 30f));
		santaStage.draw();
		
		childrenStage.act(Math.min(Gdx.graphics.getDeltaTime(), 2 / 30f));
		childrenStage.draw();

		presentStage.act(Math.min(Gdx.graphics.getDeltaTime(), 2 / 30f));
		presentStage.draw();
		
		explosionStage.act(Math.min(Gdx.graphics.getDeltaTime(), 2 / 30f));
		explosionStage.draw();
	}
	

}
