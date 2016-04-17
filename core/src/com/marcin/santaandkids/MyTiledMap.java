package com.marcin.santaandkids;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MyTiledMap{
	
	private static TiledMap tiledMap;
	
	private int tiledMapWidth;
	private int tiledMapHeight;
	
	public MyTiledMap(String string){
		tiledMap = new TmxMapLoader().load(string);
		tiledMapWidth = tiledMap.getProperties().get("width", Integer.class)*Constans.WIDTH_OF_TILE;
		tiledMapHeight = tiledMap.getProperties().get("height", Integer.class)*Constans.HEIGHT_OF_TILE;
	}
	
	public TiledMap getTiledMap(){
		return tiledMap;
	}
	public int getTiledMapWidth(){
		return tiledMapWidth;
	}
	public int getTiledMapHeight(){
		return tiledMapHeight;
	}

}
