package minesweeper;

import javafx.scene.paint.Color;

public enum TileValue
{
	Zero(Color.WHITE,0),
	One(Color.BLUE,1),
	Two(Color.GREEN,2),
	Three(Color.RED,3),
	Four(Color.PURPLE,4),
	Five(Color.MAROON,5),
	Six(Color.TURQUOISE,6),
	Seven(Color.BLACK,7),
	Eight(Color.GRAY,8);
	
	public final Color color;
	public final int value;
	
	private TileValue(Color texCol, int nVal)
	{
		this.color = texCol;
		this.value = nVal;
	}
}
