package com.marcin.santaandkids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SettingsScreen extends Table{
	
	private final static int WIDTH = 600;
	private final static int HEIGHT = 480;
	
	private final static int HEIGHT_OF_BUTTON = 50;
	private final static int WIDTH_OF_BUTTON = 200;
	
	SettingsScreen settingsScreen;
	Skin skin;
	
	TextButton backButton;
	TextButton confirmButton;
	
	public SettingsScreen(){
		settingsScreen = this;
		this.setSize(WIDTH, HEIGHT);
		this.bottom();
		this.padBottom(40);
		createSkin();
		createBackground();
		addButtons();
		
	}
	
	private void addButtons(){
		createBackButton();
		createConfirmButton();
	}
	
	private void createBackground(){
		this.background(skin.newDrawable("background", Color.BLUE));
	}
	
	
	private void createSkin(){
		skin = new Skin();
		skin.add("background", createBackgroundTexture());
		skin.add("font", createFont());
		skin.add("default", createButtonStyle());
	}
	
	//-------Skin's creators---------
	private Texture createBackgroundTexture(){
		Texture texture = new Texture(new FileHandle("data/aboutScreen.png"));
		
		return texture;
	}
	
	private TextButtonStyle createButtonStyle(){
		//Create a texture
		Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("buttonBackground",new Texture("data/button.png"));
		
		//Create a button style
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("buttonBackground");
		textButtonStyle.up = skin.newDrawable("buttonBackground", Color.NAVY);
		textButtonStyle.down = skin.newDrawable("buttonBackground", Color.NAVY);
		textButtonStyle.over = skin.newDrawable("buttonBackground", Color.BLUE);
		textButtonStyle.font = skin.getFont("font");
		
		return textButtonStyle;
	}
	
	private BitmapFont createFont(){
		FileHandle fh = new FileHandle("data/comicFont.fnt");
		BitmapFont font = new BitmapFont(fh);
		font.setColor(Color.WHITE);
		return font;
	}
	
	
	//-------Buttons creators--------
	private void createBackButton(){
		backButton = new TextButton("Back", skin);
		backButton.setSize(WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON);
		backButton.addListener(new ClickListener(){
			@Override
        	public void clicked(InputEvent event, float x, float y){
        		MenuScreen.closeSettingsScreen();
        		MenuScreen.undisableButton();
        	}
		});
		this.add(backButton).padRight(50);
	}
	
	private void createConfirmButton(){
		confirmButton = new TextButton("Confirm", skin);
		confirmButton.setSize(WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON);
		confirmButton.addListener(new ClickListener(){
			@Override
        	public void clicked(InputEvent event, float x, float y){
				System.out.println("Potwierdzam!");
        	}
		});
		this.add(confirmButton);
	}

}
