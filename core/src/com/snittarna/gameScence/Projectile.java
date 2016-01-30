package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.framework.Killable;

public class Projectile extends GameObject {
	private int damage;
	
	private float angle;
	private float speed;
	
	private Killable.Type owner;
	
	public Projectile(Vector2 position, float angle, float speed, int damage, Killable.Type owner, Animation sprite) {
		super(position, sprite);
		
		this.damage = damage;
		this.angle = angle;
		this.speed = speed;
		
		this.owner = owner;
		
		gravitates = false;
		
		setSize(new Vector2(0.5f, 0.5f));
	}
	
	public void update(float deltaTime) {
		velocity = this.getVelocity();
		
		if(colX || colY) { 
			onHit();
		}
		
		super.update(deltaTime);
		
	}
	
	public void onHit() {
		getScene().removeObject(this);
	}
	
	private Vector2 getVelocity() {
		return new Vector2((float)Math.cos(angle) * speed, (float)Math.sin(angle) * speed);
	}
	
	public Killable.Type getOwner() {
		return this.owner;
	}
	
	public float getSpeed() { 
		return this.speed;
	}
	
	public int getDamage() {
		return this.damage;
	}
}
