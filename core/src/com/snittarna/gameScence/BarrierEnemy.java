package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.pizza.AssetManager;

public class BarrierEnemy extends LaserEnemy {
	private float vunrableCount;
	private float maxVunrableCount;
	
	private boolean vunrable; 
	
	private Animation shield;
	
	public BarrierEnemy(Vector2 position, float maxShootCount, int health, float attackRange, float projectileSpeed,
			int damage, Vector2 size, Animation sprite) {
		super(position, maxShootCount, health, attackRange, projectileSpeed, damage, size, sprite);
		
		shield = new Animation(AssetManager.getTexture("projectile"));
		shield.setSize(1, 1);
		shield.setColor(0, 0, 1, 0.5f);
		
		this.maxVunrableCount = 20;
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		vunrableCount += 10 * deltaTime;
		
		shield.setPosition(this.getPosition().x, this.getPosition().x);
		
		if(vunrableCount >= maxVunrableCount) {
			vunrableCount = 0;
			vunrable = !vunrable;
		}
		
		setShootCount(0);
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		
		if(!vunrable) {
			shield.draw(batch);
			System.out.println("AYY BARIER");
		}
	}
}
