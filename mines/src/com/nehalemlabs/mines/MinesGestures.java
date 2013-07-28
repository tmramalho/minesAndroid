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

	private MinesGame boss;
	private OrthographicCamera camera;
	private Vector3 touchPos;
	private boolean zooming = false;
	
	private final ScheduledExecutorService seService = Executors.newScheduledThreadPool(1);
	private int lTap = 0;
	
	public MinesGestures(MinesGame g, OrthographicCamera c) {
		boss = g;
		camera = c;
		touchPos = new Vector3();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Gdx.app.log("touchdown", x + ":" + y + ":" + pointer + ":" + button);
		zooming = false;
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		//open tile
		touchPos.set(x, y, 0);
		camera.unproject(touchPos);
		boss.processTouch(touchPos.x, touchPos.y, 0);
		
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Gdx.app.log("FLY!", velocityX + ":" + velocityY + ":" + button);
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Gdx.app.log("PAN", x + ":" + y + ":" + deltaX + ":" + deltaY);
		/* unproject previous and current pos
		 * to get unprojected delta
		 */
		touchPos.set(x, y, 0);
		camera.unproject(touchPos); // current pos
		float xn = touchPos.x;
		float yn = touchPos.y;
		touchPos.set(x - deltaX, y - deltaY, 0);
		camera.unproject(touchPos); // previous pos
		float dx = touchPos.x - xn;
		float dy = touchPos.y - yn;
		boss.moveCam(dx, dy);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		zooming = true;
		touchPos.set(initialPointer1.x, initialPointer1.y, 0);
		camera.unproject(touchPos);
		float x1n = touchPos.x;
		float y1n = touchPos.y;
		touchPos.set(initialPointer2.x, initialPointer2.y, 0);
		camera.unproject(touchPos);
		float x2n = touchPos.x;
		float y2n = touchPos.y;
		touchPos.set(pointer1.x, pointer1.y, 0);
		camera.unproject(touchPos);
		float x1p = touchPos.x;
		float y1p = touchPos.y;
		touchPos.set(pointer2.x, pointer2.y, 0);
		camera.unproject(touchPos);
		float x2p = touchPos.x;
		float y2p = touchPos.y;
		float dx1 = x1n - x2n;
		float dy1 = y1n - y2n;
		float dx2 = x1p - x2p;
		float dy2 = y1p - y2p;
		float initialDistance = (float) Math.sqrt(dx1*dx1+dy1*dy1);
		float distance = (float) Math.sqrt(dx2*dx2+dy2*dy2);
		Gdx.app.log("RatioUn", "->" + distance/initialDistance);
		float cx = Math.abs((x1n + x2n)/2);
		float cy = Math.abs((y1n + y2n)/2);
		if(initialDistance < distance) {
			float ratio = distance/initialDistance;
			boss.zoomAndCenter(-ratio/100, cx, cy);
		} else {
			float ratio = initialDistance/distance;
			boss.zoomAndCenter( ratio/100, cx, cy);
		}
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		zooming = true;
		Gdx.app.log("RatioP", "->" + distance/initialDistance);
		/*Gdx.app.log("Zoom", initialDistance + ":" + distance);
		if(initialDistance < distance) {
			float ratio = distance/initialDistance;
			boss.zoomin(ratio);
		} else {
			float ratio = initialDistance/distance;
			boss.zoomout(ratio);
		}*/
		return false;
	}
	
	public boolean isZooming() {
		return zooming;
	}
}
