package com.snittarna.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Scene;
import com.snittarna.gameScence.GameScene;
import com.snittarna.pizza.AssetManager;
import com.snittarna.pizza.Game;
import com.snittarna.ui.MenuButton.Action;

public class GameOverScene extends MenuScene {
	Menu menu;
	
	public GameOverScene(int score) {
		super(new Menu(new MenuButton[] {
				new MenuButton(new Vector2(0, 100), new Action() {
					public void call() {
						Game.setScene(new GameScene());
					}
				}, "play again"),
				new MenuButton(new Vector2(0, 0), new Action() {
					public void call() {
						System.out.println("quit");
						Gdx.app.exit();
					}
				}, "quit")
		}), "Game Over!\nscore: " + score);
	}
}
