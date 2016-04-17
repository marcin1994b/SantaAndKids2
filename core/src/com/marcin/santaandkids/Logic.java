package com.marcin.santaandkids;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;


public class Logic implements InputProcessor{
	
	private static MyTiledMap tiledMap;
	
	private static Santa santa;
	private static Sleigh sleigh;
	
	private Present present;
	
	private static ArrayList<Child> children;
	private static ArrayList<Present> presents;
	private static ArrayList<Explosion> explosions;
	
	private static MyStage myStage;

	private static Random random = new Random();
	
	private MyCameraSettings cameraSettings;
	
	private static boolean gameStarted;
	
	private Child kid;
	
	private static int playerScore;
	
	private static String notification;

	public Logic(){
		setGameStarted(false);
		createArrayLists();
		createCameraSettings();
		createMyStage();
		createTiledMap();
		createSanta();
		createSleigh();
		createChildren();
		addSantaAndSleighToStage();
		setPlayerScore(0);
		
		myStage.update();
		Gdx.input.setInputProcessor(this);
		
	}
	
	public void update(){
		if(isGameStarted() == true){
			if(santa.isLife(explosions) == true && santa.isInGame() == true){
				if(santa.collisionDetector(tiledMap.getTiledMap()) != true){
					santa.moving(tiledMap);
					santa.getUpBonus(presents);
					cameraMoving();
					if(isSantaOnSleigh() == true && santa.getNumberOfPresents() < 5){
						setNotification("Press SPACE to load more presents!");
					}
				}
			}else{
				if(santa.isInGame() == true){
					myStage.getBloodStage().addActor(new Blood(santa.getVector().x, santa.getVector().y));
					santa.setVector(-100, -100);
				}
				santa.setInGame(false);
			}
		}
		
	}
	
	//--------Creators---------
	private void createArrayLists(){
		children = new ArrayList<>();
		presents = new ArrayList<>();
		explosions = new ArrayList<>();
	}
	private void createCameraSettings(){
		cameraSettings = new MyCameraSettings();
	}
	private void createSanta(){
		santa = new Santa(400-16, 300-16, Constans.SPEED_OF_SANTA_NORMAL, true);
		santa.setPosition(santa.getVector().x, santa.getVector().y);
	}
	private void createSleigh(){
		sleigh = new Sleigh((int)santa.getVector().x-64, (int)santa.getVector().y, new Texture(Gdx.files.internal("data/Sleigh.png")));
	}
	private void createMyStage(){
		myStage = new MyStage(800, 600);
	}
	private void createTiledMap(){
		tiledMap = new MyTiledMap("data/map1.tmx");
	}
	private void createChildren(){
		children = new ArrayList<>();
		for(int i = 0; i<30; i++){
			kid = new Child( 1, true, i%4);
			if(myStage.getChildrenStage() != null){
				myStage.getChildrenStage().addActor(kid);
			}
			kid.setPosition(santa.getVector().x, santa.getVector().y);
			children.add(kid);
			new Thread(kid).start();
		}
	}
	
	public static void setPlayerScore(int score){
		playerScore = score;
	}

	//-------Getters---------
	public static ArrayList<Present> getPresents(){
		return presents;
	}
	public static ArrayList<Child> getChildren(){
		return children;
	}
	public static ArrayList<Explosion> getExplosions(){
		return explosions;
	}
	public static Santa getSanta(){
		return santa;
	}
	public static Sleigh getSleigh(){
		return sleigh;
	}
	public static MyStage getMyStage(){
		return myStage;
	}
	public static Random getRandom(){
		return random;
	}
	public static MyTiledMap getMyTiledMap(){
		return tiledMap;
	}
	public static boolean isGameStarted(){
		return gameStarted;
	}
	public static int getPlayerScore(){
		return playerScore;
	}
	
	public static void setGameStarted(boolean tmp){
		gameStarted = tmp;
	}

	//------------------------
	
	public void addSantaAndSleighToStage(){
		myStage.getSantaStage().addActor(sleigh);
		myStage.getSantaStage().addActor(santa);
	}
	
	public void cameraMoving(){
		cameraSettings.getVector().set(myStage.getSantaStage().getCamera().position);
		if(santa.isMovingToDown() == true && (cameraSettings.getVector().y - Constans.HEIGHT_OF_SCREEN/2) > 0 && santa.getVector().y <= tiledMap.getTiledMapHeight() - (Constans.HEIGHT_OF_SCREEN/2) - 16-32){
			myStage.getSantaStage().getCamera().translate(0, -santa.getSpeed(), 0);
		}else if(santa.isMovingToLeft() == true && (cameraSettings.getVector().x - Constans.WIDTH_OF_SCREEN/2) > 0 && santa.getVector().x <= tiledMap.getTiledMapWidth() - (Constans.WIDTH_OF_SCREEN/2) - 16){
			myStage.getSantaStage().getCamera().translate(-santa.getSpeed(), 0, 0);
		}else if(santa.isMovingToRight() == true && (cameraSettings.getVector().x + Constans.WIDTH_OF_SCREEN/2) < tiledMap.getTiledMapWidth() && santa.getVector().x >= (Constans.WIDTH_OF_SCREEN/2) - 16 ){
			myStage.getSantaStage().getCamera().translate(santa.getSpeed(), 0, 0);
		}else if(santa.isMovingToUp() == true && (cameraSettings.getVector().y + Constans.HEIGHT_OF_SCREEN/2) < tiledMap.getTiledMapWidth() - 32 && santa.getVector().y >= (Constans.HEIGHT_OF_SCREEN/2) - 16){
			myStage.getSantaStage().getCamera().translate(0, santa.getSpeed(), 0);
		}else{
			cameraSettings.stop();
		}
	}
	
	private void getPresent(){
		if(santa.getNumberOfPresents() <5){
			santa.setNumberOfPresents(santa.getNumberOfPresents()+1);
		}
	}
	
	private void leavePresent(){
		if(santa.getNumberOfPresents() != 0 && isGameStarted() == true){
			if(santa.getPresentType() == 1){
				present = santa.leavePresent();
			}else if(santa.getPresentType() == 2){
				present = santa.leavePresent();
			}else if(santa.getPresentType() == 3){
				present = santa.leavePresent();
			}else if(santa.getPresentType() == 4){
				present = santa.leavePresent();
			}
			presents.add(present);
			myStage.getPresentsStage().addActor(present);
			santa.setNumberOfPresents(santa.getNumberOfPresents()-1);
			if(santa.getNumberOfPresents() == 0){
				setNotification("Go to sleigh for more presents!");
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		
		switch(keycode){
		case Input.Keys.DOWN:
			cameraSettings.setMovingToDown(true);
			santa.setMoveDirection(Constans.DOWN);
			break;
		case Input.Keys.LEFT:
			cameraSettings.setMovingToLeft(true);
			santa.setMoveDirection(Constans.LEFT);
			break;
		case Input.Keys.RIGHT:
			cameraSettings.setMovingToRight(true);
			santa.setMoveDirection(Constans.RIGHT);
			break;
		case Input.Keys.UP:
			cameraSettings.setMovingToUp(true);
			santa.setMoveDirection(Constans.UP);
			break;
		case Input.Keys.S:
			cameraSettings.setMovingToDown(true);
			santa.setMoveDirection(Constans.DOWN);
			break;
		case Input.Keys.A:
			cameraSettings.setMovingToLeft(true);
			santa.setMoveDirection(Constans.LEFT);
			break;
		case Input.Keys.D:
			cameraSettings.setMovingToRight(true);
			santa.setMoveDirection(Constans.RIGHT);
			break;
		case Input.Keys.W:
			cameraSettings.setMovingToUp(true);
			santa.setMoveDirection(Constans.UP);
			break;
		case Input.Keys.SPACE:
			if(santa.getVector().x+32 < sleigh.getVector().x+128 && santa.getVector().x+32 > sleigh.getVector().x &&
			   santa.getVector().y+32 < sleigh.getVector().y+64 && santa.getVector().y > sleigh.getVector().y){
				getPresent(); 
			}else{
				leavePresent();
			}
			break;
		case Input.Keys.ESCAPE:
		
		case Input.Keys.CONTROL_LEFT:
			if(santa.getCondition() > 0){
				santa.setSpeed(Constans.SPEED_OF_SANTA_RUN);
			}else{
				santa.setSpeed(Constans.SPEED_OF_SANTA_NORMAL);
			}
			break;
		}
		return false;
	}
	
	private boolean isSantaOnSleigh(){
		return (santa.getVector().x+32 < sleigh.getVector().x+128 && santa.getVector().x+32 > sleigh.getVector().x &&
				   santa.getVector().y+32 < sleigh.getVector().y+64 && santa.getVector().y > sleigh.getVector().y);
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Input.Keys.DOWN:
			cameraSettings.setMovingToDown(true);
			santa.setMovingToDown(false);
			break;
		case Input.Keys.LEFT:
			cameraSettings.setMovingToLeft(false);
			santa.setMovingToLeft(false);
			break;
		case Input.Keys.RIGHT:
			cameraSettings.setMovingToRight(false);
			santa.setMovingToRight(false);
			break;
		case Input.Keys.UP:
			cameraSettings.setMovingToUp(false);
			santa.setMovingToUp(false);
			break;
		case Input.Keys.S:
			cameraSettings.setMovingToDown(false);
			santa.setMovingToDown(false);
			break;
		case Input.Keys.A:
			cameraSettings.setMovingToLeft(false);
			santa.setMovingToLeft(false);
			break;
		case Input.Keys.D:
			cameraSettings.setMovingToRight(false);
			santa.setMovingToRight(false);
			break;
		case Input.Keys.W:
			cameraSettings.setMovingToUp(false);
			santa.setMovingToUp(false);
			break;
		case Input.Keys.CONTROL_LEFT:
			santa.setSpeed(Constans.SPEED_OF_SANTA_NORMAL);
			//cameraSettings.setSpeed(2);
			break;
		}
		return false;
	}
	
	public static void setNotification(String tmp){
	notification = tmp;
	}
	
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
