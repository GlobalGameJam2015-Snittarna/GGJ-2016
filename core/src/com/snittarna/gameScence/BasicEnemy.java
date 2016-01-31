package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.pizza.AssetManager;

public class BasicEnemy extends Enemy {

	Animation attack, walk;
	
	public BasicEnemy(Vector2 position, float maxAttackDelay, int health, float attackRange, int damage, float speed, Vector2 size, Animation walk, Animation attack) {
		super(position, attack);
		
		this.attack = attack;
		this.walk = walk;
		
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
				getSprite().setFlip(false, false);
			} else {
				if(!isAttacking()) velocity.x = getSpeed() * deltaTime;
				getSprite().setFlip(true, false);
			} 
			
			if (isAttacking() && attack != null) {
				setSprite(attack);
			} else {
				setSprite(walk);
			}
			System.out.println(getSprite());
			
			if(colX && !isPlayerAbove()) {
				velocity.y = 10; 
			}
		}
		super.update(deltaTime);
	}
}
