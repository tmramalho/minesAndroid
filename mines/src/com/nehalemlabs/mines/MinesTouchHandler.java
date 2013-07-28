package com.nehalemlabs.mines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MinesTouchHandler implements InputProcessor {
	private MinesGame boss;
	private OrthographicCamera camera;
	private MinesGestures ig;
	private Vector3 touchPos;

	public MinesTouchHandler(MinesGame minesGame, OrthographicCamera c, MinesGestures igest) {
		boss = minesGame;
		camera = c;
		ig = igest;
		touchPos = new Vector3();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
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
		Gdx.app.log("touchUP", screenX + ":" + screenY + ":" + pointer + ":" + button);
		if(!ig.isZooming() && pointer == 1) {
			//put flag
			touchPos.set(screenX, screenY, 0);
			camera.unproject(touchPos);
			boss.processTouch(touchPos.x, touchPos.y, 1);
		}
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
