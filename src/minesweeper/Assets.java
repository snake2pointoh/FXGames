package minesweeper;

import javafx.scene.image.Image;

public interface Assets
{
	public static Image BASE_IMG = new Image("\\minesweeper\\assets\\BaseTile.png",100,100,true,false);
	public static Image CLEARED_IMG = new Image("\\minesweeper\\assets\\ClearedTile.png",100,100,true,false);
	public static Image FLAG_IMG = new Image("\\minesweeper\\assets\\Flag.png",100,100,true,false);
	public static Image BOMB_IMG = new Image("\\minesweeper\\assets\\Bomb.png",100,100,true,false);
}
