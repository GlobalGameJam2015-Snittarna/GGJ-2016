package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.framework.Scene;
import com.snittarna.map.Map;
import com.snittarna.pizza.AssetManager;
import com.snittarna.pizza.Game;
import com.snittarna.ui.WinScene;

public class GameScene extends Scene {
	
	public boolean nextLevel;
	
	private Animation background;
	
	public GameScene() {
		super();
		Map.player = null; // dont keep the player if the game is restarted
		Map.load();
		loadLevel(0);
		background = new Animation(AssetManager.getTexture("background"));
		background.setSize(20, 10);
		//addObject(new Projectile(new Vector2(0, 0), (float)Math.PI/2, (float)Math.PI/2, 1, new Killable(new Vector2(0, 0), new Animation(AssetManager.getTexture("projectile"))), new Animation(AssetManager.getTexture("projectile"))));
		//addObject(new Projectile(new Vector2(0, 0), (float)Math.PI/2, (float)Math.PI/2, 1, Killable.Type.PLAYER, new Animation(AssetManager.getTexture("projectile"))));
		//addObject(new Cultist(new Vector2(1, 4), 5));
		//addObject(new BasicEnemy(new Vector2(1, 4), 32, 5, 7, 3, 100, new Vector2(2, 2), new Animation(new Sprite(AssetManager.getTexture("tank")), 0.5f, 0, 0, false), new Animation(AssetManager.getTexture("tank"))));
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (nextLevel)  {
			nextLevel = false;
			loadNextLevel();
		}
		
		background.setPosition(this.getCamera().position.x - 8, this.getCamera().position.y - 5);
	}
	
	public void loadNextLevel() {
		getObjects().clear();
		if (Map.isLastLevel()) {
			Game.setScene(new WinScene(Player.score));
		} else {
			Map.nextLevel();
			Map.getCurrentLevel().loadObjects(this);
		}
	}
	
	public void loadLevel(int level) {
		getObjects().clear();
		Map.setLevel(level);
		Map.getCurrentLevel().loadObjects(this);
	}
	
	public void draw(SpriteBatch batch) {
		background.draw(batch);
		Map.draw(batch);
		super.draw(batch);
	}
}
