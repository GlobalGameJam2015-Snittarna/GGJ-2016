package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.pizza.AssetManager;

public class LaserEnemy extends Enemy {

	public LaserEnemy(Vector2 position, float maxShootCount, int health, float attackRange, int damage, Vector2 size, Animation sprite) {
		super(position, sprite);
		setSize(size);
		
		setHealth(2);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}
