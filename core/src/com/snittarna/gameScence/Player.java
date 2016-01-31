package com.snittarna.gameScence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.map.Map;
import com.snittarna.pizza.AssetManager;
import com.snittarna.ui.Label;

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
	
	private Label[] labels;
	private Label hpLabel, levelLabel, scoreLabel, powerupLabel;
	private int score;
	
	//private Vector2 velocity;
	
	public Player(Vector2 position) {
		super(position, new Animation(new Sprite(AssetManager.getTexture("player")), .2f, 4, (int)1e10, false));
		Map.player = this;
		setSize(new Vector2(0.99f, 0.99f));
		
		setHealth(3);  
		
		this.speed = 100f;
		this.setType(Type.PLAYER);
		this.shootDirection = ShootDirection.LEFT; 
		this.projectilePrototype = new Projectile(new Vector2(0, 0), 0, 8, 1, Killable.Type.PLAYER, new Animation(AssetManager.getTexture("projectile")));
		this.velocity = new Vector2(0, 0);
		
		this.setMaxInvicibleTimer(4);
		
		maxFireDelay = 8;
		
		hpLabel = new Label(new Vector2(-600, 400));
		levelLabel = new Label(new Vector2(-600, -400));
		scoreLabel = new Label(new Vector2(600, 400));
		powerupLabel = new Label(new Vector2(600, -400));
		labels = new Label[] { hpLabel, levelLabel, scoreLabel, powerupLabel };
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		getSprite().animate(deltaTime);
		getScene().cameraPosition.sub(getScene().cameraPosition.cpy().sub(new Vector3(getPosition(), 0)).scl(.1f));
		
		getScene().getCamera().update();
		
		updateInput(deltaTime);
		
		if(currentFireDelay > 0) {
			currentFireDelay += 10 * deltaTime;
			if(currentFireDelay >= maxFireDelay) {
				currentFireDelay = 0;
			}
		}
		
		velocity.set(new Vector2(velocity.x*FRICTION, velocity.y));

		//this.setPosition(this.getPosition().cpy().add(new Vector2(velocity.x*deltaTime, velocity.y*deltaTime)));
		hpLabel.setText("health: " + getHealth());
		levelLabel.setText("level: " + Map.getCurrentLevelIndex());
		powerupLabel.setText("powerup: none");
		scoreLabel.setText("score: " + score);
	}
	
	public void updateInput(float deltaTime) {
		if(Gdx.input.isKeyJustPressed(Keys.X) && currentFireDelay <= 0) {
			getScene().addObject(new Projectile(getPosition().cpy().add(new Vector2(getSize().x/4, getSize().y/4)), shootDirection.value, projectilePrototype.getSpeed()+(float)Math.abs(velocity.x), projectilePrototype.getDamage(), this.getType(), new Animation(AssetManager.getTexture("projectile"))));
			currentFireDelay += 10 * deltaTime;
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.shootDirection = ShootDirection.LEFT;
			
			velocity.add(new Vector2(-speed * deltaTime, 0));
			getSprite().setFlip(true, false);
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.shootDirection = ShootDirection.RIGHT;
			
			velocity.add(new Vector2(speed * deltaTime, 0));
			getSprite().setFlip(false, false);
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			this.shootDirection = ShootDirection.UP;
		}
		
		if(colY && Gdx.input.isKeyJustPressed(Keys.Z)) {
			velocity.y = 16;
		}
	}
	
	public void drawUi(SpriteBatch batch) {
		for (Label l : labels) l.draw(batch);
	}
	
	public void onPowerUp(PowerUp p) {
		this.currentShootPattern = p.getPattern();
		this.maxFireDelay = p.getMaxFireDelay();
		this.projectilePrototype = new Projectile(new Vector2(0, 0), 0, p.getSpeed(), p.getDamage(), Killable.Type.PLAYER, new Animation(AssetManager.getTexture("projectile")));
	}
}
