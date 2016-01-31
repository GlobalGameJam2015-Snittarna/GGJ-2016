package com.snittarna.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snittarna.ui.MenuButton;

public class Menu {
	private MenuButton[] buttons;
	
	private int selected;
	
	public Menu(MenuButton[] buttons) {
		this.buttons = buttons;
		selected = 0;
	}
	
	public void update() {
		if (Gdx.input.isKeyJustPressed(Keys.UP)) selected--;
		if (selected < 0) selected = buttons.length - 1;
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) selected++;
		if (selected >= buttons.length) selected = 0;
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			buttons[selected].onClick();
		}
	}
	
	public void draw(SpriteBatch batch) {
		int i = 0;
		for (MenuButton b : buttons) b.draw(batch, selected == i++);
	}
}
