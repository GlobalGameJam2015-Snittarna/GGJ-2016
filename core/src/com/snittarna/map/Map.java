package com.snittarna.map;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snittarna.framework.GameObject;
import com.snittarna.framework.Point;
import com.snittarna.framework.ResourceReader;
import com.snittarna.pizza.AssetManager;

public class Map {
	
	private static Level[] levels;
	private static int currentLevel;
	
	
	public static void load() {
		TileType[] types = TileType.getAll(); 
		ResourceReader[] readers = ResourceReader.readObjectList(Gdx.files.internal("maps.gd"));
		levels = new Level[readers.length];
		int i = 0;
		for (ResourceReader r : readers) {
			levels[i] = loadMap(r);
		}
	}
	
	private static Level loadMap(ResourceReader r) {
		System.out.println(r.getString("data"));
		TextureData d = new Texture("maps/" + r.getString("data")).getTextureData();
		d.prepare();
		Pixmap data = d.consumePixmap();
		ArrayList<Tile> ret = new ArrayList<Tile>();
		System.out.println(data.getWidth() + ", " + data.getHeight());
		for (int x = 0; x < data.getWidth(); x++) {
			for (int y = 0; y < data.getHeight(); y++) {
				TileType t = getType(data.getPixel(x, y));
				if (t != null) {
					ret.add(new Tile(new Point(x, -y), new Vector2(x, -y), t));		
				}
			}
		}
		return new Level(ret);
	}

	private static TileType getType(int pixel) {
		Color c = new Color(pixel);
		TileType[] types = TileType.getAll();
		for (TileType t : types) {
			System.out.println(c + ", " + t.getDataColor());
			if (t.getDataColor().r == c.r && t.getDataColor().g == c.g && t.getDataColor().b == c.b && c.a != 0) {
				return t;
			}
		}
		System.out.println("wrong color " + c);
		return null;
	}
	
	public static void draw(SpriteBatch batch) {
		levels[currentLevel].draw(batch);
	}
	
	public static ArrayList<Tile> getTiles() {
		return levels[currentLevel].getTiles();
	}
}
