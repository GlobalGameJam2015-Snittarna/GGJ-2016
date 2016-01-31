package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.pizza.AssetManager;

public class PowerUp extends GameObject {
	public enum Pattern { NORMAL, SPREAD };
	
	private Pattern pattern;
	
	private int damage;
	
	private float speed;
	
	private float maxFireDelay;
	
	public PowerUp(Vector2 position,  Pattern pattern, int damage, float speed, float shootDelay) {
		super(position, new Vector2(1, 1), new Animation(AssetManager.getTexture("error")));
		this.pattern = pattern;
		this.damage = damage;
		this.speed = speed;
		this.maxFireDelay = maxFireDelay;
	}
	
	public void update(float deltaTime) {
		for (GameObject g : getScene().getObjects()) {
			if (g instanceof Player) {
				if (g.getHitbox().collision(getHitbox())) {
					((Player)g).onPowerUp(this);
					AssetManager.getSound("powerup").play();
					getScene().removeObject(this);
				}
			}
		}
		super.update(deltaTime);
	}
	
	public Pattern getPattern() {
		return this.pattern;
	}
	
	public float getMaxFireDelay() {
		return this.maxFireDelay;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public int getDamage() {
		return this.damage;
	}
}
