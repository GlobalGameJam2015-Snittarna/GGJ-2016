package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.pizza.AssetManager;

public class BasicEnemy extends Enemy {

	public BasicEnemy(Vector2 position, float maxAttackDelay, int health, float attackRange, int damage, float speed, Vector2 size, Animation sprite) {
		super(position, sprite);
		
		this.setType(Type.ENEMY);
		this.setHealth(health);
		
		this.setSpeed(speed);
		
		this.setAttackRange(attackRange);
		this.setDamage(damage);
		
		this.setMaxAttackDelay(maxAttackDelay);
		
		setSize(size);
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
