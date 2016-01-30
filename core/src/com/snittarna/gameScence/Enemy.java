package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.framework.Killable;

public class Enemy extends Killable {
	private float attackRange;
	
	private boolean isAggro;
	
	private float shootCount;
	private float maxShootCount;
	private float attackAngle;
	
	private Projectile projectilePrototype;
	
	public Enemy(Vector2 position, Animation sprite) {
		super(position, sprite);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		for(GameObject g : getScene().getObjects()) {
			if(g instanceof Player) {
				isAggro = (distanceTo(g.getPosition()) <= attackRange);
				attackAngle = (float)Math.atan2(g.getPosition().y - getPosition().y, g.getPosition().x - getPosition().x);
			}
		}
	}
	
	public void shoot(float deltaTime) {
		if(isAggro) {
			shootCount += 10 * deltaTime;
			
			if(shootCount >= maxShootCount) {
				getScene().addObject(projectilePrototype);
				shootCount = 0;
			}
		}
	}
}
