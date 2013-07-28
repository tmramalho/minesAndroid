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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class MainMenu implements Screen {
	
	MinesMain parent;
	private Stage stage;
	private Skin skin;

	// constructor to keep a reference to the main Game class
	public MainMenu(MinesMain g, BitmapFont font){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		this.parent = g;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/uiskin.atlas"));
		skin = new Skin(atlas);
		skin.add("lato-white", font);
		skin.load(Gdx.files.internal("data/uiskin.json"));
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		final Label title = new Label("Welcome to mines!", skin);
		table.add(title);
		table.row();
		final TextButton button = new TextButton("Easy", skin);
		table.add(button).height(h/12).space(h/36);
		button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				parent.playGame(9, 9, 10);
			}
		});
		table.row();
		final TextButton button1 = new TextButton("Medium", skin);
		table.add(button1).height(h/12).space(h/36);
		button1.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				parent.playGame(16, 16, 40);
			}
		});
		table.row();
		final TextButton button2 = new TextButton("Hard", skin);
		table.add(button2).height(h/12).space(h/36);
		button2.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				parent.playGame(30, 16, 99);
			}
		});
		table.row();
		final TextButton button3 = new TextButton("Professional", skin);
		table.add(button3).height(h/12).space(h/36);
		button3.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				parent.playGame(30, 24, 180);
			}
		});
		table.row();
		final TextButton button4 = new TextButton("Custom", skin);
		table.add(button4).height(h/12).space(h/36);
		button4.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				parent.showCustom();
			}
		});
		Gdx.app.log("MainMenu", "I am alive");
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
