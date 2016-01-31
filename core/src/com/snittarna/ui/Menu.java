package com.snittarna.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snittarna.pizza.AssetManager;
import com.snittarna.ui.MenuButton;

public class Menu {
	private MenuButton[] buttons;
	
	private int selected;
	
	public Menu(MenuButton[] buttons) {
		this.buttons = buttons;
		selected = 0;
	}
	
	public void update() {
		int os = selected;
		if (Gdx.input.isKeyJustPressed(Keys.UP)) selected--;
		if (selected < 0) selected = buttons.length - 1;
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) selected++;
		if (selected >= buttons.length) selected = 0;
		
		if (Gdx.input.isKeyJustPressed(Keys.Z) || Gdx.input.isKeyJustPressed(Keys.X) || Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			buttons[selected].onClick();
			AssetManager.getSound("menu-select").play();
		}
		
		if (os != selected) AssetManager.getSound("menu-change").play();
	}
	
	public void draw(SpriteBatch batch) {
		int i = 0;
		for (MenuButton b : buttons) b.draw(batch, selected == i++);
	}
}
