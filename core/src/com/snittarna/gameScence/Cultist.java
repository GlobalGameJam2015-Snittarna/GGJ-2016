package com.snittarna.gameScence;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.framework.Killable;
import com.snittarna.map.NextLevelTrigger;
import com.snittarna.pizza.AssetManager;

public class Cultist extends Killable {
	private float spawnCount;
	private float maxSpawnCount;
	
	private float spawnDelay;
	private float maxSpawnDelay;
	
	private boolean spawning;
	private boolean aggro;
	
	private Animation idle;
	private Animation active;
	
	public Cultist(Vector2 position, int health) {
		super(position, new Animation(AssetManager.getTexture("cultistIdle")));
		this.setHealth(health);
		
		this.setSize(new Vector2(1.5f, 2.5f));
		this.maxSpawnCount = 8;
		this.maxSpawnDelay = 5;
		
		idle = new Animation(AssetManager.getTexture("cultistIdle"));
		active = new Animation(AssetManager.getTexture("cultist"));
		active.setAnimation(0.5f, 4, 0, false);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		spawnCount += 10 * deltaTime;
		
		if(spawnCount >= maxSpawnCount) {
			spawnCount = 0;
			spawning = !spawning;
		}
		
		for(GameObject g : getScene().getObjects()) {
			if(g instanceof Player) {
				aggro = (this.distanceTo(g.getPosition()) <= 10);
			}
		}
		
		if(spawning) {
			if(aggro) {
				maxSpawnCount = 16;
				this.setColor(new Color(1, 0.3f, 0.3f, 1));
				
				spawnDelay += 10 *deltaTime;
				
				if(spawnDelay >= maxSpawnDelay) {
					spawnRandomEnemy();
					spawnDelay = 0;
				}
			}
			
			setSprite(active);
			
			getSprite().animate(deltaTime);
		} else {
			maxSpawnCount = 4;
			this.setColor(new Color(1, 1, 1, 1));
			setSprite(idle);
		}
	}
	
	public void spawnRandomEnemy() {
		Random random = new Random();
		
		int type = random.nextInt(5);
		Vector2 position = new Vector2(0, 0);
		
		for(GameObject g : getScene().getObjects()) {
			if(g instanceof NextLevelTrigger)
				position = g.getPosition().cpy().add(new Vector2(0, 2));
		}
		
		switch(type) {
		case 0:
			getScene().addObject(new BasicEnemy(position, 8, 1, 5, 1, 50, new Vector2(0.9f, 0.9f), new Animation(new Sprite(AssetManager.getTexture("basicEnemy")), 0.3f, 3, 0, false), new Animation(new Sprite(AssetManager.getTexture("basicEnemyAttack")), 0.3f, 2, 0, false)));
			break;
		case 1:
			getScene().addObject(new BasicEnemy(position, 8, 1, 7, 1, 100, new Vector2(0.5f, 0.5f), new Animation(AssetManager.getTexture("projectile")), new Animation(AssetManager.getTexture("projectile"))));
			break;
		}
	}
}
