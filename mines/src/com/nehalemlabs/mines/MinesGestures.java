package com.nehalemlabs.mines;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MinesGestures implements GestureListener {
	private MinesBoard board;
	private MinesGame boss;
	private OrthographicCamera camera;
	
	private final ScheduledExecutorService seService = Executors.newScheduledThreadPool(1);
	private int lTap = 0;
	
	private Runnable tapper(float x, float y, int current) {
		return new Runnable() {
			int current = 0;
			float x;
			float y;
			Runnable setCurrent(float _x, float _y, int _current) {
				x = _x;
				y = _y;
				current = _current;
				return this;
			}
			@Override
			public void run() {
				if(current == lTap) {
					Gdx.app.log("GestureDetectorTest", "tap " + current + " activated");
					Vector3 touchPos = new Vector3();
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					camera.unproject(touchPos);
					boss.processTouch(touchPos.x, touchPos.y, current-1);
				}
				else {
					
				}
			}
		}.setCurrent(x, y, current);
	}
	
	public MinesGestures(MinesGame g, OrthographicCamera c, MinesBoard b) {
		boss = g;
		camera = c;
		board = b;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		//Gdx.app.log("GestureDetectorTest", "tap at " + x + ", " + y + ", count: " + count + ", btn:" + button);
		
		lTap = count;
		seService.schedule(tapper(x, y, count), 300, TimeUnit.MILLISECONDS);
		
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Gdx.app.log("PAN", x + ":" + y + ":" + deltaX + ":" + deltaY);
		boss.moveCam(-deltaX/100, deltaY/100);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		Gdx.app.log("Pinch", initialPointer1.x + ":" + initialPointer2.x + ":" + pointer1.x + ":" + pointer2.x);
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		Gdx.app.log("Zoom", initialDistance + ":" + distance);
		if(initialDistance < distance) {
			float ratio = distance/initialDistance;
			boss.zoomin(ratio);
		} else {
			float ratio = initialDistance/distance;
			boss.zoomout(ratio);
		}
		return false;
	}
	}
