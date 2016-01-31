package com.snittarna.gameScence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;

public class Corpse extends GameObject {
	private float colorDrain;
	
	public Corpse(Vector2 position, Vector2 size, Animation sprite) {
		super(position, sprite);
		
		setSize(size);
	}
	
	public void update(float deltaTime) {
		setSize(this.getSize().cpy().sub(new Vector2(0, 1 * deltaTime)));
		
		if(getSize().y <= 0) getScene().removeObject(this);
		
		colorDrain += 1 * deltaTime;
		
		setColor(new Color(1, 1-colorDrain, 1-colorDrain, 1-colorDrain));
		
		super.update(deltaTime);
	}
}
