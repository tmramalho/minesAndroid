package com.nehalemlabs.mines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MinesInputHandler implements InputProcessor {
	private MinesBoard board;
	private MinesGame boss;
	private OrthographicCamera camera;
	
	public MinesInputHandler(MinesGame g, OrthographicCamera c, MinesBoard b) {
		boss = g;
		camera = c;
		board = b;
	}
	
	@Override
	public boolean keyDown (int keycode) {
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		switch(keycode){
		case Input.Keys.LEFT:
			boss.moveCam(-0.5f, 0);
			break;
		case Input.Keys.RIGHT:
			boss.moveCam(0.5f, 0);
			break;
		case Input.Keys.UP:
			boss.moveCam(0, 0.5f);
			break;
		case Input.Keys.DOWN:
			boss.moveCam(0, -0.5f);
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		switch (character) {
		case 'q':
			boss.zoomin(0.5f);
			break;
		case 'e':
			boss.zoomout(0.5f);
			break;
		case 'w':
			boss.moveCam(0.5f, 0.5f);
			break;
		case 't':
			boss.returnToMenu();
			break;
		}
		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		int w = board.getWidth();
		int h = board.getHeight();
		int i = (int) ((touchPos.x + w/2));
		int j = (int) ((touchPos.y + h/2));
		if(i >= 0 && i < w && j >=0 && j < h)
			board.tileOpened(i, j);
		//boss.recenterCamera(touchPos.x, touchPos.y);
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	public boolean touchMoved (int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
}
