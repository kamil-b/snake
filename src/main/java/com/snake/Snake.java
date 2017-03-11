package com.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Snake {

	private int posX;
	private int posY;
	private int sizeX = 10;
	private int sizeY = 10;
	private int speed = 2;
	private Color color;

	public Snake(int x, int y, Color color) {
		this.posX = x;
		this.posY = y;
		this.color = color;
	}

	public void update(int dx, int dy) {
		posX = posX + (speed * dx);
		posY = posY + (speed * dy);
	}

	public void show(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(posX, posY, sizeX, sizeY);

	}
	
	
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	
}
