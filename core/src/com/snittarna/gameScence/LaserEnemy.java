package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.pizza.AssetManager;

public class LaserEnemy extends Enemy {
	private float projectileSpeed;
	
	private int projectileDamage;
	
	Animation attack, walk;
	
	public LaserEnemy(Vector2 position, float maxShootCount, int health, float attackRange, float projectileSpeed, int damage, Vector2 size, Animation walk, Animation attack) {
		super(position, walk);
		
		this.walk = walk;
		this.attack = attack;
		
		this.setSize(size);
		this.setHealth(health);
		this.setMaxShootCount(maxShootCount);
		this.setHealth(health);
		this.setAttackRange(attackRange);
		
		this.projectileDamage = damage;
		this.projectileSpeed = projectileSpeed;
		
		this.setWorth(getHealth() * 100 + 100);
		
		this.setType(Type.ENEMY);
	}
	
	public void update(float deltaTime) {
		if(!isPlayerAbove()) shoot(deltaTime);
		this.setProjectilePrototype(new Projectile(getPosition().cpy().add(new Vector2(getSize().x/4, getSize().y/2)), getAttackAngle(), projectileSpeed, projectileDamage, Killable.Type.ENEMY, new Animation(AssetManager.getTexture("projectile"))));
		
		if(this.getShootCount() >= this.getMaxShootCount()-2) {
			this.setSprite(attack);
		} else {
			this.setSprite(walk);
		}
		
		super.update(deltaTime);
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}
