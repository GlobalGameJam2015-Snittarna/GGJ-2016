package com.snittarna.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.Animation;
import com.snittarna.framework.GameObject;
import com.snittarna.framework.Point;
import com.snittarna.framework.Scene;
import com.snittarna.gameScence.BarrierEnemy;
import com.snittarna.gameScence.BasicEnemy;
import com.snittarna.gameScence.Boss;
import com.snittarna.gameScence.LaserEnemy;
import com.snittarna.gameScence.Player;
import com.snittarna.pizza.AssetManager;

public class Level {
	private ArrayList<Tile> tiles;
	private Pixmap data;
	
	public Level(ArrayList<Tile> tiles, Pixmap data) {
		this.tiles = tiles;
		this.data = data;
		System.out.println(tiles.size() + " tiles in level");
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public void draw(SpriteBatch batch) {
		for (Tile t : tiles) t.draw(batch);
	}
	
	public void loadObjects(Scene scene) {
		for (int x = 0; x < data.getWidth(); x++) {
			for (int y = 0; y < data.getHeight(); y++) {
				addObject(new Vector2(x + 0.5f, -y + 0.5f), data.getPixel(x, y), scene);
			}
		}
	}
	
	private static void addObject(Vector2 position, int pixel, Scene scene) {
		switch(pixel) {
		case 0xff0000ff:
			scene.addObject(Map.player == null ? new Player(position) : Map.player);
			Map.player.setPosition(position);
			break;
		case 0x0070ECff:
			// basic enemy
			scene.addObject(new BasicEnemy(position, 8, 2, 5, 1, 150, new Vector2(0.9f, 0.9f), new Animation(new Sprite(AssetManager.getTexture("basicEnemy")), 0.3f, 3, 0, false), new Animation(new Sprite(AssetManager.getTexture("basicEnemyAttack")), 0.3f, 2, 0, false)));
			break;
		case 0x72007Cff:
			// tank enemy
			scene.addObject(new BasicEnemy(position, 32, 5, 7, 3, 50, new Vector2(2, 2), new Animation(AssetManager.getTexture("projectile")), null));
			break;
		case 0x21007Fff:
			// Small minIon
			scene.addObject(new BasicEnemy(position, 8, 5, 7, 3, 50, new Vector2(0.5f, 0.5f), new Animation(AssetManager.getTexture("projectile")), null));
			break;
		case 0x00ff00ff:
			scene.addObject(new LaserEnemy(position, 16, 2, 7, 8, 1, new Vector2(0.9f, 0.9f), new Animation(AssetManager.getTexture("projectile"))));
			break;
		case 0x7FFF8Eff:
			scene.addObject(new BarrierEnemy(position, 16, 2, 7, 8, 1, new Vector2(0.9f, 0.9f), new Animation(AssetManager.getTexture("projectile"))));
			break;
		case 0x6D7F3Fff:
			scene.addObject(new Boss(position));
			break;
		case 0xffff00ff:
			scene.addObject(new NextLevelTrigger(position));
			break;
		}
	}
}
