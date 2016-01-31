package com.snittarna.ui;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
		drawTutorial(batch);
		drawTitle(batch, title);
		menu.draw(batch);
	}
	
	private void drawTitle(SpriteBatch batch, String title) {
		GlyphLayout g = new GlyphLayout(AssetManager.font, title);
		AssetManager.font.draw(batch, g, 0 - g.width / 2, 250);
	}
	
	private void drawTutorial(SpriteBatch batch) {
		GlyphLayout g = new GlyphLayout(AssetManager.font, "Arrow keys to move, Z to jump, X to shoot \n Reach of level");
		AssetManager.font.draw(batch, g, 0 - g.width / 2, -250);
	}
}
