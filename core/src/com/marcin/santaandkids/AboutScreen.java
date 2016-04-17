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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;





public class AboutScreen extends Table {
	
	private final static String text = "Hi! \nMy name is Marcin and this is my first desktop application written in JAVA with the use of libGDX.Idea for the game I took from classes at my university. It is a very simple game - you are Santa Clause and you have to give presents to children but children can not catch you, so you can not be too close the children. That is all. Simple, isn't ? So let's go and good luck :)";
	
	private final static int WIDTH = 600;
	private final static int HEIGHT = 480;
	
	private final static int HEIGHT_OF_BUTTON = 50;
	private final static int WIDTH_OF_BUTTON = 200;
	
	Skin skin;
	TextButton backButton;
	TextArea textArea;
	AboutScreen aboutScreen;

	
	public AboutScreen(){
		aboutScreen = this;
		this.setSize(WIDTH, HEIGHT);
		this.bottom();
		this.padBottom(40);
		createSkin();
		createBackground();
		addTextArea();
		this.row();
		addButton();
	}
	
	private void createSkin(){
		skin = new Skin();
		skin.add("background", createBackgroundTexture());
		skin.add("font", createFont());
		skin.add("default", createButtonStyle());
		skin.add("default", createFieldAreaStyle());
	}
	

	public void addTextArea(){
		textArea = new TextArea(text, skin);
		textArea.setPrefRows(11);
		this.add(textArea).width(WIDTH-50);
	}
	
	public void addButton(){
		createButtons();
		this.add(backButton);
	}
	
	private void createButtons(){
		backButton = createButton("Back", skin, Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8, 100 );
		backButton.addListener(new ClickListener(){
			@Override
        	public void clicked(InputEvent event, float x, float y){
        		MenuScreen.closeAboutTable();
        		MenuScreen.undisableButton();
        	}
		});
	}
	
	private TextButton createButton(String text, Skin skin, int x, int y){
		TextButton button = new TextButton(text, skin);
		button.setPosition(x, y);
		button.setHeight(HEIGHT_OF_BUTTON);
		button.setWidth(WIDTH_OF_BUTTON);
		return button;
	}
	
	private void createBackground(){
		this.background(skin.newDrawable("background", Color.BLUE));
	}
	
	//-----------Skin's creators-------------
	
	private TextFieldStyle createFieldAreaStyle(){
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = skin.getFont("font");
		textFieldStyle.fontColor = Color.WHITE;
		
		return textFieldStyle;
	}
	
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
	

	
	

}
