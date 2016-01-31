package com.snittarna.ui;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.pizza.AssetManager;

public class MenuButton {
	
	private Vector2 position;
	private Action action;
	private String text;
	
	public MenuButton(Vector2 position, Action action, String text) {
		this.position = position;
		this.action = action;
		this.text = text;
	}
	
	public void onClick() {
		action.call();
	}
	
	public interface Action {
		public void call();
	}

	public void draw(SpriteBatch batch, boolean  selected) {
		GlyphLayout g = new GlyphLayout(AssetManager.font, (selected ? ">" : "") + text);
		AssetManager.font.draw(batch, g, position.x - g.width / 2, position.y);
	}
}
