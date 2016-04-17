package com.marcin.santaandkids;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class GameScreen implements Screen{

	private SantaAndKids game;

	private TiledMapRenderer tiledMapRender;
	private Logic logic;
	
	private SpriteBatch batch;
	
	private static ArrayList<Texture> presentsTexture;
	private static ArrayList<Texture> numbersTexture;
	private static ArrayList<Texture> kidOneTexture;
	private static ArrayList<Texture> kidTwoTexture;
	private static ArrayList<Texture> kidThreeTexture;
	private static ArrayList<Texture> kidFourTexture;
	private static ArrayList<Texture> santaTexture;
	private static ArrayList<Texture> gameInfoText;
	
	private static Texture explosionTexture;
	private static Texture bloodTexture;
	private static Texture sleighTexture;
	
	private Texture presentBanner;
	private Texture childrenBanner;
	
	private Texture speedProgressBar;
	private Texture speedHideProgressBar;
	private Image speedHideProgressBarImg;
	
	private BitmapFont font;
	
	private float startTime = -1;
	private boolean isStarting = true;;
	

	public GameScreen(SantaAndKids game){
		this.game = game;
		loadTextures();
		loadFont();
		createLogic();
        createTiledMapRenderer();
        batch = new SpriteBatch();
        speedHideProgressBarImg = new Image(getSpeedHideProgressBar());
	}
	
	//--------Getters----------
	public static ArrayList<Texture> getPresentsTexture(){
		return GameScreen.presentsTexture;
	}
	public static ArrayList<Texture> getNumbersTexture(){
		return GameScreen.numbersTexture;
	}
	public static ArrayList<Texture> getKidOneTexture(){
		return GameScreen.kidOneTexture;
	}
	public static ArrayList<Texture> getKidTwoTexture(){
		return GameScreen.kidTwoTexture;
	}
	public static ArrayList<Texture> getKidThreeTexture(){
		return GameScreen.kidThreeTexture;
	}
	public static ArrayList<Texture> getKidFourTexture(){
		return GameScreen.kidFourTexture;
	}
	public static ArrayList<Texture> getSantaTexture(){
		return GameScreen.santaTexture;
	}
	public static ArrayList<Texture> getGameInfoText(){
		return GameScreen.gameInfoText;
	}
	public static Texture getExplosionTexture(){
		return GameScreen.explosionTexture;
	}
	public static Texture getBloodTexture(){
		return GameScreen.bloodTexture;	
	}
	public static Texture getSleighTexture(){
		return GameScreen.sleighTexture;
	}
	public Texture getPresentBanner(){
		return this.presentBanner;
	}
	public Texture getChildrenBanner(){
		return this.childrenBanner;
	}
	public Texture getSpeedProgressBar(){
		return this.speedProgressBar;
	}
	public Texture getSpeedHideProgressBar(){
		return this.speedHideProgressBar;
	}

	//-----------------------------------
	private void createLogic(){
		logic = new Logic();
	}
	
	private void createTiledMapRenderer(){
		tiledMapRender = new OrthogonalTiledMapRenderer(Logic.getMyTiledMap().getTiledMap());
	}
	
	private void loadTextures(){
		loadPresents();
		loadNumbers();
		loadKids();
		loadSanta();
		loadExplosionTexture();
		loadBloodTexture();
		loadSleighTexture();
		loadBannersTextures();
		loadGameInfoText();
		loadSpeedProgressBar();
	}




	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		drawCamera();
		drawTiledMap();
		
		logic.update();
		
		Logic.getMyStage().draw();
		
		batch.begin();
		drawInfoBanners();
		drawProgressBar();
		if(isStarting == true){
			countingDownToStart();
		}else if(Logic.getSanta().isInGame() == false){
			drawGameOverText();
			System.out.println("Game time: " + (System.nanoTime()/100000000 - startTime));
		}else if(Logic.getChildren().size() == 0){
			drawWinText();
			System.out.println("Game time: " + (System.nanoTime()/100000000 - startTime));
		}
		batch.end();
	}
	private void drawCamera(){

		Logic.getMyStage().getSantaStage().getCamera().update();
		tiledMapRender.setView((OrthographicCamera)Logic.getMyStage().getSantaStage().getCamera());
		
	}
	
	private void drawTiledMap(){
		tiledMapRender.render();
	}
	
	private void drawInfoBanners(){
		batch.draw(getPresentBanner(), 50, 520, 225, 75);
		if(Logic.getSanta().getPresentType() == 1){
			batch.draw(getPresentsTexture().get(0), 47, 530, 84, 84);
		}else if(Logic.getSanta().getPresentType() == 2){
			batch.draw(getPresentsTexture().get(1), 47, 530, 84, 84);
		}else if(Logic.getSanta().getPresentType() == 3){
			batch.draw(getPresentsTexture().get(2), 47, 530, 84, 84);
		}else if(Logic.getSanta().getPresentType() == 4){
			batch.draw(getPresentsTexture().get(3), 47, 530, 84, 84);
		}
		font.draw(batch, Integer.toString(Logic.getSanta().getNumberOfPresents()), 170, 567);
		batch.draw(getChildrenBanner(), 525, 520, 225, 75);
		font.draw(batch, Integer.toString(Logic.getChildren().size()), 610, 567);
	}

	private void countingDownToStart(){
		if(startTime == -1){
			startTime = System.nanoTime()/100000000;
			isStarting = true;
			
		}
		
		float time = (System.nanoTime()/100000000) - startTime;
		
		if(time > 0 && time < 11){
			batch.draw(getNumbersTexture().get(2), (Gdx.graphics.getWidth()/2)-200, (Gdx.graphics.getHeight()/2)-200, 400, 500);
		}else if(time > 11 && time < 21){
			batch.draw(getNumbersTexture().get(1), (Gdx.graphics.getWidth()/2)-200, (Gdx.graphics.getHeight()/2)-200, 400, 500);
		}else if(time > 21 && time < 31){
			batch.draw(getNumbersTexture().get(0), (Gdx.graphics.getWidth()/2)-200, (Gdx.graphics.getHeight()/2)-200, 400, 500);
		}else if(time > 31 && time < 41){
			batch.draw(getNumbersTexture().get(3), (Gdx.graphics.getWidth()/2)-200, (Gdx.graphics.getHeight()/2)-230, 400, 500);
		}else if(time > 42){
			isStarting = false;
			Logic.setGameStarted(true);
		}
	}
	
	private void drawWinText(){
		batch.draw(getGameInfoText().get(0), (Gdx.graphics.getWidth()/2)-400, (Gdx.graphics.getHeight()/2)-300);
	}
	
	private void drawGameOverText(){
		batch.draw(getGameInfoText().get(1), (Gdx.graphics.getWidth()/2)-400, (Gdx.graphics.getHeight()/2)-300);
	}
	private void drawProgressBar(){
		batch.draw(getSpeedProgressBar(), (Gdx.graphics.getWidth()/2)-300, 20, getSpeedProgressBar().getWidth(), 40);
		speedHideProgressBarImg.setPosition(117, 33);
		speedHideProgressBarImg.setHeight(14);
		speedHideProgressBarImg.setWidth(updateSpeedHideProgressBar());
		speedHideProgressBarImg.draw(batch, 2);
	}
	private float updateSpeedHideProgressBar(){
		//System.out.println((((Logic.getSanta().getCondition()*100)/512)*getSpeedHideProgressBar().getWidth())/100);
		return (((Logic.getSanta().getCondition()*100)/512)*getSpeedHideProgressBar().getWidth())/100 ;
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
	
	//--------Load Textures-------
	private void loadPresents(){
		presentsTexture = new ArrayList<>();
		presentsTexture.add(new Texture(Gdx.files.internal("data/presents/present1.png")));
		presentsTexture.add(new Texture(Gdx.files.internal("data/presents/present2.png")));
		presentsTexture.add(new Texture(Gdx.files.internal("data/presents/present3.png")));
		presentsTexture.add(new Texture(Gdx.files.internal("data/presents/present4.png")));
	}
	
	private void loadNumbers(){
		numbersTexture = new ArrayList<>();
		numbersTexture.add(new Texture(Gdx.files.internal("data/numbers/1.png")));
		numbersTexture.add(new Texture(Gdx.files.internal("data/numbers/2.png")));
		numbersTexture.add(new Texture(Gdx.files.internal("data/numbers/3.png")));
		numbersTexture.add(new Texture(Gdx.files.internal("data/numbers/go.png")));
	}
	
	private void loadKids(){
		loadKid1();
		loadKid2();
		loadKid3();
		loadKid4();
	}
	
	private void loadKid1(){
		kidOneTexture = new ArrayList<>();
		kidOneTexture.add(new Texture(Gdx.files.internal("data/kid1/KidRight.png")));
		kidOneTexture.add(new Texture(Gdx.files.internal("data/kid1/KidFront.png")));
		kidOneTexture.add(new Texture(Gdx.files.internal("data/kid1/KidLeft.png")));
		kidOneTexture.add(new Texture(Gdx.files.internal("data/kid1/KidBack.png")));
	}
	private void loadKid2(){
		kidTwoTexture = new ArrayList<>();
		kidTwoTexture.add(new Texture(Gdx.files.internal("data/kid2/KidRight.png")));
		kidTwoTexture.add(new Texture(Gdx.files.internal("data/kid2/KidFront.png")));
		kidTwoTexture.add(new Texture(Gdx.files.internal("data/kid2/KidLeft.png")));
		kidTwoTexture.add(new Texture(Gdx.files.internal("data/kid2/KidBack.png")));
	}
	private void loadKid3(){
		kidThreeTexture = new ArrayList<>();
		kidThreeTexture.add(new Texture(Gdx.files.internal("data/kid3/KidRight.png")));
		kidThreeTexture.add(new Texture(Gdx.files.internal("data/kid3/KidFront.png")));
		kidThreeTexture.add(new Texture(Gdx.files.internal("data/kid3/KidLeft.png")));
		kidThreeTexture.add(new Texture(Gdx.files.internal("data/kid3/KidBack.png")));
	}
	private void loadKid4(){
		kidFourTexture = new ArrayList<>();
		kidFourTexture.add(new Texture(Gdx.files.internal("data/kid4/KidRight.png")));
		kidFourTexture.add(new Texture(Gdx.files.internal("data/kid4/KidFront.png")));
		kidFourTexture.add(new Texture(Gdx.files.internal("data/kid4/KidLeft.png")));
		kidFourTexture.add(new Texture(Gdx.files.internal("data/kid4/KidBack.png")));
	}
	
	private void loadSanta(){
		santaTexture = new ArrayList<>();
		santaTexture.add(new Texture(Gdx.files.internal("data/santa/SantaRight.png")));
		santaTexture.add(new Texture(Gdx.files.internal("data/santa/SantaFront.png")));
		santaTexture.add(new Texture(Gdx.files.internal("data/santa/SantaLeft.png")));
		santaTexture.add(new Texture(Gdx.files.internal("data/santa/SantaBack.png")));
	}
	private void loadBloodTexture(){
		bloodTexture = new Texture(Gdx.files.internal("data/blood.png"));
	}
	private void loadExplosionTexture(){
		explosionTexture = new Texture(Gdx.files.internal("data/explosion.png"));
	}
	private void loadSleighTexture(){
		sleighTexture = new Texture(Gdx.files.internal("data/sleigh.png"));
	}
	private void loadBannersTextures(){
		presentBanner = new Texture(Gdx.files.internal("data/presentBanner.png"));
        childrenBanner = new Texture(Gdx.files.internal("data/childrenBanner.png"));
	}
	private void loadGameInfoText(){
		gameInfoText = new ArrayList<>();
		gameInfoText.add(new Texture(Gdx.files.internal("data/win.png")));
		gameInfoText.add(new Texture(Gdx.files.internal("data/gameOver.png")));
	}
	private void loadSpeedProgressBar(){
		speedProgressBar = new Texture(Gdx.files.internal("data/progressBar/speedProgressBar.png"));
		speedHideProgressBar = new Texture(Gdx.files.internal("data/progressBar/speedHideProgressBar.png"));
	}
	private void loadFont(){
		font = new BitmapFont(Gdx.files.internal("data/comicFont.fnt"));
	}

}
