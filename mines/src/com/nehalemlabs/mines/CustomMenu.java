package com.nehalemlabs.mines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class CustomMenu implements Screen {
	
	MinesMain parent;
	private Stage stage;
	private Skin skin;
	private OrthographicCamera camera;

	// constructor to keep a reference to the main Game class
	public CustomMenu(MinesMain g){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(w, h);
		this.camera.position.set(w/2, h/2, 0f);
		this.parent = g;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		final Label title = new Label("Customize your game below", skin);
		table.add(title);
		table.row();
		final Label wLabel = new Label("Width", skin);
		table.add(wLabel);
		final Slider wslider = new Slider(10, 100, 1, false, skin);
		wslider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//
			}
		});
		table.add(wslider);
		table.row();
		final Label hLabel = new Label("Height", skin);
		table.add(hLabel);
		final Slider hslider = new Slider(10, 100, 1, false, skin);
		table.add(hslider);
		table.row();
		final Label nbLabel = new Label("Bombs", skin);
		table.add(nbLabel);
		final Slider nbslider = new Slider(10, 100, 1, false, skin);
		table.add(nbslider);
		table.row();
		final TextButton button = new TextButton("Play", skin);
		table.add(button);
		button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				parent.playGame(20, 20, 20);
			}
		});
		table.row();
		final TextButton button1 = new TextButton("Back", skin);
		table.add(button1);
		button1.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				parent.gotoMenu();
			}
		});
		Gdx.app.log("CustomMenu", "I am alive");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		//regain input control
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
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
	public void dispose() {
		stage.dispose();
	}

}
