package com.snittarna.ui;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.pizza.AssetManager;

public class Label {
	
	private Vector2 position;
	private String  text;
	
	public Label(Vector2 position) {
		this.position = position;
		text = "";
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void draw(SpriteBatch batch) {
		GlyphLayout g = new GlyphLayout(AssetManager.font, text);
		AssetManager.font.draw(batch, g, position.x - g.width  / 2, position.y);
	}
}
