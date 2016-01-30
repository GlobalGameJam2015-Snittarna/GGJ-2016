package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.gameScence.Enemy.PlayerSide;
import com.snittarna.pizza.AssetManager;

public class TankEnemy extends Enemy {
	public TankEnemy(Vector2 position) {
		super(position, new Animation(AssetManager.getTexture("projectile")));
		
		setSize(new Vector2(3, 3));
		setHealth(10);
		
		setDamage(3);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if(this.isAggro()) {
			if(getCurrentPlayerSide() == PlayerSide.LEFT) {
				if(!isAttacking()) velocity.x = -getSpeed() * deltaTime;
			} else {
				if(!isAttacking()) velocity.x = getSpeed() * deltaTime;
			}
			
			if(colX && !isPlayerAbove()) {
				//velocity.y = 10; fienden måste ju vara unik på något sätt väll :^))))))))
			}
		}
	}
}
