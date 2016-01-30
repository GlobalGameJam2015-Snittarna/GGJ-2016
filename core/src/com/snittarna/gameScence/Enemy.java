package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.framework.Killable;

public class Enemy extends Killable {
	public enum PlayerSide { LEFT, RIGHT }; 

	private PlayerSide currentPlayerSide;
	
	private float attackRange;
	
	private boolean isAggro;
	
	private float shootCount;
	private float maxShootCount;
	private float attackAngle;
	private float speed;
	
	private float maxVelocity;
	
	private Projectile projectilePrototype;
	
	private boolean attacking;
	
	private boolean playerAbove;
	
	public Enemy(Vector2 position, Animation sprite) {
		super(position, sprite);
		
		this.setMaxInvicibleTimer(4);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		for(GameObject g : getScene().getObjects()) {
			if(g instanceof Player) {
				isAggro = (distanceTo(g.getPosition()) <= attackRange);
				attackAngle = (float)Math.atan2(g.getPosition().y - getPosition().y, g.getPosition().x - getPosition().x);
				
				attacking = (getHitbox().collision(g.getHitbox()));
				
				playerAbove = (this.getPosition().y >= g.getPosition().y);
				
				if(g.getPosition().x > getPosition().x) {
					currentPlayerSide = PlayerSide.RIGHT;
				} else {
					currentPlayerSide = PlayerSide.LEFT;
				}
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
	
	public float getAttackRange() {
		return attackRange;
	}

	public boolean isAggro() {
		return isAggro;
	}

	public float getShootCount() {
		return shootCount;
	}

	public float getMaxShootCount() {
		return maxShootCount;
	}

	public float getAttackAngle() {
		return attackAngle;
	}

	public Projectile getProjectilePrototype() {
		return projectilePrototype;
	}

	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}

	public void setAggro(boolean isAggro) {
		this.isAggro = isAggro;
	}

	public void setShootCount(float shootCount) {
		this.shootCount = shootCount;
	}

	public void setMaxShootCount(float maxShootCount) {
		this.maxShootCount = maxShootCount;
	}

	public void setAttackAngle(float attackAngle) {
		this.attackAngle = attackAngle;
	}

	public void setProjectilePrototype(Projectile projectilePrototype) {
		this.projectilePrototype = projectilePrototype;
	}
	
	public PlayerSide getCurrentPlayerSide() {
		return this.currentPlayerSide;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getMaxVelocity() {
		return this.maxVelocity;
	}
	
	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}
	
	public boolean isAttacking() {
		return attacking;
	}
	
	public boolean isPlayerAbove() {
		return this.playerAbove;
	}
}
