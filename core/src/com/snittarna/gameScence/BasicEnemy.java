package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.pizza.AssetManager;

public class BasicEnemy extends Enemy {

	public BasicEnemy(Vector2 position) {
		super(position, new Animation(AssetManager.getTexture("projectile")));
		
		this.setType(Type.ENEMY);
		this.setHealth(2);
		
		this.setSpeed(150);
		this.setMaxVelocity(40);
		
		this.setAttackRange(5);
		
		this.gravitates = true;
		
		setSize(new Vector2(0.9f, 0.9f));
	}
	
	public void update(float deltaTime) {
		if(this.isAggro()) {
			if(getCurrentPlayerSide() == PlayerSide.LEFT) {
				if(!isAttacking()) velocity.x = -getSpeed() * deltaTime;
			} else {
				if(!isAttacking()) velocity.x = getSpeed() * deltaTime;
			}
			
			if(colX && !isPlayerAbove()) {
				velocity.y = 10; 
			}
		}
		super.update(deltaTime);
	}
}
