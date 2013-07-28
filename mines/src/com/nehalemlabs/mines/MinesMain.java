package com.nehalemlabs.mines;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MinesMain extends Game {
	private MainMenu mainMenu;
	private MinesGame mainGame;
	private CustomMenu customMenu;
	
	@Override
	public void create() {
		//font = new BitmapFont(Gdx.files.internal("data/default.fnt"), Gdx.files.internal("data/default.png"), false);
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("Lato-Regular.ttf"));
		BitmapFont font = gen.generateFont(16);
		gen.dispose();
		mainGame = new MinesGame(this, font);
		mainMenu = new MainMenu(this, font);
		customMenu = new CustomMenu(this, font);
		setScreen(mainMenu);
	}
	
	public void playGame(int w, int h, int nb) {
		mainGame.createBoard(w, h, nb);
		setScreen(mainGame);
	}
	
	public void gotoMenu() {
		setScreen(mainMenu);
	}
	
	public void showCustom() {
		setScreen(customMenu);
	}

	@Override
	public void dispose() {
		mainMenu.dispose();
		mainGame.dispose();
	}
}
