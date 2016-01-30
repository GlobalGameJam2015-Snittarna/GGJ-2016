package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.pizza.AssetManager;

public class BasicEnemy extends Enemy {

	public BasicEnemy(Vector2 position) {
		super(position, new Animation(AssetManager.getTexture("projectile")));
		
		this.setType(Type.ENEMY);
		this.setHealth(2);
		
		this.setSpeed(20);
		this.setMaxVelocity(40);
		
		this.setAttackRange(200);
		
		this.gravitates = true;
		
		setSize(new Vector2(1, 1));
	}
	
	public void update(float deltaTime) {
		if(this.isAggro()) {
			if(getCurrentPlayerSide() == PlayerSide.LEFT) {
				if(!isAttacking()) velocity.x = -getSpeed() * deltaTime;
			} else {
				if(!isAttacking()) velocity.x = getSpeed() * deltaTime;
			}
		}
		super.update(deltaTime);
	}
}
