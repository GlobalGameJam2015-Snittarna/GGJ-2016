package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.pizza.AssetManager;

//import com.snittarna.gameScene.Enemy;

public class LaserEnemy extends Enemy {

	public LaserEnemy(Vector2 position) {
		super(position, new Animation(AssetManager.getTexture("laserEnemy")));
		setSize(new Vector2(0.99f, 0.99f));
		System.out.println(getHitbox());
		setHealth(2);
		//gravitates = false;
	}
	
	public void update(float dt) {
		System.out.println(colX + ", " + colY);
		super.update(dt);
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}
