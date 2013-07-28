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
import com.esotericsoftware.tablelayout.Cell;

public class CustomMenu implements Screen {
	
	MinesMain parent;
	private Stage stage;
	private Skin skin;
	private OrthographicCamera camera;
	private int wVal;
	private int hVal;
	private int nbVal;

	// constructor to keep a reference to the main Game class
	public CustomMenu(MinesMain g, BitmapFont font){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(w, h);
		this.camera.position.set(w/2, h/2, 0f);
		this.parent = g;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/uiskin.atlas"));
		skin = new Skin(atlas);
		skin.add("lato-white", font);
		skin.load(Gdx.files.internal("data/uiskin.json"));
		
		wVal = 16;
		hVal = 16;
		nbVal = 40;

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		final Label title = new Label("Customize your game below", skin);
		table.add(title);
		table.row();
		final Label wLabel = new Label("Width", skin);
		table.add(wLabel).height(h/12).space(h/36);
		final Label wnLabel = new Label(Integer.toString(wVal), skin);
		table.add(wnLabel).height(h/12).space(h/36);
		final Slider wslider = new Slider(8, 40, 1, false, skin);
		wslider.setValue(wVal);
		table.add(wslider).height(h/12).space(h/36);
		table.row();
		final Label hLabel = new Label("Height", skin);
		table.add(hLabel).height(h/12).space(h/36);
		final Label hnLabel = new Label(Integer.toString(hVal), skin);
		table.add(hnLabel).height(h/12).space(h/36);
		final Slider hslider = new Slider(8, 40, 1, false, skin);
		hslider.setValue(hVal);
		table.add(hslider).height(h/12).space(h/36);
		table.row();
		final Label nbLabel = new Label("Bombs", skin);
		table.add(nbLabel).height(h/12).space(h/36);
		final Label nbnLabel = new Label(Integer.toString(nbVal), skin);
		table.add(nbnLabel).height(h/12).space(h/36);
		final Slider nbslider = new Slider(1, (int)((wVal*hVal)/3), 1, false, skin);
		nbslider.setValue(nbVal);
		table.add(nbslider).height(h/12).space(h/36);
		table.row();
		final TextButton button = new TextButton("Play", skin);
		table.add(button).height(h/12).space(h/36);
		final TextButton button1 = new TextButton("Back", skin);
		table.add(button1).height(h/12).space(h/36);
		
		wslider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				wVal = (int) wslider.getValue();
				wnLabel.setText(Integer.toString(wVal));
				int nbValTemp = (int) nbslider.getValue();
				nbslider.setRange(1, (int)((wVal*hVal)/3));
				nbValTemp = Math.min(nbValTemp, (int)((wVal*hVal)/3));
				nbslider.setValue(nbValTemp);
			}
		});
		hslider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				hVal = (int) hslider.getValue();
				hnLabel.setText(Integer.toString(hVal));
				int nbValTemp = (int) nbslider.getValue();
				nbslider.setRange(1, (int)((wVal*hVal)/3));
				nbValTemp = Math.min(nbValTemp, (int)((wVal*hVal)/3));
				nbslider.setValue(nbValTemp);
			}
		});
		nbslider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				nbVal = (int) nbslider.getValue();
				nbnLabel.setText(Integer.toString(nbVal));
			}
		});
		button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button.isChecked());
				parent.playGame(wVal, hVal, nbVal);
			}
		});
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
