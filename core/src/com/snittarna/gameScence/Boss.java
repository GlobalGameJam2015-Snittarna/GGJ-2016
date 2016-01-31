package com.snittarna.gameScence;

import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.Killable;
import com.snittarna.pizza.AssetManager;

public class Boss extends BasicEnemy {

	public Boss(Vector2 position) {
		super(position, 8, 20, 10, 5, 20, new Vector2(5, 5), new Animation(AssetManager.getTexture("projectile")), new Animation(AssetManager.getTexture("projectile")));
		
		this.setMaxShootCount(16);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(isAggro()) shoot(deltaTime);
		this.setProjectilePrototype(new Projectile(getPosition().cpy().add(new Vector2(getSize().x/4, getSize().y/4)), getAttackAngle(), 4, 4, Killable.Type.ENEMY, new Animation(AssetManager.getTexture("projectile"))));
	}
}
