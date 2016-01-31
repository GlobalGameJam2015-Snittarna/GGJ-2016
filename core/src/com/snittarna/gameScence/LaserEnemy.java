package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.pizza.AssetManager;

public class LaserEnemy extends Enemy {
	private float projectileSpeed;
	
	private int projectileDamage;
	
	public LaserEnemy(Vector2 position, float maxShootCount, int health, float attackRange, float projectileSpeed, int damage, Vector2 size, Animation sprite) {
		super(position, sprite);
		
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
		this.setProjectilePrototype(new Projectile(getPosition().cpy().add(new Vector2(getSize().x/4, getSize().y/4)), getAttackAngle(), projectileSpeed, projectileDamage, Killable.Type.ENEMY, new Animation(AssetManager.getTexture("projectile"))));
		super.update(deltaTime);
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}
