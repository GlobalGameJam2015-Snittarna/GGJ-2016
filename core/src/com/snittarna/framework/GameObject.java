/**
 * 
 */
package com.snittarna.framework;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.snittarna.map.Map;
import com.snittarna.map.Tile;

/**
 * @author Johannes
 */
public abstract class GameObject {
	private Vector2 position, size, origin; 
	protected Vector2 velocity;
	private Animation sprite;
	private Scene scene;
	private float depth;
	protected boolean gravitates;
	
	final static float G = -1f;

	public void setPosition(Vector2 p) { 
		position = p;
		if (sprite != null) sprite.setPosition(position.x - sprite.getOriginX() / sprite.getRegionWidth(), position.y - sprite.getOriginY() / sprite.getRegionHeight());
	}
	
	public Vector2 getPosition() { return position; }
	
	// position on the screen, in world space coordinates
	public Vector2 getScreenCoords() {
		Vector3 v = scene.getCamera().project(new Vector3(getPosition(), 0));
		return new Vector2(v.x, v.y);
	}
	
	public Vector2 getUiScreenCoords() {
		return getScreenCoords().scl(2).sub(new Vector2(scene.UI_W / 2, scene.UI_H / 2));
	}
	
	public void setSize(Vector2 s) { 
		size = s;
		//System.out.println("setting size " + s);
		if (sprite != null) sprite.setSize(s.x, s.y); 
	}
	
	public void setColor(Color c) {
		if(sprite != null) sprite.setColor(c);
	}
	
	public Vector2 getSize() { return size; }
	
	public Animation getSprite() { return sprite; }

	public Scene getScene() { return scene; }
	
	//note that origin is in pixels on the texture region, not world coordinates
	protected void setOrigin(Vector2 origin) {
		this.origin = origin;
		if (sprite != null) sprite.setOrigin(origin.x, origin.y);
	}
	
	protected Vector2 getOrigin() {
		return origin;
	}
	
	protected void setOriginCenter() {
		getSprite().setOriginCenter();
		origin = new Vector2(sprite.getOriginX()/2, sprite.getOriginY()/2);
		//origin = new Vector2(sprite.getRegionWidth() / 2, sprite.getRegionHeight() / 2);
	}
	
	public float getDepth() {
		return depth;
	}
	
	public void setDepth(float depth) {
		this.depth = depth;
		if (scene != null) scene.onDepthChange();
	}
	
	public void setSprite(TextureRegion texture) {
		sprite.setRegion(texture);
	}
	
	public void setSprite(Animation sprite) {
		this.setSprite(sprite, false);
	}
	
	public void setSprite(Animation sprite, boolean discardInfo) {
		this.sprite = sprite;
		if (!discardInfo) {
			setPosition(position);
			setSize(size);
			setOrigin(origin);
		}
	}
		
	public GameObject(Vector2 position, Vector2 size, Animation sprite) {
		this.sprite = sprite;
		setOriginCenter();
		setPosition(position);
		setSize(size);
		velocity = new Vector2();
		gravitates = true;
	}
	
	public GameObject(Vector2 position, Animation sprite) {
		this(position, new Vector2((sprite != null ? sprite.getWidth() : 0), (sprite != null ? sprite.getHeight() : 0)), sprite);
	}
	
	public void onAdd(Scene scene) {
		this.scene = scene;
	}
	
	public Rectangle getHitbox() {
		//System.out.println("hitbox size " + size);
		return new Rectangle(this.getSprite().getBoundingRectangle().x, this.getSprite().getBoundingRectangle().y, this.getSprite().getBoundingRectangle().width, this.getSprite().getBoundingRectangle().height);
		//return new Rectangle(this.position.cpy().sub(this.size.cpy().scl(.5f)), this.size);
	}
	
	public void onRemove() { };
	
	public float distanceTo(Vector2 v) {
		Vector2 delta = v.cpy().sub(getPosition().cpy());
		float f = delta.len();
		return f;
	}
	
	private void move(float x, float y) {
		setPosition(getPosition().add(x, y));
	}
	
	protected boolean colX, colY;
	
	public void update(float deltaTime) { 

		//System.out.println(velocity);
		colX = false;
		colY = false;
		
		if (gravitates) velocity.y += G;
		
		int steps = 100;
		
		ArrayList<Tile> tiles = getCloseTiles(Map.getTiles());
		
		for (int i = 0; i < steps; i++) {
			if (!colX) {
				move(velocity.x / steps * deltaTime, 0);
				if (isCollidingWithAny(tiles)) {
					colX = true;
					move(-velocity.x / steps * deltaTime, 0);
					velocity.x = 0;
				}
			}
			
			if (!colY) {
				move(0, velocity.y / steps * deltaTime);
				if (isCollidingWithAny(tiles)) {
					colY = true;
					move(0, -velocity.y / steps * deltaTime);
					velocity.y = 0;
				}
			}
		}
        
        //System.out.println("x: " + colX + ", y: " + colY);	
	}
	
	private boolean isCollidingWithAny(ArrayList<Tile> tiles) {
		for (Tile t : tiles) {
			if (getHitbox().collision(t.getHitBox())) {
				//System.out.println("collision " + getHitbox().toString() + ", " + t.getHitBox().toString());
				return true;
			}
		}
		return false;
	}

	private ArrayList<Tile> getCloseTiles(ArrayList<Tile> tiles) {
		ArrayList<Tile> ret = new ArrayList<Tile>();
		for (Tile t : tiles) {
			if (distanceTo(t.getPosition()) < velocity.len() + 1.5) ret.add(t);
		}
		return ret;
	}

	public void draw(SpriteBatch batch) {
		if (sprite != null) sprite.draw(batch);
	}
	
	public void drawUi(SpriteBatch batch) { } 
}
