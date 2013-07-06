package com.nehalemlabs.mines;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MinesGame implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private MinesBoard board;
	private TextureAtlas atlas;
	private Sprite[] tileSprites;
	private int width;
	private int height;
	private MainMenu mainMenu;
	private MinesMain parent;
	private InputMultiplexer im;
	
	public MinesGame(MinesMain g) {
		this.parent = g;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(10, 10*h/w);
		batch = new SpriteBatch();
		im = new InputMultiplexer();
		board = new MinesBoard(10, 10, 10);

		if(Gdx.app.getType() == ApplicationType.Desktop) {
			MinesInputHandler ih = new MinesInputHandler(this, camera, board);
			im.addProcessor(ih);
		}
		else {
			MinesGestures ig = new MinesGestures(this, camera, board);
			GestureDetector gd = new GestureDetector(20, 0.5f, 2, 0.15f, ig);
			im.addProcessor(gd);
		}
		Gdx.input.setInputProcessor(im);
		
		atlas = new TextureAtlas(Gdx.files.internal("tiles.atlas"));

		tileSprites = new Sprite[10];
		tileSprites[9] = atlas.createSprite("closed");
		tileSprites[0] = atlas.createSprite("empty");
		tileSprites[1] = atlas.createSprite("open1");
		tileSprites[2] = atlas.createSprite("open2");
		tileSprites[3] = atlas.createSprite("open3");
		tileSprites[4] = atlas.createSprite("open4");
		tileSprites[5] = atlas.createSprite("open5");
		tileSprites[6] = atlas.createSprite("open6");
		tileSprites[7] = atlas.createSprite("open7");
		tileSprites[8] = atlas.createSprite("open8");
		Gdx.app.log("MainGame", "I am alive");
	}
	
	public void createBoard(int w, int h, int nb) {
		width = w;
		height = h;
		board.createMineField(width, height, nb);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		float wStart = -1 * width/2;
		float hStart = -1 * height/2;
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int tileType = board.getTile(i, j);
				batch.draw(tileSprites[tileType], wStart+i, hStart+j, 0, 0, 1f, 1f, 1f, 1f, 0);
			}
		}
		batch.end();
		//Gdx.app.log("MainGame", "I am active");
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		//regain input control
		Gdx.input.setInputProcessor(im);
	}

	public void returnToMenu() {
		parent.gotoMenu();
		
	}

	public void zoomin(float f) {
		Gdx.app.log("CameraIn", Float.toString(camera.viewportWidth*camera.zoom) + ":" + Float.toString(camera.viewportHeight*camera.zoom));
		float nextZoom = camera.zoom - f;
		if(camera.viewportWidth*nextZoom < 10 && camera.viewportHeight*nextZoom < 10)
			return; //too much zoom in;
		camera.zoom -= f;
		camera.update();
	}

	public void zoomout(float f) {
		Gdx.app.log("CameraOut", Float.toString(camera.viewportWidth*camera.zoom) + ":" + Float.toString(camera.viewportHeight*camera.zoom));
		float nextZoom = camera.zoom + f;
		if(camera.viewportWidth*nextZoom > width && camera.viewportHeight*nextZoom > height)
			return; //too much zoom out;
		camera.zoom += f;
		camera.update();
	}

	public void moveCam(float f, float g) {
		float dx = 0;
		float dy = 0;
		float vw = camera.viewportWidth;
		float vh = camera.viewportHeight;
		Vector3 p = camera.position;
		//lock at bounds
		if(f > 0) { if(p.x+f+vw/2 > width/2)   dx = width/2 - vw/2 - p.x;  else dx = f; }
		if(f < 0) { if(p.x+f-vw/2 < -width/2)  dx = vw/2 - width/2 - p.x;  else dx = f; }
		if(g > 0) { if(p.y+g+vh/2 > height/2)  dy = height/2 - vh/2 - p.y; else dy = g; }
		if(g < 0) { if(p.y+g-vh/2 < -height/2) dy = vh/2 - height/2 - p.y; else dy = g; }
		camera.translate(dx, dy, 0);
		camera.update();
	}

	public void recenterCamera(float tx, float ty) {
		float vw = camera.viewportWidth;
		float vh = camera.viewportHeight;
		Vector3 p = camera.position;
		float dx = tx - p.x;
		float dy = ty - p.y;
		Gdx.app.log("CamRec", Float.toString(dx) + " " + Float.toString(dy));
		if(Math.sqrt(dx*dx) > 0.25*vw || Math.sqrt(dy*dy) > 0.25*vh) moveCam(dx, dy);
		
	}
}
