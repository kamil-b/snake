package com.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Snake {

	private int posX;
	private int posY;
	
	private int scale = 20;
	private Color color;

	public Snake(int x, int y, Color color, int scale) {
		this.scale = scale;
		this.posX = x;
		this.posY = y;
		this.color = color;
	}

	public void update(int dx, int dy) {
		posX = posX + (scale * dx);
		posY = posY + (scale * dy);
		if (posX > 600) {
			posX = 0;
		}
		if (posX < 0) {
			posX = 600;
		}
		if (posY > 400) {
			posY = 0;
		}
		if (posY < 0) {
			posY = 400;
		}
	}

	public void show(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(posX, posY, scale, scale);

	}

	@Override
	public String toString() {
		return "Snake [posX=" + posX + ", posY=" + posY + ", scale=" + scale + ", color=" + color + "]";
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
