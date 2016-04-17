package com.marcin.santaandkids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class SplashScreen implements Screen{
	
	Texture splashTexture;
	Sprite splashSprite;
	SpriteBatch batch;
	SantaAndKids game;
	
	TweenManager tweenManager;
	
	int splashScreenCounter = 0;
	
	TweenCallback cb = new TweenCallback() {
		
		@Override
		public void onEvent(int tmp, BaseTween<?> arg1) {
			switch(tmp){
			case TweenCallback.COMPLETE:
				tweenComplete();
				break;
			case TweenCallback.END:
				setMenuScreen();
				break;
			}
		}
	};
	
	public SplashScreen(SantaAndKids game){
		this.game = game;
	}
	

	@Override
	public void show() {

		createTweenManager();
		showSplashScreens();

		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		splashSprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void createTweenManager(){
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
	}
	
	private void showSplashScreens(){
		
		splashTexture = new Texture("data/splashScreen.png");
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		splashSprite = new Sprite(splashTexture);
		
		Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splashSprite, SpriteAccessor.ALPHA, 2).target(1).delay(1).start(tweenManager);
		Tween.to(splashSprite, SpriteAccessor.ALPHA, 2).target(0).delay(3).setCallback(cb).setCallbackTriggers(TweenCallback.END).start(tweenManager);
	}
	
	private void tweenComplete(){
		setNewTexture("data/splashScreen2.png");
		Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splashSprite, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
		Tween.to(splashSprite, SpriteAccessor.ALPHA, 2).setCallback(cb).setCallbackTriggers(TweenCallback.END).target(0).delay(2).start(tweenManager);
	}
	
	private void setNewTexture(String string){
		splashTexture = new Texture(string);
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		splashSprite.setTexture(splashTexture);
	}
	
	private void setMenuScreen(){
		game.setScreen(new MenuScreen(game));
	}

}

