package com.snake;

import javafx.scene.canvas.GraphicsContext;

public class Snake {

	private int posX;
	private int posY;
	private int sizeX = 10;
	private int sizeY = 10;
	private int speed = 3;

	public Snake(int x, int y) {
		this.posX = x;
		this.posY = y;
	}

	public void update(int dx, int dy) {
		posX = posX + (speed * dx);
		posY = posY + (speed * dy);
	}

	public void show(GraphicsContext gc) {
		gc.fillRect(posX, posY, sizeX, sizeY);

	}

}
