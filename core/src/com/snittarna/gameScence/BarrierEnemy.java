package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.pizza.AssetManager;

public class BarrierEnemy extends LaserEnemy {
	private float vunrableCount;
	private float maxVunrableCount;
	
	private boolean vunrable; 
	
	private Animation shield;
	
	public BarrierEnemy(Vector2 position, float maxShootCount, int health, float attackRange, float projectileSpeed,
			int damage, Vector2 size, Animation sprite) {
		super(position, maxShootCount, health, attackRange, projectileSpeed, damage, size, sprite);
		
		this.setMaxInvicibleTimer(1.5f);
		
		this.maxVunrableCount = 20;
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		vunrableCount += 10 * deltaTime;
		
		if(vunrableCount >= maxVunrableCount) {
			vunrableCount = 0;
			vunrable = !vunrable;
		} 
		
		if(vunrable) {
			this.setDamage(1);
		} else {
			this.setDamage(0);
		}
		
		setShootCount(0);
	}
	
	public void onHit(Projectile p) {
		if(!vunrable) { 
			setHealth(getHealth()+p.getDamage());
			getScene().addObject(new Projectile(getPosition().cpy().add(new Vector2(getSize().x/4, getSize().y/4)), getAttackAngle(), p.getSpeed(), p.getDamage(), Killable.Type.ENEMY, new Animation(AssetManager.getTexture("projectile"))));
		}
		super.onHit(p);
	}
	
	public void draw(SpriteBatch batch) {
		if(!vunrable) {
			this.setColor(new Color(0.5f, 0.5f, 1f, 1f));
		} else {
			this.setColor(new Color(1f, 1f, 1f, 1f));
		}
		super.draw(batch);
	}
}
