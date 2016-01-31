package com.snittarna.map;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.gameScence.GameScene;
import com.snittarna.gameScence.Player;
import com.snittarna.pizza.AssetManager;

public class NextLevelTrigger extends GameObject {

	public NextLevelTrigger(Vector2 position) {
		super(position, new Vector2(0.1f, .1f), new Animation(AssetManager.getTexture("error")));
	}
	
	public void update(float dt) {
		for (GameObject g : getScene().getObjects()) {
			if (g instanceof Player) {
				if (g.getHitbox().collision(getHitbox())) {
					((GameScene)getScene()).nextLevel = true;
					System.out.println("new level");
				}
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		// is invisible
	}
}
