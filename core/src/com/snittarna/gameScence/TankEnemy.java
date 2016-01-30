package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.pizza.AssetManager;

public class TankEnemy extends Enemy {
	public TankEnemy(Vector2 position) {
		super(position, new Animation(AssetManager.getTexture("projectile")));
		
		setSize(new Vector2(3, 3));
		setHealth(10);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}
