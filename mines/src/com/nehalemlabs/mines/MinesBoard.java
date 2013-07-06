package com.nehalemlabs.mines;

import java.util.ArrayDeque;
import java.util.Queue;

import com.badlogic.gdx.Gdx;

public class MinesBoard {
	private int[][] bombs;
	private int[][] state;
	private int[][] counter;
	private int width;
	private int height;
	private int nb;
	private int remaining;
	private boolean boom = false;
	private boolean clean = false;
	private boolean ready = false;
	
	public MinesBoard(int w, int h, int nBombs) {
		createMineField(w,h,nBombs);
	}

	public void createMineField(int w, int h, int nBombs) {
		if(nBombs >= w*h) {
			throw new IllegalArgumentException("less bombs than squares plz!");
		}
		
		setWidth(w);
		setHeight(h);
		setNb(nBombs);
		ready = false;
	}
	
	private void putBombs(int ci, int cj) {
		bombs = new int[width][height];
		state = new int[width][height];
		counter = new int[width][height];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				bombs[i][j] = 0;
				state[i][j] = 0;
				counter[i][j] = 0;
			}
		}
		
		for (int n = 0; n < nb; n++) {
			boolean done = false;
			while(!done) {
				int i = (int) (Math.random() * width);
				int j = (int) (Math.random() * height);
				//avoid putting bomb where player clicked
				if((i > ci-2 && i < ci+2) && (j > cj-2 && j < cj+2)) continue;
				if(bombs[i][j] == 0) {
					bombs[i][j] = 1;
					done = true;
				}
			}
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (bombs[i][j] == 1) {
					if(i != 0 && j != 0)                  counter[i-1][j-1] += 1;
					if(i != 0)                            counter[i-1][j  ] += 1;
					if(i != 0 && j != (height-1))         counter[i-1][j+1] += 1;
					if(j != 0)                            counter[i  ][j-1] += 1;
					if(j != (height-1))                   counter[i  ][j+1] += 1;
					if(i != (width-1) && j != 0)          counter[i+1][j-1] += 1;
					if(i != (width-1))                    counter[i+1][j  ] += 1;
					if(i != (width-1) && j != (height-1)) counter[i+1][j+1] += 1;
				}
			}
		}
		
		remaining = width*height - nb;
		ready = true;
	}
	
	public int getTile(int i, int j) {
		if(!ready) return 9;
		if(state[i][j] == 0) return 9; //closed
		if(state[i][j] == 1) return 10; //flagged
		return counter[i][j]; //open
	}
	
	public void tileFlagged(int i, int j) {
		Gdx.app.log("Board", "Flagged" + i + ","+j);
		if(state[i][j] == 0) state[i][j] = 1;
		else if(state[i][j] == 1) state[i][j] = 0;
	}
	
	public void tileOpened(int i, int j) {
		if(!ready) putBombs(i, j);
		if(boom || clean) return;
		if(state[i][j] > 0) return;
		Gdx.app.log("Board", "Opened" + i + ","+j);
		if(bombs[i][j] == 1) {
			this.boom = true;
			Gdx.app.log("Board", "DEFEAT");
			return;
		}
		
		//Gdx.app.log("info", Integer.toString(state[i][j]) +":"
		//		+ Integer.toString(bombs[i][j])+":"+ Integer.toString(counter[i][j]));
		
		Queue<Pair<Integer,Integer>> q = new ArrayDeque<Pair<Integer,Integer>>();
		openSingleTile(i, j, q);
		
		while(!q.isEmpty()) {
			Pair<Integer,Integer> p = q.poll();
			if(p.i != (width-1) && p.j != (height-1) && state[p.i+1][p.j+1] == 0) 
				openSingleTile(p.i+1, p.j+1, q);
			if(p.i != (width-1)                      && state[p.i+1][p.j  ] == 0) 
				openSingleTile(p.i+1, p.j  , q);
			if(p.i != (width-1) && p.j != 0          && state[p.i+1][p.j-1] == 0) 
				openSingleTile(p.i+1, p.j-1, q);
			if(p.j != (height-1)                     && state[p.i  ][p.j+1] == 0) 
				openSingleTile(p.i  , p.j+1, q);
			if(p.j != 0                              && state[p.i  ][p.j-1] == 0) 
				openSingleTile(p.i  , p.j-1, q);
			if(p.i != 0 && p.j != (height-1)         && state[p.i-1][p.j+1] == 0) 
				openSingleTile(p.i-1, p.j+1, q);
			if(p.i != 0                              && state[p.i-1][p.j  ] == 0) 
				openSingleTile(p.i-1, p.j  , q);
			if(p.i != 0 && p.j != 0                  && state[p.i-1][p.j-1] == 0) 
				openSingleTile(p.i-1, p.j-1, q);
		}
		
		if(remaining == 0) {
			clean = true;
			Gdx.app.log("Board", "VICTORY");
		}
	}
	
	private void openSingleTile(int i, int j, Queue<Pair<Integer,Integer>> q) {
		state[i][j] = 2;
		remaining -= 1;
		if(counter[i][j] == 0) {
			q.add(new Pair<Integer, Integer>(i, j));
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isBoom() {
		return boom;
	}

	public void setBoom(boolean boom) {
		this.boom = boom;
	}

	public int getNb() {
		return nb;
	}

	public void setNb(int nb) {
		this.nb = nb;
	}
}
