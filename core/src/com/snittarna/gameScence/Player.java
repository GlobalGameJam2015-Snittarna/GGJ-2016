package com.snittarna.gameScence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.gameScence.PowerUp.Pattern;
import com.snittarna.map.Map;
import com.snittarna.pizza.AssetManager;
import com.snittarna.pizza.Game;
import com.snittarna.ui.GameOverScene;
import com.snittarna.ui.Label;

public class Player extends Killable {
	
	public static final int DEFAULT_DAMAGE = 1;
	public static final float DEFAULT_SPEED = 8;
	public static final float DEFAULT_DELAY = 8;
	
	enum ShootDirection { 
		LEFT ((float)Math.PI), RIGHT (0), UP ((float)Math.PI/2);
		
		float value;
		
		ShootDirection(float value) {
			this.value = value;
		}
	}
	
	private boolean flip;
	
	final float FRICTION = 0.8f;
	
	private float currentFireDelay;
	private float maxFireDelay;
	
	private ShootDirection shootDirection;
	
	private PowerUp.Pattern currentShootPattern;
	
	private Projectile projectilePrototype;
	
	private float speed;
	
	private Label[] labels;
	private Label hpLabel, levelLabel, scoreLabel, powerupLabel;
	public static int score;
	
	//private Vector2 velocity;
	
	public Player(Vector2 position) {
		super(position, new Animation(new Sprite(AssetManager.getTexture("player")), .2f, 4, (int)1e10, false));
		Map.player = this;
		setSize(new Vector2(0.99f, 0.99f*1.5f));
		
		score = 0;
		
		setHealth(8);  
		
		this.speed = 100f;
		this.setType(Type.PLAYER);
		this.shootDirection = ShootDirection.LEFT; 
		this.projectilePrototype = new Projectile(new Vector2(0, 0), 0, DEFAULT_SPEED, DEFAULT_DAMAGE, Killable.Type.PLAYER, new Animation(AssetManager.getTexture("projectile")));
		this.velocity = new Vector2(0, 0);
		this.currentShootPattern = Pattern.NORMAL;
		
		this.setMaxInvicibleTimer(4);
		
		maxFireDelay = DEFAULT_DELAY;
		
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
		levelLabel.setText("level: " + (Map.getCurrentLevelIndex() + 1));
		powerupLabel.setText("powerup: " + currentShootPattern);
		scoreLabel.setText("score: " + score);
		
		if(this.getPosition().y <= -15) {
			this.setHealth(0);
		}
	}
	
	public void setHealth(int health) {
		if (health < getHealth()) hit();
		super.setHealth(health);
		if (health <= 0) {
			Game.setScene(new GameOverScene(score));
		}
	}
	
	private void hit() {
		AssetManager.getSound("player-hurt").play();
		getScene().setScreenShake(.2f);
	}
	
	public void onHit(Projectile p) {
		super.onHit(p);
		hit();
		if (getHealth() <= 0) {
			Game.setScene(new GameOverScene(score));
		}
	}
	
	public void onRemove() {
		AssetManager.getSound("player-hurt").play();
		Game.setScene(new GameOverScene(score));
	}
	
	public void updateInput(float deltaTime) {
		if(Gdx.input.isKeyJustPressed(Keys.X) && currentFireDelay <= 0) {
			if(this.currentShootPattern == Pattern.NORMAL) { 
				getScene().addObject(new Projectile(getPosition().cpy().add(new Vector2(getSize().x/4, getSize().y/4)), shootDirection.value, projectilePrototype.getSpeed()+(float)Math.abs(velocity.x), projectilePrototype.getDamage(), this.getType(), new Animation(AssetManager.getTexture("projectile"))));
			} else {
				for(int i = -1; i < 2; i++) {
					getScene().addObject(new Projectile(getPosition().cpy().add(new Vector2(getSize().x/4, getSize().y/4)), shootDirection.value-i*(float)Math.PI/16, projectilePrototype.getSpeed()+(float)Math.abs(velocity.x), projectilePrototype.getDamage(), this.getType(), new Animation(AssetManager.getTexture("projectile"))));
				}
			}
			currentFireDelay += 10 * deltaTime;
			AssetManager.getSound("player-shoot").play();
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.shootDirection = ShootDirection.LEFT;
			
			velocity.add(new Vector2(-speed * deltaTime, 0));
			flip = true;
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.shootDirection = ShootDirection.RIGHT;
			
			velocity.add(new Vector2(speed * deltaTime, 0));
			flip = false;
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			this.shootDirection = ShootDirection.UP;
		}
		
		if(!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.getSprite().setCurrentFrame(getSprite().getMinFrame());
		}
		
		if(colY && Gdx.input.isKeyJustPressed(Keys.Z)) {
			velocity.y = 16;
			AssetManager.getSound("player-jump").play();
		}
	}
	
	public void drawUi(SpriteBatch batch) {
		for (Label l : labels) l.draw(batch);
	}
	
	public void draw(SpriteBatch batch) {
		getSprite().setFlip(flip, false);
		super.draw(batch);
	}
	
	public void onPowerUp(PowerUp p) {
		this.currentShootPattern = p.getPattern();
		this.maxFireDelay = p.getMaxFireDelay();
		this.projectilePrototype = new Projectile(new Vector2(0, 0), 0, p.getSpeed(), p.getDamage(), Killable.Type.PLAYER, new Animation(AssetManager.getTexture("projectile")));
	}
	
	public void raiseScore(int value) {
		this.score += value;
	}
}
