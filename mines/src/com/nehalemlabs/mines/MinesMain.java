package com.nehalemlabs.mines;

import com.badlogic.gdx.Game;

public class MinesMain extends Game {
	private MainMenu mainMenu;
	private MinesGame mainGame;
	private CustomMenu customMenu;
	
	@Override
	public void create() {
		mainGame = new MinesGame(this);
		mainMenu = new MainMenu(this);
		customMenu = new CustomMenu(this);
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
