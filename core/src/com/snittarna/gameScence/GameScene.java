package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.framework.Scene;
import com.snittarna.pizza.AssetManager;

public class GameScene extends Scene {
	public GameScene() {
		super();
		//addObject(new Projectile(new Vector2(0, 0), (float)Math.PI/2, (float)Math.PI/2, 1, Killable.Type.PLAYER, new Animation(AssetManager.getTexture("projectile"))));
		addObject(new Player(new Vector2(0, 0)));
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}