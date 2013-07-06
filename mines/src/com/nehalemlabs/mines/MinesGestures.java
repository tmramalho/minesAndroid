package com.nehalemlabs.mines;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class MinesGestures implements GestureListener {
	private MinesBoard board;
	private MinesGame boss;
	private OrthographicCamera camera;
	
	private final ScheduledExecutorService seService = Executors.newScheduledThreadPool(1);
	private int lTap = 0;
	
	private Runnable tapper(int current) {
		return new Runnable() {
			int current = 0;
			Runnable setCurrent(int _current) {
				current = _current;
				return this;
			}
			@Override
			public void run() {
				if(current == lTap) {
					Gdx.app.log("GestureDetectorTest", "tap " + current + " activated");
				}
				else {
					
				}
			}
		}.setCurrent(current);
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
		seService.schedule(tapper(count), 300, TimeUnit.MILLISECONDS);
		
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}
	}
