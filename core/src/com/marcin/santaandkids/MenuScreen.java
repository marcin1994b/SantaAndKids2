package com.marcin.santaandkids;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class MenuScreen implements Screen{
	
	
	private final static int TMP = 80;
	
	private Stage stage = new Stage();
	
	public static SantaAndKids game;
	private Texture splashTexture;
	private Sprite splashSprite;
	private SpriteBatch batch;
	private Skin skin;
	private TweenManager tweenManager;
	Skin windowSkin;
	
	private static TextButton newGameButton;
	private static TextButton settingsButton;
	private static TextButton aboutButton;
	private static TextButton exitButton;
	
	static AboutScreen aboutScreen;
	static SettingsScreen settingsScreen;
	
	private TweenCallback cb = new TweenCallback() {
		@Override
		public void onEvent(int tmp, BaseTween<?> arg1) {
			switch(tmp){
			case TweenCallback.COMPLETE:
				addButton();
				break;
			}
		}
	};

	// constructor
	public MenuScreen(SantaAndKids game) {
		this.game = game;
	}

	@Override
	public void show() {
		createTweenManager();
		showMenuScreen();

		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		splashSprite.draw(batch);
		batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 2 / 30f));
		stage.draw();
	}
	
	//-----------------------------------------------------
	
	private void createTweenManager(){
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
	}
	
	
	private void showMenuScreen(){
		splashTexture = new Texture("data/menuScreen.png");
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		splashSprite = new Sprite(splashTexture);
		
		Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splashSprite, SpriteAccessor.ALPHA, 2).target(1).delay(1).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);
	}
	
	
	private void createButtonStyle(){
		//Create a font
		FileHandle fh = new FileHandle("data/comicFont.fnt");
		BitmapFont font = new BitmapFont(fh);
		font.setColor(Color.WHITE);
		skin = new Skin();
		skin.add("default", font);
		  
		//Create a texture
		Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background",new Texture("data/button.png"));
		
		//Create a button style
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background");
		textButtonStyle.up = skin.newDrawable("background", Color.NAVY);
		textButtonStyle.down = skin.newDrawable("background", Color.NAVY);
		textButtonStyle.over = skin.newDrawable("background", Color.BLUE);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
	}
	
	
	private void addButton(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        Tween.registerAccessor(TextButton.class, new StageAccessor());
 
        createButtonStyle();

        newGameButton = createButton("New Game", skin);
        newGameButton.addListener(new ClickListener(){
        	@Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("New Game!");
                MenuScreen.game.setScreen(new GameScreen(MenuScreen.game));
                
            };
        });
        Tween.to(newGameButton, StageAccessor.POSITION_Y, 1.0f).target(Gdx.graphics.getHeight()/2 - TMP).start(tweenManager);
        stage.addActor(newGameButton);

        settingsButton = createButton("Settings", skin);
        settingsButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		System.out.println("Settings!");
        		MenuScreen.settingsScreen = new SettingsScreen();
        		MenuScreen.settingsScreen.setPosition(Gdx.graphics.getWidth()/8 ,Gdx.graphics.getHeight()/2 - 270);
        		disableButton();
        		stage.addActor(MenuScreen.settingsScreen);
        	}
        });
        Tween.to(settingsButton, StageAccessor.POSITION_Y, 1.0f).target(Gdx.graphics.getHeight()/2 - (Constans.HEIGHT_OF_BUTTON + Constans.SPACE_BETWEEN_BUTTONS) - TMP).delay(1).start(tweenManager);
        stage.addActor(settingsButton);
        
        aboutButton = createButton("About", skin);
        aboutButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		MenuScreen.aboutScreen = new AboutScreen();
        		MenuScreen.aboutScreen.setPosition(Gdx.graphics.getWidth()/8 ,Gdx.graphics.getHeight()/2 - 270);
        		disableButton();
        		stage.addActor(MenuScreen.aboutScreen);
        	}
        });
        Tween.to(aboutButton, StageAccessor.POSITION_Y, 1.0f).target(Gdx.graphics.getHeight()/2 - 2*(Constans.HEIGHT_OF_BUTTON + Constans.SPACE_BETWEEN_BUTTONS) - TMP).delay(2).start(tweenManager);
        stage.addActor(aboutButton);
        
        exitButton = createButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		System.out.println("Exit!");
        		Gdx.app.exit();
        	}
        });
        Tween.to(exitButton, StageAccessor.POSITION_Y, 1.0f).target(Gdx.graphics.getHeight()/2 - 3*(Constans.HEIGHT_OF_BUTTON + Constans.SPACE_BETWEEN_BUTTONS) - TMP).delay(3).start(tweenManager);
        stage.addActor(exitButton);
	}
	
	
	
	private void disableButton(){
		newGameButton.setVisible(false);
		settingsButton.setVisible(false);
		aboutButton.setVisible(false);
		exitButton.setVisible(false);
	}
	
	public static void undisableButton(){
		newGameButton.setVisible(true);
		settingsButton.setVisible(true);
		aboutButton.setVisible(true);
		exitButton.setVisible(true);
	}
	
	private TextButton createButton(String text, Skin skin){
		TextButton button = new TextButton(text, skin);
		button.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 ,-100);
		button.setHeight(Constans.HEIGHT_OF_BUTTON);
		button.setWidth(Constans.WIDTH_OF_BUTTON);
		return button;
	}
	
	public static void closeAboutTable(){
		aboutScreen.remove();
	}
	
	public static void closeSettingsScreen(){
		settingsScreen.remove();
	}
	
	//-----------------------------------------------------------------------

	@Override
	public void resize(int width, int height) {

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

}
