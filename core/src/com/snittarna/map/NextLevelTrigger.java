package com.snittarna.map;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.gameScence.Cultist;
import com.snittarna.gameScence.GameScene;
import com.snittarna.gameScence.Player;
import com.snittarna.pizza.AssetManager;

public class NextLevelTrigger extends GameObject {
	private boolean canEnter;
	
	public NextLevelTrigger(Vector2 position) {
		super(position, new Vector2(2f, 2f), new Animation(new Sprite(AssetManager.getTexture("portal")), .5f, 4, 0, false));
		this.getSprite().setAnimation(0.5f, 4, 0, false);
	}
	
	public void update(float dt) {
		getSprite().animate(dt);
		
		if(canEnter) 
			setColor(new Color(1, 1, 1, 1));
		else
			setColor(new Color(1, 0.3f, 0.3f, 0.7f));
		
		canEnter = true;
		
		for (GameObject g : getScene().getObjects()) {
			if(g instanceof Cultist) {
				canEnter = false;
			}
		}
		
		for (GameObject g : getScene().getObjects()) {
			if (g instanceof Player) {
				if (g.getHitbox().collision(getHitbox()) && canEnter) {
					((GameScene)getScene()).nextLevel = true;
					System.out.println("new level");
					AssetManager.getSound("next-level").play();
					getScene().cameraPosition = new Vector3(-10, 0, 0);
				}
			}
		}
	}
}
