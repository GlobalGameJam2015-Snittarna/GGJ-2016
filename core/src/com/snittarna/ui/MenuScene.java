package com.snittarna.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snittarna.framework.Scene;
import com.snittarna.pizza.AssetManager;

public class MenuScene extends Scene {
	private Menu menu;
	private String title;
	
	public MenuScene(Menu menu, String title) {
		this.menu = menu;
		this.title = title;
	}
	
	public void update(float deltaTime) {
		menu.update();
	}
	
	public void drawUi(SpriteBatch batch) {
		drawTitle(batch, title);
		menu.draw(batch);
	}
	
	private void drawTitle(SpriteBatch batch, String title) {
		AssetManager.font.draw(batch, title, -50, 250);
	}
}
