package com.nehalemlabs.mines;

import com.badlogic.gdx.Game;

public class MinesMain extends Game {
	private MainMenu mainMenu;
	private MinesGame mainGame;
	
	@Override
	public void create() {
		mainGame = new MinesGame(this);
		mainMenu = new MainMenu(this);
		setScreen(mainMenu);
	}
	
	public void playGame(int w, int h, int nb) {
		mainGame.createBoard(w, h, nb);
		setScreen(mainGame);
	}
	
	public void gotoMenu() {
		setScreen(mainMenu);
	}

	@Override
	public void dispose() {
		mainMenu.dispose();
		mainGame.dispose();
	}
}
