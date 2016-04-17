package com.marcin.santaandkids;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import aurelienribon.tweenengine.TweenAccessor;

public class ImageAccessor implements TweenAccessor<Image> {
	
	public final static int SCALE = 1;
	public final static int POSITION_XY = 2;

	@Override
	public int getValues(Image target, int tweenType, float[] returnValues) {
		switch (tweenType) {
	      case SCALE:
	         returnValues[0] = target.getScaleX();
	         returnValues[1] = target.getScaleY();
	         return 2;
	      case POSITION_XY:
	         returnValues[0] = target.getX();
	         returnValues[1] = target.getY();
	         return 2;
	      default:
	         return 0;
		}
	}

	@Override
	public void setValues(Image target, int tweenType, float[] newValues) {
		switch (tweenType) {
	      case SCALE:
	    	 target.setScale((int) newValues[0], newValues[1]);
	    	 break;
	      case POSITION_XY:
	    	 target.setPosition(newValues[0], newValues[1]);
	         break;
		}
		
	}

}
