package com.snittarna.gameScence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.pizza.AssetManager;

public class Player extends Killable {
	
	enum ShootDirection { 
		LEFT ((float)Math.PI), RIGHT (0), UP ((float)Math.PI/2);
		
		float value;
		
		ShootDirection(float value) {
			this.value = value;
		}
	}
	
	final float FRICTION = 0.8f;
	
	private float currentFireDelay;
	private float maxFireDelay;
	
	private ShootDirection shootDirection;
	
	private PowerUp.Pattern currentShootPattern;
	
	private Projectile projectilePrototype;
	
	private float speed;
	
	private Vector2 velocity;
	
	public Player(Vector2 position) {
		super(position, new Animation(AssetManager.getTexture("projectile")));
		
		setSize(new Vector2(1, 1));
		
		setHealth(3);  
		
		this.speed = 100f;
		
		this.setType(Type.PLAYER);
		
		this.shootDirection = ShootDirection.LEFT; 
		
		this.projectilePrototype = new Projectile(new Vector2(0, 0), 0, 8, 1, Killable.Type.PLAYER, new Animation(AssetManager.getTexture("projectile")));
		
		this.velocity = new Vector2(0, 0);
		
		maxFireDelay = 8;
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		updateInput(deltaTime);
		
		if(currentFireDelay > 0) {
			currentFireDelay += 10 * deltaTime;
			if(currentFireDelay >= maxFireDelay) {
				currentFireDelay = 0;
			}
		}
		
		velocity.set(new Vector2(velocity.x*FRICTION, 0));
		
		System.out.println(velocity);
		
		this.setPosition(this.getPosition().cpy().add(new Vector2(velocity.x*deltaTime, velocity.y*deltaTime)));
	}
	
	public void updateInput(float deltaTime) {
		if(Gdx.input.isKeyJustPressed(Keys.SPACE) && currentFireDelay <= 0) {
			getScene().addObject(new Projectile(getPosition().cpy().add(new Vector2(getSize().x/4, getSize().y/4)), shootDirection.value, projectilePrototype.getSpeed(), projectilePrototype.getDamage(), this.getType(), new Animation(AssetManager.getTexture("projectile"))));
			currentFireDelay += 10 * deltaTime;
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.shootDirection = ShootDirection.LEFT;
			
			velocity.add(new Vector2(-speed * deltaTime, 0));
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.shootDirection = ShootDirection.RIGHT;
			
			velocity.add(new Vector2(speed * deltaTime, 0));
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			this.shootDirection = ShootDirection.UP;
		}
	}
	
	public void onPowerUp(PowerUp p) {
		this.currentShootPattern = p.getPattern();
		this.maxFireDelay = p.getMaxFireDelay();
		this.projectilePrototype = new Projectile(new Vector2(0, 0), 0, p.getSpeed(), p.getDamage(), Killable.Type.PLAYER, new Animation(AssetManager.getTexture("projectile")));
	}
}
