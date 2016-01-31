package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;

public class BarrierEnemy extends LaserEnemy {
	private float barrierCount;
	private float maxBarrierCount;
	
	public BarrierEnemy(Vector2 position, float maxShootCount, int health, float attackRange, float projectileSpeed,
			int damage, Vector2 size, Animation sprite) {
		super(position, maxShootCount, health, attackRange, projectileSpeed, damage, size, sprite);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		this.setShootCount(0);
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}
